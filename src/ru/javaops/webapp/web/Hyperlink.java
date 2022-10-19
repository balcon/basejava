package ru.javaops.webapp.web;

import ru.javaops.webapp.model.ContactType;

public class Hyperlink {

    public static String of(ContactType type, String value) {
        return switch (type) {
            case PHONE -> "tel:" + value;
            case TELEGRAM -> "https://t.me/" + value;
            case SKYPE -> "skype:" + value;
            case MAIL -> "mailto:" + value;
            default -> value;
        };
    }

    public static String iconOf(ContactType contactType) {
        return switch (contactType) {
            case PHONE -> "fa fa-phone";
            case TELEGRAM -> "fa-brands fa-telegram";
            case SKYPE -> "fa-brands fa-skype";
            case MAIL -> "fa fa-envelope";
            case LINKEDIN -> "fa-brands fa-linkedin";
            case STACKOVERFLOW -> "fa-brands fa-stack-overflow";
            case GITHUB -> "fa-brands fa-github";
            case HOMEPAGE -> "fa fa-house";
        };
    }

}
