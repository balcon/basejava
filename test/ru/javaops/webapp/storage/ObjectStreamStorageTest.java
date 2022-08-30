package ru.javaops.webapp.storage;

import org.junit.After;

import java.io.File;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(new File("storage_test")));
    }

    @After
    public void tearDown() {
        storage.clear();
    }
}