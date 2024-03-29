package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.serializers.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final StreamSerializer streamSerializer;

    public FileStorage(String path, StreamSerializer streamSerializer) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(streamSerializer);
        File directory = new File(path);
        if (!directory.isDirectory()) {
            throw new StorageException("[" + directory.getAbsolutePath() + "] isn't directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new StorageException("[" + directory.getAbsolutePath() + "] isn't readable/writable");
        }
        this.directory = directory;
        this.streamSerializer = streamSerializer;
    }

    @Override
    protected final void doSave(File file, Resume resume) {
        try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Create error with [" + file.getName() + "]", e);
        }
        doUpdate(file, resume);

    }

    @Override
    protected final Resume doGet(File file) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Read error with [" + file.getName() + "]", e);
        }
    }

    @Override
    protected final void doUpdate(File file, Resume resume) {
        try {
            streamSerializer.doWrite(new BufferedOutputStream(new FileOutputStream(file)), resume);
        } catch (IOException e) {
            throw new StorageException("Write error with [" + file.getName() + "]", e);
        }
    }

    @Override
    protected final void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Delete error with [" + file.getName() + "]");
        }
    }


    @Override
    protected final List<Resume> doCopyAll() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : getFiles()) {
            resumes.add(doGet(file));
        }
        return resumes;
    }

    @Override
    protected final File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected final boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public final void clear() {
        for (File file : getFiles()) {
            doDelete(file);
        }
    }

    @Override
    public final int getSize() {
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
