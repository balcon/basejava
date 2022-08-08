package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

public class UuidMapStorage extends AbstractMapStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void updateResume(Object searchKey, Resume resume) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected void removeResume(Object searchKey) {
        storage.remove((String) searchKey);
    }
}
