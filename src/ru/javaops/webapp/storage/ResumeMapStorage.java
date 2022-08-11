package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

public class ResumeMapStorage extends AbstractMapStorage {

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey!=null;
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
}
