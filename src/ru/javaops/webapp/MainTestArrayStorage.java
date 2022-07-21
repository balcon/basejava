package ru.javaops.webapp;

import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.SortedArrayStorage;
import ru.javaops.webapp.storage.Storage;

/**
 * Test for your ru.javaops.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");
        Resume r4 = new Resume();
        r4.setUuid("uuid2");
        Resume r5 = new Resume();
        r5.setUuid("uuid5");

        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.update(r4);

        System.out.println("Save duplicate");
        ARRAY_STORAGE.save(r4);
        System.out.println("Update wrong resume");
        ARRAY_STORAGE.update(r5);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.getSize());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        System.out.println("Remove uuid1");
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.getSize());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
