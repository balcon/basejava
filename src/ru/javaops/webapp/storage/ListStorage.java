package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * LCollection ist based storage for Resumes
 */
public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

//    @Override
//    public void save(Resume resume) {
//        if (storage.contains(resume)) {
//            throw new ExistsStorageException(resume);
//        }
//        storage.add(resume);
//    }

    @Override
    public void update(Resume resume) {
        int index = storage.indexOf(resume);
        if (index == -1) {
            throw new NotExistsStorageException(resume);
        }
        storage.set(index, resume);
    }

    @Override
    public void delete(String uuid) {
        if (!storage.remove(new Resume(uuid))) {
            throw new NotExistsStorageException(new Resume(uuid));
        }
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
    protected int indexOf(Resume resume) {
        return storage.indexOf(resume);
    }

    @Override
    protected void save_(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected Resume get_(int index) {
        return storage.get(index);
    }
}
