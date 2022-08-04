package ru.javaops.webapp.storage;

import org.junit.Test;
import ru.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    @Test
    public void getAll() {
        Resume[] resumes = storage.getAll();
        List<Resume> resumesList = Arrays.asList(resumes);
        assertTrue(resumesList.contains(RESUME_1));
        assertTrue(resumesList.contains(RESUME_2));
        assertTrue(resumesList.contains(RESUME_3));
    }
}