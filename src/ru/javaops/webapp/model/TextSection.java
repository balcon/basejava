package ru.javaops.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private final String content;

    public TextSection(String content) {
        Objects.requireNonNull(content);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
