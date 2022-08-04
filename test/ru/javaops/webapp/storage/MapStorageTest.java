package ru.javaops.webapp.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    @Test
    public void getAll() {
        Resume[] resumes=storage.getAll();
        List<Resume> resumesList = Arrays.asList(resumes);
        Assert.assertTrue(resumesList.contains(RESUME_1));
        Assert.assertTrue(resumesList.contains(RESUME_2));
        Assert.assertTrue(resumesList.contains(RESUME_3));
    }
}