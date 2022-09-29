package ru.javaops.webapp.storage;

import org.junit.Before;
import ru.javaops.webapp.storage.util.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.get().getStorage());
    }
    @Override
    @Before
    public void setUp() {
        storage.clear();
        super.setUp();
    }
}