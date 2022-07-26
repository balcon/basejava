package ru.javaops.webapp.exception;

public class NotExistsStorageException extends StorageException {
    public NotExistsStorageException(String uuid) {
        super("Resume ["+uuid+"] not exists in the storage");
    }
}
