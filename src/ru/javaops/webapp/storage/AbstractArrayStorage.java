package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Abstract array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_CAPACITY = 10000;

    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int size = 0;

    protected abstract void insertResumeToArray(Resume resume);

    protected abstract void removeResumeFromArray(int index);


    @Override
    protected void insertResume(Resume resume) {
        if (size >= STORAGE_CAPACITY) {
            throw new StorageException("Storage overflow");
        } else {
            insertResumeToArray(resume);
            size++;
        }
    }

    @Override
    protected final Resume getResume(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected final void updateResume(Integer searchKey, Resume resume) {
        storage[searchKey] = resume;
    }

    @Override
    protected final void removeResume(Integer searchKey) {
        int index = searchKey;
        removeResumeFromArray(index);
        size--;
    }

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public final int getSize() {
        return size;
    }
}
