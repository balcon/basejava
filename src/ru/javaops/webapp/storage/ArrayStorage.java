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
    protected void insertResumeToArray(Object searchKey, Resume resume) {
        storage[size] = resume;
    }


    @Override
    protected void removeResumeFromArray(Object searchKey) {
        storage[(int) searchKey] = storage[size - 1];
        storage[size - 1] = null;
    }
}
