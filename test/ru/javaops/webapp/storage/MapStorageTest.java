package ru.javaops.webapp.storage;

import org.junit.Test;
import ru.javaops.webapp.model.Resume;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    @Test
    public void getAllSorted() {
        List<Resume> resumes = storage.getAllSorted();
        assertTrue(resumes.contains(RESUME_1));
        assertTrue(resumes.contains(RESUME_2));
        assertTrue(resumes.contains(RESUME_3));
    }
}