package ru.javaops.webapp.exception;

import ru.javaops.webapp.model.Resume;

public class NotExistsStorageException extends StorageException {
    public NotExistsStorageException(Resume resume) {
        super("Resume [" + resume + "] not exists");
    }
}
