package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistsStorageException;
import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void insertResume(int index, Resume resume);

    protected abstract Resume getResume(int index);

    protected abstract void updateResume(int index, Resume resume);

    protected abstract void removeResume(int index);

    protected abstract int indexOf(String uuid);

    //    protected abstract boolean isExist(Object searchKey);
////
//    private int getExisting(String uuid) {
//        Resume resume = new Resume(uuid);
//        int index = indexOf(resume);
//        if (index < 0) {
//            throw new NotExistsStorageException(resume);
//        }
//        return
//    }

//    private int notExistingSearchKey(String uuid) {
//        if (indexOf(new Resume(uuid)))
//    }

    @Override
    public final void save(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index >= 0) {
            throw new ExistsStorageException(resume.getUuid());
        } else {
            insertResume(index, resume);
        }
    }

    @Override
    public final Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index < 0) {
            throw new NotExistsStorageException(uuid);
        }
        return getResume(index);
    }

    @Override
    public final void delete(String uuid) {
        int index = indexOf(uuid);
        if (index < 0) {
            throw new NotExistsStorageException(uuid);
        } else {
            removeResume(index);
        }
    }

    @Override
    public final void update(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index < 0) {
            throw new NotExistsStorageException(resume.getUuid());
        } else {
            updateResume(index, resume);
        }
    }
}

