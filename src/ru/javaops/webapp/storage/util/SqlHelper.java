package ru.javaops.webapp.storage.util;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.storage.function.ConsumerSqlStatement;
import ru.javaops.webapp.storage.function.FunctionSqlStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public void updateQuery(String query, ConsumerSqlStatement action) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement statement =
                     connection.prepareStatement(query)) {
            action.accept(statement);
        } catch (SQLException e) {
            throw new StorageException("Database error", e);
        }
    }

    public <T> T selectQuery(String query, FunctionSqlStatement<T> action) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement statement =
                     connection.prepareStatement(query)) {
            return action.apply(statement);
        } catch (SQLException e) {
            throw new StorageException("Database error", e);
        }
    }
}
