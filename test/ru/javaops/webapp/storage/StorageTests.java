package ru.javaops.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        UuidMapStorageTest.class,
        ResumeMapStorageTest.class,
        ObjectStreamFileStorageTest.class,
        ObjectStreamPathStorageTest.class,
        DataStreamPathStorageTest.class,
        JsonStreamPathStorageTest.class,
//        SqlStorageTest.class
})
public class StorageTests {
}
