package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Abstract array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
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
    protected final Object getResume(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected final void updateResume(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    @Override
    protected final void removeResume(Object searchKey) {
        int index = (int) searchKey;
        removeResumeFromArray(index);
        size--;
    }

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public final List<Resume> getAllSorted() {
        List<Resume> sortedStorage = Arrays.asList(Arrays.copyOf(storage, size));
        sortedStorage.sort((o1, o2) -> {
            int c = o1.getFullName().compareTo(o2.getFullName());
            if (c != 0) {
                return c;
            } else {
                return o1.getUuid().compareTo(o2.getUuid());
            }
        });
        return sortedStorage;
    }

    @Override
    public final int getSize() {
        return size;
    }
}
