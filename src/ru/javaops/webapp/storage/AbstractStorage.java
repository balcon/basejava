package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistsStorageException;
import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void insertResume(int index, Resume resume);

    protected abstract Resume getResume(int index);

    protected abstract void updateResume(int index, Resume resume);

    protected abstract void removeResume(int index);

    protected abstract int indexOf(Resume resume);

    @Override
    public final void save(Resume resume) {
        int index = indexOf(resume);
        if (index >= 0) {
            throw new ExistsStorageException(resume);
        } else {
            insertResume(index, resume);
        }
    }

    @Override
    public final Resume get(String uuid) {
        Resume resume = new Resume(uuid);
        int index = indexOf(resume);
        if (index < 0) {
            throw new NotExistsStorageException(resume);
        }
        return getResume(index);
    }

    @Override
    public final void delete(String uuid) {
        Resume resume = new Resume(uuid);
        int index = indexOf(resume);
        if (index < 0) {
            throw new NotExistsStorageException(resume);
        } else {
            removeResume(index);
        }
    }

    @Override
    public final void update(Resume resume) {
        int index = indexOf(resume);
        if (index < 0) {
            throw new NotExistsStorageException(resume);
        } else {
            updateResume(index, resume);
        }
    }
}

