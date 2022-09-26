package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.util.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO resume (UUID, full_name) VALUES (?,?)")) {
                setStatement(statement, resume.getUuid(), resume.getFullName());
                statement.execute();
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                    setStatement(statement, resume.getUuid(), contact.getKey().name(), contact.getValue());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r " +
                "LEFT JOIN contact c ON c.resume_uuid = r.uuid " +
                "WHERE r.uuid=?", statement -> {
            setStatement(statement, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistsStorageException(uuid);
            }
            Resume resume = getResume(resultSet);
            do {
                setContact(resume, resultSet);
            } while (resultSet.next());
            return resume;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE resume SET full_name=? WHERE uuid=?")) {
                setStatement(statement, resume.getFullName(), resume.getUuid());
                if (statement.executeUpdate() == 0) {
                    throw new NotExistsStorageException(resume.getUuid());
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM contact WHERE resume_uuid=?")) {
                setStatement(statement, resume.getUuid());
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                    setStatement(statement, resume.getUuid(), contact.getKey().name(), contact.getValue());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", statement -> {
            setStatement(statement, uuid);
            if (statement.executeUpdate() == 0) {
                throw new NotExistsStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume r " +
                "LEFT JOIN contact c ON c.resume_uuid = r.uuid " +
                "ORDER BY full_name, uuid", statement -> {
            List<Resume> resumes = new ArrayList<>();
            Set<String> uuids = new HashSet<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid").strip();
                if (!uuids.contains(uuid)) {
                    uuids.add(uuid);
                    resumes.add(getResume(resultSet));
                }
                setContact(resumes.get(resumes.size()-1), resultSet);
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

    private void setStatement(PreparedStatement statement, String... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            statement.setString(i + 1, values[i]);
        }
    }

    private Resume getResume(ResultSet resultSet) throws SQLException {
        return new Resume(resultSet.getString("uuid").strip(), resultSet.getString("full_name"));
    }

    private void setContact(Resume resume, ResultSet resultSet) throws SQLException {
        resume.setContact(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
    }

    private void insertContacts(PreparedStatement statement, Resume resume) throws SQLException {
        for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
            setStatement(statement, contact.getValue(), resume.getUuid(), contact.getKey().name());
            statement.addBatch();
        }
        statement.executeBatch();
    }
}
