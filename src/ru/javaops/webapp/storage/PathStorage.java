package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final SerializationType serializationType;

    public PathStorage(String path, SerializationType serializationType) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(serializationType);
        Path directory = Paths.get(path);
        if (!Files.isDirectory(directory)) {
            throw new StorageException("[" + directory.toAbsolutePath() + "] isn't directory");
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new StorageException("[" + directory.toAbsolutePath() + "] isn't readable/writable");
        }
        this.directory = directory;
        this.serializationType = serializationType;
    }

    @Override
    protected final void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Create error with [" + path.toAbsolutePath() + "]", e);
        }
        doUpdate(path, resume);
    }

    @Override
    protected final Resume doGet(Path path) {
        try {
            return doRead(path);
        } catch (IOException e) {
            throw new StorageException("Read error with [" + path.toAbsolutePath() + "]", e);
        }
    }

    @Override
    protected final void doUpdate(Path path, Resume resume) {
        try {
            doWrite(path, resume);
        } catch (IOException e) {
            throw new StorageException("Write error with [" + path.toAbsolutePath() + "]", e);
        }
    }

    @Override
    protected final void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Delete error with [" + path.toAbsolutePath() + "]", e);
        }
    }

    @Override
    protected final List<Resume> doCopyAll() {
        List<Resume> resumes = new ArrayList<>();
        getFileList().map(this::doGet).forEach(resumes::add);
        return resumes;
    }

    @Override
    protected final Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected final boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    public final void clear() {
        getFileList().forEach(this::doDelete);
    }

    @Override
    public final int getSize() {
        return (int) getFileList().count();
    }

    private Stream<Path> getFileList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Get list of files error in [" + directory.toAbsolutePath() + "]", e);
        }
    }

    private Resume doRead(Path path) throws IOException {
        return serializationType.doRead(new BufferedInputStream(Files.newInputStream(path)));
    }

    private void doWrite(Path path, Resume resume) throws IOException {
        serializationType.doWrite(new BufferedOutputStream(Files.newOutputStream(path)), resume);
    }

}
