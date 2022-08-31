package ru.javaops.webapp.storage;

import org.junit.After;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage("storage_test", new ObjectStreamSerialization()));
    }

    @After
    public void tearDown() {
        storage.clear();
    }
}