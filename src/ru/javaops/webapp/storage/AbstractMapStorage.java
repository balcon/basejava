package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractMapStorage<K> extends AbstractStorage<K> {
    protected final HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected void doSave(K key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int getSize() {
        return storage.size();
    }
}
