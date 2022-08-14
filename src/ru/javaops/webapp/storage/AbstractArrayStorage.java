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

    protected abstract void insertResume(int index, Resume resume);

    protected abstract void removeResume(int index);


    @Override
    protected void doSave(Integer index, Resume resume) {
        if (size >= STORAGE_CAPACITY) {
            throw new StorageException("Storage overflow");
        } else {
            insertResume(index, resume);
            size++;
        }
    }

    @Override
    protected final Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    protected final void doUpdate(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected final void doDelete(Integer index) {
        removeResume(index);
        size--;
    }

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public final int getSize() {
        return size;
    }
}
