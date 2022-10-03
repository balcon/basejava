package ru.javaops.webapp.web;

import ru.javaops.webapp.model.ContactType;

public class Hyperlink {
    public static String of(ContactType type, String value) {
        return switch (type) {
            case PHONE -> actionLink(type, value, "tel:");
            case TELEGRAM -> actionLink(type, value, "https://t.me/");
            case SKYPE -> actionLink(type, value, "skype:");
            case MAIL -> actionLink(type, value, "mailto:");
            case LINKEDIN, STACKOVERFLOW, GITHUB, HOMEPAGE ->
                    "<a href=\"" + value + "\">" + type.getTitle() + "</a>";
        };
    }

    private static String actionLink(ContactType type, String value, String action) {
        return "<b>" + type.getTitle() + ":</b> <a href=\"" + action + value + "\">" + value + "</a>";
    }
}
