package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistsStorageException;
import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<K> implements Storage {

    protected abstract void insertResume(Resume resume);

    protected abstract Resume getResume(K searchKey);

    protected abstract void updateResume(K searchKey, Resume resume);

    protected abstract void removeResume(K searchKey);

    protected abstract List<Resume> getAll();

    protected abstract K getSearchKey(String uuid);

    protected abstract boolean isExist(K searchKey);

    private K getExistingSearchKey(String uuid) {
        K searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistsStorageException(uuid);
        }
        return searchKey;
    }

    private void getNotExistingSearchKey(String uuid) {
        K searchKey = getSearchKey(uuid);
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

    @Override
    public final List<Resume> getAllSorted() {
        List<Resume> resumes = getAll();
        resumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumes;
    }
}

