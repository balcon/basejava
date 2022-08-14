package ru.javaops.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    TELEGRAM("Telegram"),
    MAIL("E-mail"),
    SKYPE("Skype");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
