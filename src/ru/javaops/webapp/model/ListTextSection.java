package ru.javaops.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListTextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private final List<String> textList = new ArrayList<>();

    public void add(String text) {
        Objects.requireNonNull(text);
        this.textList.add(text);
    }

    public List<String> getTextList() {
        return textList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListTextSection that = (ListTextSection) o;
        return textList.equals(that.textList);
    }

    @Override
    public int hashCode() {
        return textList.hashCode();
    }

    @Override
    public String toString() {
        return textList.toString();
    }
}
