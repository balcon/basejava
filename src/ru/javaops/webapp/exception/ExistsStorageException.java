package ru.javaops.webapp.exception;

import ru.javaops.webapp.model.Resume;

public class ExistsStorageException extends StorageException {
    public ExistsStorageException(Resume resume) {
        super("Resume [" + resume.getUuid() + "] already exists");
    }
}
