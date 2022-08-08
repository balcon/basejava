package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractMapStorage extends AbstractStorage {
    protected final HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected void insertResume(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        ArrayList<Resume> sortedStorage = new ArrayList<>(storage.values());
        sortedStorage.sort(AbstractStorage.resumeComparator);
        return sortedStorage;
    }

    @Override
    public int getSize() {
        return storage.size();
    }
}
