package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.util.Config;
import ru.javaops.webapp.storage.util.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sql;

    public SqlStorage() {
        sql = new SqlHelper(Config.get("db.url"), Config.get("db.user"), Config.get("db.password"));
    }

    @Override
    public void clear() {
       sql.updateQuery("DELETE FROM resume", PreparedStatement::execute);
    }

    // TODO Doesn't throw ExistsStorageException
    @Override
    public void save(Resume resume) {
        sql.updateQuery("INSERT INTO resume (UUID, full_name) VALUES (?,?)", statement -> {
            statement.setString(1, resume.getUuid());
            statement.setString(2, resume.getFullName());
            statement.execute();
        });
    }

    @Override
    public Resume get(String uuid) {
        return sql.selectQuery("SELECT uuid, full_name FROM resume WHERE uuid=?", statement -> {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistsStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        });
    }

    @Override
    public void update(Resume resume) {
        sql.updateQuery("UPDATE resume SET full_name=? WHERE uuid=?", statement -> {
            statement.setString(1, resume.getFullName());
            statement.setString(2, resume.getUuid());
            if(statement.executeUpdate()==0){
                throw new NotExistsStorageException(resume.getUuid());
            }
        });
    }

    @Override
    public void delete(String uuid) {
        sql.updateQuery("DELETE FROM resume WHERE uuid=?", statement -> {
            statement.setString(1, uuid);
            if (statement.executeUpdate() == 0) {
                throw new NotExistsStorageException(uuid);
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sql.selectQuery("SELECT * FROM resume ORDER BY full_name", statement -> {
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
        return sql.selectQuery("SELECT COUNT(*) FROM resume", statement -> {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

}
