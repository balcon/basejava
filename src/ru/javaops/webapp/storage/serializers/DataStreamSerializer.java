package ru.javaops.webapp.storage.serializers;

import ru.javaops.webapp.model.Resume;

import java.io.*;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(OutputStream outputStream, Resume resume) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();

            return new Resume(uuid, fullName);
        }
    }
}
