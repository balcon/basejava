package ru.javaops.webapp.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResumeTest {
    @Test
    public void equals() {
        Resume r1 = new Resume("u1");
        Resume r2 = new Resume("u1");
        Resume r3 = new Resume("u2");

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
    }
}