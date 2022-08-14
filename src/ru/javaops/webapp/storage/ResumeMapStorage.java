package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

/**
 * HashMap based storage for Resumes, searched by resume
 */
public class ResumeMapStorage extends AbstractMapStorage<Resume> {

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume key) {
        return key != null;
    }

    @Override
    protected Resume doGet(Resume key) {
        return key;
    }

    @Override
    protected void doUpdate(Resume key, Resume resume) {
        storage.put(key.getUuid(), resume);
    }

    @Override
    protected void doDelete(Resume key) {
        storage.values().remove(key);
    }
}
