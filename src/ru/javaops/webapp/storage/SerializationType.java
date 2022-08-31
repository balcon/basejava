package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationType {

    void doWrite(OutputStream outputStream, Resume resume) throws IOException;

    Resume doRead(InputStream inputStream) throws IOException;
}
