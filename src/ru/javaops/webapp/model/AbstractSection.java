package ru.javaops.webapp.model;

public abstract class AbstractSection {
    private final String title;

    public AbstractSection(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
