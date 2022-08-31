package ru.javaops.webapp.storage.serializers;

import ru.javaops.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {

    void doWrite(OutputStream outputStream, Resume resume) throws IOException;

    Resume doRead(InputStream inputStream) throws IOException;
}
