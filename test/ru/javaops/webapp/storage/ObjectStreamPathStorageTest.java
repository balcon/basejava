package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.ObjectStreamSerializer;
import ru.javaops.webapp.storage.util.Config;

public class ObjectStreamPathStorageTest extends AbstractFileSystemStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(Config.get().getStorageTestDir(), new ObjectStreamSerializer()));
    }
}