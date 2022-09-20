package ru.javaops.webapp.storage.util;

import org.postgresql.util.PSQLException;
import ru.javaops.webapp.exception.ExistsStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.storage.function.ConsumerSqlStatement;
import ru.javaops.webapp.storage.function.FunctionSqlStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void updateQuery(String query, ConsumerSqlStatement action) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query)) {
            action.accept(statement);
        } catch (PSQLException pe) {
            if (pe.getSQLState().equals("23505")) { // unique_violation
                throw new ExistsStorageException("some_uuid");
            }
        } catch (SQLException e) {
            throw new StorageException("Database error", e);
        }
    }

    public <T> T selectQuery(String query, FunctionSqlStatement<T> action) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query)) {
            return action.apply(statement);
        } catch (SQLException e) {
            throw new StorageException("Database error", e);
        }
    }
}
