package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

/**
 * Unsorted array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected int indexOf(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(resume)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertResumeToArray(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void removeResumeFromArray(int index) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
    }
}
