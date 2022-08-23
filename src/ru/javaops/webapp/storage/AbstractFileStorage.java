package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory);
        String path = directory.getAbsolutePath();
        if (!directory.isDirectory()) {
            throw new StorageException("[" + path + "] isn't directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new StorageException("[" + path + "] isn't readable/writable");
        }
        this.directory = directory;
    }

    @SuppressWarnings("all")
    @Override
    protected void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
            doWrite(file, resume);
        } catch (IOException e) {
            throw new StorageException("IO error [" + file.getName() + "]", e);
        }
    }

    protected abstract void doWrite(File file, Resume resume);

    @Override
    protected Resume doGet(File file) {
        return doRead(file);
    }

    protected abstract Resume doRead(File file);

    @Override
    protected void doUpdate(File file, Resume resume) {
        doWrite(file, resume);
    }

    @SuppressWarnings("all")
    @Override
    protected void doDelete(File file) {
        file.delete();
    }


    @SuppressWarnings("all")
    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : directory.listFiles()) {
            resumes.add(doRead(file));
        }
        return resumes;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @SuppressWarnings("all")
    @Override
    public void clear() {
        for (File file : directory.listFiles()) {
            file.delete();
        }
    }

    @SuppressWarnings("all")
    @Override
    public int getSize() {
        return directory.listFiles().length;
    }
}
