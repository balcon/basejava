package ru.javaops.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListTextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private final List<String> content = new ArrayList<>();

    public void add(String content) {
        Objects.requireNonNull(content);
        this.content.add(content);
    }

    public List<String> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListTextSection that = (ListTextSection) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
