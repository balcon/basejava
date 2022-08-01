package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistsStorageException;
import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void insertResume(int index, Resume resume);

    protected abstract Resume getResume(int index);

    protected abstract void updateResume(int index, Resume resume);

    protected abstract void removeResume(int index);

    protected abstract int indexOf(String uuid);

    private int getExistingIndex(String uuid) {
        int index = indexOf(uuid);
        if (index < 0) {
            throw new NotExistsStorageException(uuid);
        }
        return index;
    }

    private int getNotExistingIndex(String uuid) {
        int index = indexOf(uuid);
        if (index >= 0) {
            throw new ExistsStorageException(uuid);
        }
        return index;
    }

    @Override
    public final void save(Resume resume) {
        insertResume(getNotExistingIndex(resume.getUuid()), resume);
    }

    @Override
    public final Resume get(String uuid) {
        return getResume(getExistingIndex(uuid));
    }

    @Override
    public final void delete(String uuid) {
        removeResume(getExistingIndex(uuid));
    }

    @Override
    public final void update(Resume resume) {
        updateResume(getExistingIndex(resume.getUuid()), resume);
    }
}

