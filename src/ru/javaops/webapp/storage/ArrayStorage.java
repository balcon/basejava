package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

/**
 * Unsorted array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected int indexOf(String uuid) {
        Resume resume = new Resume();
        resume.setUuid(uuid);
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(resume)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void saveToStorage(Resume resume, int index) {
        storage[index]=resume;
    }
}
