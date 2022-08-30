package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamStorage extends AbstractFileStorage{

    public ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    protected void doWrite(OutputStream outputStream, Resume resume) throws IOException {

    }

    @Override
    protected Resume doRead(InputStream inputStream) throws IOException {
        return null;
    }
}
