package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Sorted array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected void insertResumeToArray(Resume resume) {
        int searchKey = getSearchKey(resume.getUuid());
        int indes = -searchKey - 1;
        if (indes != size) { // Don't call arraycopy() if resume inserted to last place of storage
            System.arraycopy(storage, indes, storage, indes + 1, size - indes);
        }
        storage[indes] = resume;
    }

    @Override
    protected void removeResumeFromArray(int index) {
        if (index != size - 1) { // Don't call arraycopy() if resume removed from last place of storage
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        }
        storage[size - 1] = null;
    }
}
