package ru.javaops.webapp.storage.util;

import ru.javaops.webapp.exception.ExistsStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.storage.function.ExecutorSql;
import ru.javaops.webapp.storage.function.TransactExecutorSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new StorageException("Database driver error");
        }
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String query, ExecutorSql<T> executor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            return executor.execute(statement);
        } catch (SQLException e) {
            throw checkSqlState(e);
        }
    }

    public <T> T transactExecute(TransactExecutorSql<T> executor) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T result = executor.execute(connection);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                throw checkSqlState(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private StorageException checkSqlState(SQLException e) {
        if (e.getSQLState().equals("23505")) { // unique_violation
            return new ExistsStorageException("some_uuid");
        }
        return new StorageException(e);
    }
}
