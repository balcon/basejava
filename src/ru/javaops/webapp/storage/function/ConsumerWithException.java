package ru.javaops.webapp.storage.function;

import java.io.IOException;

@FunctionalInterface
public interface ConsumerWithException<T> {
    void accept(T object) throws IOException;
}
