package ru.javaops.webapp.storage.function;

import java.io.IOException;

@FunctionalInterface
public interface ExecutorWithException {
    void execute() throws IOException;
}
