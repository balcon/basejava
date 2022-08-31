package ru.javaops.webapp.storage;

import org.junit.After;
import org.junit.Before;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage("storage_test", new ObjectStreamSerialization()));
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