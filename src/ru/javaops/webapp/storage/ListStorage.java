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
    protected int indexOf(Resume resume) {
        return storage.indexOf(resume);
    }

    // TODO Unused int index... ¯\_(ツ)_/¯
    @Override
    protected void insertResume(int index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    protected void removeResume(int index) {
        storage.remove(index);
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        storage.set(index, resume);
    }
}
