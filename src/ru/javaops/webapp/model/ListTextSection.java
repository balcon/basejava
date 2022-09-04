package ru.javaops.webapp.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListTextSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<String> textList;

    public ListTextSection() {
        textList = new ArrayList<>();
    }

    public ListTextSection(List<String> listText) {
        this.textList = listText;
    }

    public void add(String text) {
        this.textList.add(Objects.requireNonNull(text));
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
