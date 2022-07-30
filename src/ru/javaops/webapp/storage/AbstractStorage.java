package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistsStorageException;
import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract int indexOf(Resume resume);

    protected abstract void save_(Resume resume, int index);

    protected abstract Resume get_(int index);

    @Override
    public final void save(Resume resume) {
        int index = indexOf(resume);
        if (index >= 0) {
            throw new ExistsStorageException(resume);
        } else {
            save_(resume, index);
        }
    }

    @Override
    public final Resume get(String uuid) {
        Resume resume = new Resume(uuid);
        int index = indexOf(resume);
        if (index < 0) {
            throw new NotExistsStorageException(resume);
        }
        return get_(index);
    }

}

