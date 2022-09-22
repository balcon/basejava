package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.util.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String url, String user, String password) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(url, user, password));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactExecute(connection -> {
            try (PreparedStatement statement =
                         connection.prepareStatement("INSERT INTO resume (UUID, full_name) VALUES (?,?)")) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, resume.getFullName());
                statement.execute();
            }
            try (PreparedStatement statement =
                         connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                    statement.setString(1, resume.getUuid());
                    statement.setString(2, contact.getKey().name());
                    statement.setString(3, contact.getValue());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r " +
                "LEFT JOIN contact c ON c.resume_uuid = r.uuid " +
                "WHERE r.uuid=?", statement -> {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistsStorageException(uuid);
            }
            Resume resume = new Resume(uuid, resultSet.getString("full_name"));
            do {
                resume.setContact(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
            } while (resultSet.next());
            return resume;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.execute("UPDATE resume SET full_name=? WHERE uuid=?", statement -> {
            statement.setString(1, resume.getFullName());
            statement.setString(2, resume.getUuid());
            if (statement.executeUpdate() == 0) {
                throw new NotExistsStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", statement -> {
            statement.setString(1, uuid);
            if (statement.executeUpdate() == 0) {
                throw new NotExistsStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume ORDER BY full_name", statement -> {
            List<Resume> resumes = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid").strip();
                String fullName = resultSet.getString("full_name");
                resumes.add(new Resume(uuid, fullName));
            }
            return resumes;
        });
    }

    @Override
    public int getSize() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", statement -> {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

}
