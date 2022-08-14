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
    protected boolean isExist(String key) {
        return storage.containsKey(key);
    }

    @Override
    protected Resume doGet(String key) {
        return storage.get(key);
    }

    @Override
    protected void doUpdate(String key, Resume resume) {
        storage.put(key, resume);
    }

    @Override
    protected void doDelete(String key) {
        storage.remove(key);
    }
}
