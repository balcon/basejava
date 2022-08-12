package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

/**
 * HashMap based storage for Resumes, searched by uuid
 */
public class UuidMapStorage extends AbstractMapStorage<String> {

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected Resume getResume(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void updateResume(String searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    protected void removeResume(String searchKey) {
        storage.remove(searchKey);
    }
}
