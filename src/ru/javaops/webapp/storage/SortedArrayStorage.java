package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Sorted array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid, ""));
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected void insertResume(int index, Resume resume) {
        index = -index - 1;
        if (index != size) { // Don't call arraycopy() if resume inserted to last place of storage
            System.arraycopy(storage, index, storage, index + 1, size - index);
        }
        storage[index] = resume;
    }

    @Override
    protected void removeResume(int index) {
        if (index != size - 1) { // Don't call arraycopy() if resume removed from last place of storage
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        }
        storage[size - 1] = null;
    }
}
