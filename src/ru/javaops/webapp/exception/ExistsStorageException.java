package ru.javaops.webapp.exception;

public class ExistsStorageException extends StorageException {
    public ExistsStorageException(String uuid) {
        super("Resume [" + uuid + "] already exists");
    }
}
