package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistsStorageException;
import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Comparator;

public abstract class AbstractStorage implements Storage {

    protected static final Comparator<Resume> resumeComparator =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    protected abstract void insertResume(Resume resume);

    protected abstract Object getResume(Object searchKey);

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

    private void getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistsStorageException(uuid);
        }
        // return searchKey;
    }

    @Override
    public final void save(Resume resume) {
        getNotExistingSearchKey(resume.getUuid());
        insertResume(resume);
    }

    @Override
    public final Object get(String uuid) {
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

