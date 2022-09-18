package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.util.Config;

import java.sql.*;
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
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement statement =
                     connection.prepareStatement("DELETE FROM resume")) {
            statement.execute();
        } catch (SQLException e) {
            throw new StorageException("DB error", e);
        }
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
            throw new StorageException("DB error", e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT uuid, full_name FROM resume WHERE uuid=?")) {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistsStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException("DB error", e);
        }
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
