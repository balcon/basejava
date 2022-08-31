package ru.javaops.webapp.storage;

import org.junit.After;
import org.junit.Before;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage("storage_test", new ObjectStreamSerialization()));
    }

    @Override
    @Before
    public void setUp() {
        tearDown();
        super.setUp();
    }

    @After
    public void tearDown() {
        storage.clear();
    }
}