package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.DataStreamSerializer;

public class DataStreamPathStorageTest extends AbstractFileSystemStorageTest {
    public DataStreamPathStorageTest() {
        super(new PathStorage("storage_test", new DataStreamSerializer()));
    }
}