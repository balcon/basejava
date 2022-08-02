package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

/**
 * Unsorted array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected void insertResumeToArray(int index, Resume resume) { //TODO unused index
        storage[size] = resume;
    }

    @Override
    protected void removeResumeFromArray(int index) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
    }
}
