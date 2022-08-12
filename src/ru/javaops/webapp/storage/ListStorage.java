package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Collection List based storage for Resumes
 */
public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        ListIterator<Resume> iterator = storage.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getUuid().equals(uuid)) {
                return iterator.nextIndex() - 1;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void insertResume(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getResume(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void removeResume(Integer searchKey) {
        storage.remove(searchKey.intValue());
    }

    @Override
    protected void updateResume(Integer searchKey, Resume resume) {
        storage.set(searchKey, resume);
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public void clear() {
        storage.clear();

    }

    @Override
    public int getSize() {
        return storage.size();
    }
}