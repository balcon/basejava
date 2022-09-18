package ru.javaops.webapp.storage.function;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface FunctionSqlStatement<T> {
    T apply(PreparedStatement statement) throws SQLException;
}
