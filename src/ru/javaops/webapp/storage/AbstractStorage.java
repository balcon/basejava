package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistsStorageException;
import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void insertResume(Object searchKey, Resume resume);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void updateResume(Object searchKey, Resume resume);

    protected abstract void removeResume(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistsStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistsStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public final void save(Resume resume) {
        insertResume(getNotExistingSearchKey(resume.getUuid()), resume);
    }

    @Override
    public final Resume get(String uuid) {
        return getResume(getExistingSearchKey(uuid));
    }

    @Override
    public final void update(Resume resume) {
        updateResume(getExistingSearchKey(resume.getUuid()), resume);
    }

    @Override
    public final void delete(String uuid) {
        removeResume(getExistingSearchKey(uuid));
    }
}

