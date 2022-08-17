package ru.javaops.webapp.model;

public class TextSection extends AbstractSection {
    private String text;

    public TextSection(String title, String text) {
        super(title);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
