package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.List;

public interface Storage {
    void clear();

    void save(Resume resume);

    Object get(String uuid);

    void update(Resume resume);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int getSize();
}
