package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistsStorageException;
import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save(Resume resume) {
        if (storage.contains(resume)) {
            throw new ExistsStorageException(resume);
        }
        storage.add(resume);
    }

    @Override
    public Resume get(String uuid) {
        int index = storage.indexOf(new Resume(uuid));
        if (index == -1) {
            throw new NotExistsStorageException(new Resume(uuid));
        }
        return storage.get(index);
    }

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
}
