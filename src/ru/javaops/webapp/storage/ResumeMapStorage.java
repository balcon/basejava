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
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected Resume getResume(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void updateResume(Resume searchKey, Resume resume) {
        storage.put(searchKey.getUuid(), resume);
    }

    @Override
    protected void removeResume(Resume searchKey) {
        storage.values().remove(searchKey);
    }
}
