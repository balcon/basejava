package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.ObjectStreamSerializer;

public class ObjectStreamPathStorageTest extends AbstractFileSystemStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage("storage_test", new ObjectStreamSerializer()));
    }
}