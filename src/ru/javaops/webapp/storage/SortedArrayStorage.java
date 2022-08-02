package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Sorted array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected void insertResumeToArray(Object searchKey, Resume resume) {
        int index = -(int) searchKey - 1;
        if (index != size) { // Don't call arraycopy() if resume inserted to last place of storage
            System.arraycopy(storage, index, storage, index + 1, size - index);
        }
        storage[index] = resume;
    }


    @Override
    protected void removeResumeFromArray(Object searchKey) {
        int index = (int) searchKey;
        if (index != size - 1) { // Don't call arraycopy() if resume removed from last place of storage
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        }
        storage[size - 1] = null;
    }

}
