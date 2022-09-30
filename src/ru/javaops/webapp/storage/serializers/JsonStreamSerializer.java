package ru.javaops.webapp.storage.serializers;

import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.util.JsonParser;

import java.io.*;

public class JsonStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(OutputStream outputStream, Resume resume) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream)) {
            JsonParser.write(resume, writer);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream)) {
            return JsonParser.read(reader);
        }
    }
}
