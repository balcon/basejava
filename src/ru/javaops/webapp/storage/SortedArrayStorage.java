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
    protected void saveToStorage(Resume resume, int index) {
        // TODO Maybe use System.arraycopy() ???
        int newIndex = -index - 1;
        for (int i = size; i > newIndex; i--) {
            storage[i] = storage[i - 1];
        }
        storage[newIndex] = resume;
    }

    @Override
    protected void deleteFromStorage(int index) {
        // TODO Maybe use System.arraycopy() ???
        for (int i = index; i < size-1; i++) {
            storage[i] = storage[i + 1];
        }
        storage[size-1] = null;
    }

}
