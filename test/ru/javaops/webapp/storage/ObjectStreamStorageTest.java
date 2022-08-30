package ru.javaops.webapp.storage;

import org.junit.Before;

import java.io.File;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(new File("storage_test")));
    }

    @Override
    @Before
    public void setUp() {
        storage.clear();
        super.setUp();
    }
}