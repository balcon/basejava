package ru.javaops.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    SKYPE("Skype"),
    MAIL("E-mail"),
    TELEGRAM("Telegram");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
