package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.io.*;
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

    protected abstract void doWrite(OutputStream outputStream, Resume resume) throws IOException;

    protected abstract Resume doRead(InputStream inputStream) throws IOException;

    @Override
    protected void doSave(File file, Resume resume) {
        try {
            if (!file.createNewFile()) {
                throw new StorageException("Create error with [" + file.getName() + "]");
            }
        } catch (IOException e) {
            throw new StorageException("Create error with [" + file.getName() + "]", e);
        }
        doUpdate(file, resume);

    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Read error with [" + file.getName() + "]", e);
        }
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            doWrite(new BufferedOutputStream(new FileOutputStream(file)), resume);
        } catch (IOException e) {
            throw new StorageException("Write error with [" + file.getName() + "]", e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Delete error with [" + file.getName() + "]");
        }
    }


    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : getFiles()) {
            resumes.add(doGet(file));
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

    @Override
    public void clear() {
        for (File file : getFiles()) {
            doDelete(file);
        }
    }

    @Override
    public int getSize() {
        return getFiles().length;
    }

    private File[] getFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Read files error in [" + directory.getName() + "]");
        }
        return files;
    }
}
