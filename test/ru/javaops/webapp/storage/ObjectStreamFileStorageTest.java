package ru.javaops.webapp.storage;

import org.junit.After;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage("storage_test", new ObjectStreamSerialization()));
    }

    @After
    public void tearDown() {
        storage.clear();
    }
}