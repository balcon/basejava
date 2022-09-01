package ru.javaops.webapp.storage;

import org.junit.Before;

public abstract class AbstractFileSystemStorageTest extends AbstractStorageTest {
    protected AbstractFileSystemStorageTest(Storage storage) {
        super(storage);
    }

    @Override
    @Before
    public void setUp() {
        storage.clear();
        super.setUp();
    }
}
