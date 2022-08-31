package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final SerializationType serializationType;

    public PathStorage(String directoryPath, SerializationType serializationType) {
        Objects.requireNonNull(directoryPath);
        Path directory = Paths.get(directoryPath);
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
    protected void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Create error with [" + path.toAbsolutePath() + "]", e);
        }
        doUpdate(path, resume);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serializationType.doRead(new BufferedInputStream(new FileInputStream(path.toString())));
        } catch (IOException e) {
            throw new StorageException("Read error with [" + path.toAbsolutePath() + "]", e);
        }
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            serializationType.doWrite(new BufferedOutputStream(new FileOutputStream(path.toString())), resume);
        } catch (IOException e) {
            throw new StorageException("Write error with [" + path.toAbsolutePath() + "]", e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Delete error with [" + path.toAbsolutePath() + "]", e);
        }
    }


    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> resumes = new ArrayList<>();
        for (Path path : getPaths()) {
            resumes.add(doGet(path));
        }
        return resumes;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        // TODO need better, remove toString
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    public void clear() {
        for (Path path : getPaths()) {
            doDelete(path);
        }
    }

    @Override
    public int getSize() {
        return getPaths().length;
    }

    private Path[] getPaths() {
        Path[] arrayPaths=new Path[0];
        try {
            List<Path> listPaths = new ArrayList<>();
            DirectoryStream<Path> paths = Files.newDirectoryStream(directory);
            for (Path path : paths) {
                listPaths.add(path);
            }
            arrayPaths = listPaths.toArray(arrayPaths);
        } catch (IOException e) {
            throw new StorageException("Read paths error in [" + directory.toAbsolutePath() + "]");
        }
        return arrayPaths;
    }

}
