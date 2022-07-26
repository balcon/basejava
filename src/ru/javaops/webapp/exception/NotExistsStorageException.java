package ru.javaops.webapp.exception;

import ru.javaops.webapp.model.Resume;

public class NotExistsStorageException extends StorageException {
    public NotExistsStorageException(String uuid) {
        super("Resume [" + uuid + "] not exists");
    }

    public NotExistsStorageException(Resume resume) {
        this(resume.getUuid());
    }
}
