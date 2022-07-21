package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

public interface Storage {
    void clear();

    void save(Resume resume);

    Resume get(String uuid);

    void update(Resume resume);

    void delete(String uuid);

    Resume[] getAll();

    int getSize();
}
