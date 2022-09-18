package ru.javaops.webapp.storage.function;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ConsumerSqlStatement {
    void accept(PreparedStatement statement) throws SQLException;
}
