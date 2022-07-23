package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int indexOf(String uuid) {
        Resume resume = new Resume();
        resume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, resume);
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        int newIndex = -index - 1;
        if (size - newIndex > 0) { // Don't call arraycopy() if resume insert to last position in storage
            System.arraycopy(storage, newIndex, storage, newIndex + 1, size - newIndex);
        }
        storage[newIndex] = resume;
    }

    @Override
    protected void removeResume(int index) {
        if (size - index - 1 > 0) { // Don't call arraycopy() if remove last resume in storage
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        }
        storage[size - 1] = null;
    }

}
