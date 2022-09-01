package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.ObjectStreamSerializer;

public class ObjectStreamFileStorageTest extends AbstractFileSystemStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new FileStorage("storage_test", new ObjectStreamSerializer()));
    }
}