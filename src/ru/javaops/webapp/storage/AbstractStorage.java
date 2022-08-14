package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistsStorageException;
import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<K> implements Storage {

    protected abstract void doSave(K searchKey, Resume resume);

    protected abstract Resume doGet(K searchKey);

    protected abstract void doUpdate(K searchKey, Resume resume);

    protected abstract void doDelete(K searchKey);

    protected abstract List<Resume> doCopyAll();

    protected abstract K getSearchKey(String uuid);

    protected abstract boolean isExist(K searchKey);

    private K getExistingSearchKey(String uuid) {
        K searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistsStorageException(uuid);
        }
        return searchKey;
    }

    private K getNotExistingSearchKey(String uuid) {
        K searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistsStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public final void save(Resume resume) {
        K searchKey = getNotExistingSearchKey(resume.getUuid());
        doSave(searchKey, resume);
    }

    @Override
    public final Resume get(String uuid) {
        return doGet(getExistingSearchKey(uuid));
    }

    @Override
    public final void update(Resume resume) {
        doUpdate(getExistingSearchKey(resume.getUuid()), resume);
    }

    @Override
    public final void delete(String uuid) {
        doDelete(getExistingSearchKey(uuid));
    }

    @Override
    public final List<Resume> getAllSorted() {
        List<Resume> resumes = doCopyAll();
        resumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumes;
    }
}

