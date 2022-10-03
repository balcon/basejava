package ru.javaops.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    SKYPE("Skype"),
    TELEGRAM("Telegram"),
    MAIL("E-mail"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
