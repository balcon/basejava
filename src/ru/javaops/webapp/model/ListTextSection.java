package ru.javaops.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListTextSection extends AbstractSection {
    private final List<String> content = new ArrayList<>();

    public void add(String content) {
        Objects.requireNonNull(content);
        this.content.add(content);
    }

    public List<String> getContent() {
        return content;
    }
}
