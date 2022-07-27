package ru.javaops.webapp.model;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ResumeTest {
    Resume r1 = new Resume("u1");
    Resume r2 = new Resume("u1");
    Resume r3 = new Resume("u2");
    Resume r4 = new Resume("a1");

    @Test
    public void checkEquals() {
        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
    }

    @Test
    public void checkHashCode() {
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotEquals(r1.hashCode(), r3.hashCode());
    }

    @Test
    public void checkComparison() {
        Resume[] resumes={r1,r2,r3,r4};
        Arrays.sort(resumes);

        assertEquals(r4,resumes[0]);
        assertEquals(r3,resumes[3]);
    }
}