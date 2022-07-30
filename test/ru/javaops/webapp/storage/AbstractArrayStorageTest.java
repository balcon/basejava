package ru.javaops.webapp.storage;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);

    }

//    @Test(expected = StorageException.class)
//    public void storageOverflow() {
//        storage.clear();
//        try {
//            for (int i = 0; i < AbstractArrayStorage.STORAGE_CAPACITY; i++) {
//                storage.save(new Resume("uuid" + i));
//            }
//        } catch (StorageException e) {
//            fail("Unexpected overflow");
//        }
//        // Save and overflow
//        storage.save(RESUME_1);
//    }
}