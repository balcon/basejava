package ru.javaops.webapp.storage;

import org.junit.Before;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage());
    }

    @Override
    @Before
    public void setUp() {
        storage.clear();
        super.setUp();
    }
}