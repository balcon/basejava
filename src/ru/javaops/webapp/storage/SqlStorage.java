package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.util.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SqlStorage implements Storage {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public SqlStorage() {
        this.dbUrl = Config.get("db.url");
        this.dbUser = Config.get("db.user");
        this.dbPassword = Config.get("db.password");
    }

    @Override
    public void clear() {

    }

    @Override
    public void save(Resume resume) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO resume (UUID, full_name) VALUES (?,?)")) {
            statement.setString(1, resume.getUuid());
            statement.setString(2, resume.getFullName());
            statement.execute();
        } catch (SQLException e) {
            throw new StorageException("DB read error", e);
        }
    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    public void update(Resume resume) {

    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public List<Resume> getAllSorted() {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }
}
