package ru.javaops.webapp.storage;

import java.io.File;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(new File("./storage_test")));
    }
}