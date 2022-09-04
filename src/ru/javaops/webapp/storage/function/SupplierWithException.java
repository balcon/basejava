package ru.javaops.webapp.storage.function;

import java.io.IOException;

@FunctionalInterface
public interface SupplierWithException<T> {
    T get() throws IOException;
}
