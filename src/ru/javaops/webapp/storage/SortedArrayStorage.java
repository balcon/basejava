package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Sorted array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int indexOf(Resume resume) {
        return Arrays.binarySearch(storage, 0, size, resume);
    }

    @Override
    protected void insertResumeToArray(Resume resume, int index) {
        int newIndex = -index - 1;
        if (newIndex != size) { // Don't call arraycopy() if resume inserted to last place of storage
            System.arraycopy(storage, newIndex, storage, newIndex + 1, size - newIndex);
        }
        storage[newIndex] = resume;
    }

    @Override
    protected void removeResumeFromArray(int index) {
        if (index != size - 1) { // Don't call arraycopy() if resume removed from last place of storage
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        }
        storage[size - 1] = null;
    }

}
