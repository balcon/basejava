package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.ObjectStreamSerializer;
import ru.javaops.webapp.storage.util.Config;

public class ObjectStreamFileStorageTest extends AbstractFileSystemStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new FileStorage(Config.get().getStorageTestDir(), new ObjectStreamSerializer()));
    }
}