package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Collection List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {
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
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected void insertResume(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Object getResume(Object searchKey) {
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

    @Override
    public void clear() {
        storage.clear();

    }

    @Override
    public List<Resume> getAllSorted() {
        ArrayList<Resume> sortedStorage = new ArrayList<>(storage);
        sortedStorage.sort(AbstractStorage.resumeComparator);
        return sortedStorage;
    }

    @Override
    public int getSize() {
        return storage.size();
    }
}