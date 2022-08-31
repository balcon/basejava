package ru.javaops.webapp.storage;

import org.junit.After;
import org.junit.Before;
import ru.javaops.webapp.storage.serializers.ObjectStreamSerializer;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage("storage_test", new ObjectStreamSerializer()));
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