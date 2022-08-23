package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File>{
    private final File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory);
        String path = directory.getAbsolutePath();
        if (!directory.isDirectory()){
            throw new StorageException(path + " isn't directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new StorageException(path + " isn't readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
            doWrite(file, resume);
        } catch (IOException e) {
            throw new StorageException("IO error ["+file.getName()+"]", e);
        }
    }

    protected abstract void doWrite(File file, Resume resume);

    @Override
    protected Resume doGet(File searchKey) {
        return null;
    }

    @Override
    protected void doUpdate(File searchKey, Resume resume) {

    }

    @Override
    protected void doDelete(File searchKey) {

    }

    @Override
    protected List<Resume> doCopyAll() {
        return null;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void clear() {

    }

    @Override
    public int getSize() {
        return 0;
    }
}
