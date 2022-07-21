package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Abstract array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    private static final int STORAGE_CAPACITY = 10000;

    protected final Resume[] storage = new Resume[STORAGE_CAPACITY];
    protected int size = 0;

    protected abstract int indexOf(String uuid);

    protected abstract void saveToStorage(Resume resume, int index);

    protected abstract void deleteFromStorage(int index);

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (size >= STORAGE_CAPACITY) {
            System.out.println("Storage contains maximum number of resumes");
        } else if (index >= 0) {
            System.out.println("Resume [" + resume.getUuid() + "] already exists");
        } else {
            saveToStorage(resume, index);
            size++;
        }

    }

    @Override
    public final Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index < 0) {
            System.out.println("Resume [" + uuid + "] is not contained in the storage");
            return null;
        }
        return storage[index];
    }

    @Override
    public final void update(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index < 0) {
            System.out.println("Resume [" + resume.getUuid() + "] is not contained in the storage");
        } else {
            storage[index] = resume;
        }

    }

    @Override
    public final void delete(String uuid) {
        int index = indexOf(uuid);
        if (index < 0) {
            System.out.println("Resume [" + uuid + "] is not contained in the storage");
        } else {
            deleteFromStorage(index);
            size--;
        }
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
