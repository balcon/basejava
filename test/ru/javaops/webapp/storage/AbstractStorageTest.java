package ru.javaops.webapp.storage;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.javaops.webapp.ResumeTestData;
import ru.javaops.webapp.exception.ExistsStorageException;
import ru.javaops.webapp.exception.NotExistsStorageException;
import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Resume;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_EXISTS = "uuid_not_exists";

    protected static final Resume RESUME_1 = ResumeTestData.buildResume(UUID_1, "John Doe");
    protected static final Resume RESUME_2 = ResumeTestData.buildResume(UUID_2, "John Doe");
    protected static final Resume RESUME_3 = ResumeTestData.buildResume(UUID_3, "Homer Simpson");
    protected static final Resume RESUME_4 = ResumeTestData.buildResume(UUID_4, "Philip Fry");
    protected static final Resume RESUME_NOT_EXISTS = ResumeTestData.buildResume(UUID_NOT_EXISTS, "Anonymous");

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    private void assertSize(int size) {
        assertEquals(size, storage.getSize());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertGetAll(Resume[] resumes) {
        assertArrayEquals(resumes, storage.getAllSorted().toArray());
    }

    @Before
    public void setUp() {
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistsStorageException.class)
    public void saveExisting() {
        storage.save(RESUME_1);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistsStorageException.class)
    public void getNotExisting() {
        storage.get(UUID_NOT_EXISTS);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1, "Jimmy White");
        resume.setContact(ContactType.SKYPE, "New Skype");
//        resume.setSection(SectionType.PERSONAL, new TextSection("New Personal"));
        storage.update(resume);
        assertEquals(resume, storage.get(UUID_1));
    }

    @Test(expected = NotExistsStorageException.class)
    public void updateNotExisting() {
        storage.update(RESUME_NOT_EXISTS);
    }

    @Test(expected = NotExistsStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistsStorageException.class)
    public void deleteNotExisting() {
        storage.delete(UUID_NOT_EXISTS);
    }

    @Test
    public void getSize() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        assertGetAll(new Resume[0]);
    }

    @Test
    public void getAllSorted() {
        Resume[] resumes = {RESUME_3, RESUME_1, RESUME_2};
        assertGetAll(resumes);
    }
}