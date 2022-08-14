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
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected void doSave(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void doDelete(Integer index) {
        storage.remove(index.intValue());
    }

    @Override
    protected void doUpdate(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected List<Resume> doCopyAll() {
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