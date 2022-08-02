package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Abstract array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_CAPACITY = 10000;

    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int size = 0;

    protected abstract void insertResumeToArray(Object searchKey, Resume resume);

    protected abstract void removeResumeFromArray(Object searchKey);

    @Override
    protected void insertResume(Object searchKey, Resume resume) {
        if (size >= STORAGE_CAPACITY) {
            throw new StorageException("Storage overflow");
        } else {
            insertResumeToArray(searchKey, resume);
            size++;
        }
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected void updateResume(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    @Override
    protected void removeResume(Object searchKey) {
        removeResumeFromArray(searchKey);
        size--;
    }


    @Override
    protected Object getSearchKey(String uuid) {
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return false;
    }

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public final int getSize() {
        return size;
    }
}
