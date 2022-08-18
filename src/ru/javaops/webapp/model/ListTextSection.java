package ru.javaops.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListTextSection extends AbstractSection {
    private final List<String> texts = new ArrayList<>();

    public ListTextSection(String title) {
    }

    public void newText(String text) {
        texts.add(text);
    }

    //TODO do remove
    //TODO do update by index
    //TODO for getting Texts implement Iterator
}
