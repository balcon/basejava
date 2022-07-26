package ru.javaops.webapp.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javaops.webapp.exception.ExistsStorageException;
import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void getSize() {
        assertEquals(3, storage.getSize());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.getSize());
    }

    @Test
    public void save() {
        storage.save(new Resume("uui4"));
        assertEquals(4, storage.getSize());
    }

    @Test(expected = ExistsStorageException.class)
    public void saveIfExists() {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void storageOverflow() throws NoSuchFieldException, IllegalAccessException {
        Field storage_capacity = AbstractArrayStorage.class.getDeclaredField("STORAGE_CAPACITY");
        storage_capacity.setAccessible(true);
        int maxCapacity = (int) storage_capacity.get(null);
        storage.clear();
        // Очень странная конструкция с fail(), но так требует задание
        try {
            for (int i = 0; i < maxCapacity; i++) {
                storage.save(new Resume("_uuid" + i));
            }
        } catch (StorageException e) {
            fail("Unexpected overflow");
        }
        // Save and overflow
        storage.save(new Resume("overCapacity"));

    }

    @Test
    public void get() {
        assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test(expected = NotExistsStorageException.class)
    public void getIfNotExists() {
        storage.get("notExist");
    }

    @Test(expected = NotExistsStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.getSize());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistsStorageException.class)
    public void deleteIfNotExists() {
        storage.delete("notExists");
    }

    @Test
    public void update() {
        Resume r1 = new Resume(UUID_1);
        storage.update(r1);
        assertEquals(r1, storage.get(UUID_1));
    }

    @Test(expected = NotExistsStorageException.class)
    public void updateIfNotExists() {
        storage.update(new Resume("notExists"));
    }

    @Test
    public void getAll() {
        Resume[] resumes = {new Resume(UUID_1),
                new Resume(UUID_2),
                new Resume(UUID_3)};
        assertArrayEquals(resumes, storage.getAll());
    }
}