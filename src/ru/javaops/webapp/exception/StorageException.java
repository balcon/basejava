package ru.javaops.webapp.exception;

public class StorageException extends RuntimeException {
    public StorageException(String message) {
        super(message);
    }
}
