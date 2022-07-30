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

    protected abstract int indexOf(Resume resume);

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void removeResume(int index);

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected final void save_(Resume resume, int index) {
        if (size >= STORAGE_CAPACITY) {
            throw new StorageException("Storage overflow");
        } else {
            insertResume(resume, index);
            size++;
        }
    }

    @Override
    protected Resume get_(int index) {
        return storage[index];
    }

    @Override
    protected void delete_(int index) {
        removeResume(index);
        size--;
    }

    @Override
    protected void update_(int index, Resume resume) {
        storage[index] = resume;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public final int getSize() {
        return size;
    }
}
