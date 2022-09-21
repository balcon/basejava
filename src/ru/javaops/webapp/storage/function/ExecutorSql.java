package ru.javaops.webapp.storage.function;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ExecutorSql<T> {
    T execute(PreparedStatement statement) throws SQLException;
}
