package ru.javaops.webapp.storage.util;

import ru.javaops.webapp.exception.ExistsStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.storage.function.ExecutorSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executeQuery(String query, ExecutorSql<T> action) {
        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            return action.execute(statement);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // unique_violation
                throw new ExistsStorageException("some_uuid");
            }
            throw new StorageException("Database error", e);
        }
    }
}
