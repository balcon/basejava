package ru.javaops.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private final String content;

    public TextSection(String content) {
        Objects.requireNonNull(content);
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
