package ru.javaops.webapp.exception;

public class StorageException extends RuntimeException {
    public StorageException(Exception e) {
        super(e);
    }

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Exception e) {
        super(message, e);
    }
}
