package ru.javaops.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private final String text;

    public TextSection(String text) {
        Objects.requireNonNull(text);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}
