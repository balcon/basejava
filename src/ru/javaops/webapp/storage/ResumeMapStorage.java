package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResumeMapStorage extends AbstractStorage {
    private final HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsValue((Resume) searchKey);
    }

    @Override
    protected void insertResume(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void updateResume(Object searchKey, Resume resume) {
        storage.put(((Resume) searchKey).getUuid(), resume);
    }

    @Override
    protected void removeResume(Object searchKey) {
        storage.values().remove((Resume) searchKey);
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
