package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private static final int CAPACITY = 10000;

    private int size = 0;
    private final Resume[] storage = new Resume[CAPACITY];

    private int indexOf(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        int index=indexOf(r.uuid);
        if (index!=-1){
            System.out.println("Resume [" + r.uuid + "] is already in the storage");
            return;
        }
        if (size >= CAPACITY) {
            System.out.println("Storage contains maximum number of resumes");
            return;
        }
        storage[size] = r;
        size++;
    }

    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.println("Resume [" + uuid + "] is not contained in the storage");
        return null;
    }

    public void update(Resume resume) {
        int index=indexOf(resume.uuid);
        if (index!=-1){
            storage[index]=resume;
        } else {
            System.out.println("Resume [" + resume.uuid + "] is not contained in the storage");
        }

    }

    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume [" + uuid + "] is not contained in the storage");
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
