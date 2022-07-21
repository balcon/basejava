package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private static final int STORAGE_CAPACITY = 10000;

    private int size = 0;
    private final Resume[] storage = new Resume[STORAGE_CAPACITY];

    private int indexOf(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (size >= STORAGE_CAPACITY) {
            System.out.println("Storage contains maximum number of resumes");
        } else if (index != -1) {
            System.out.println("Resume [" + resume.getUuid() + "] already exists");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index == -1) {
            System.out.println("Resume [" + uuid + "] is not contained in the storage");
            return null;
        }
        return storage[index];
    }

    public void update(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index == -1) {
            System.out.println("Resume [" + resume.getUuid() + "] is not contained in the storage");
        } else {
            storage[index] = resume;
        }

    }

    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index == -1) {
            System.out.println("Resume [" + uuid + "] is not contained in the storage");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
