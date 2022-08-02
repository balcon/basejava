package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int getSize() {
        return storage.size();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }


    @Override
    protected void insertResume(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected void removeResume(Object searchKey) {
        storage.remove((int) searchKey);
    }


    @Override
    protected void updateResume(Object searchKey, Resume resume) {
        storage.set((int) searchKey, resume);
    }
}
