package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistsStorageException;
import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract int indexOf(Resume resume);

    protected abstract void save_(int index, Resume resume);

    protected abstract Resume get_(int index);

    protected abstract void delete_(int index);

    protected abstract void update_(int index, Resume resume);

    @Override
    public final void save(Resume resume) {
        int index = indexOf(resume);
        if (index >= 0) {
            throw new ExistsStorageException(resume);
        } else {
            save_(index, resume);
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

    @Override
    public final void delete(String uuid) {
        Resume resume = new Resume(uuid);
        int index = indexOf(resume);
        if (index < 0) {
            throw new NotExistsStorageException(resume);
        } else {
            delete_(index);
        }
    }

    @Override
    public final void update(Resume resume) {
        int index = indexOf(resume);
        if (index < 0) {
            throw new NotExistsStorageException(resume);
        } else {
            update_(index, resume);
        }

    }
}

