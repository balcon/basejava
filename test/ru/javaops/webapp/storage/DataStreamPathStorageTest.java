package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.DataStreamSerializer;
import ru.javaops.webapp.storage.util.Config;

public class DataStreamPathStorageTest extends AbstractFileSystemStorageTest {
    public DataStreamPathStorageTest() {
        super(new FileStorage(Config.get().getStorageTestDir(), new DataStreamSerializer()));
    }
}