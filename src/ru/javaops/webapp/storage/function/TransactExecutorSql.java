package ru.javaops.webapp.storage.function;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactExecutorSql {
    void execute(Connection connection) throws SQLException;
}
