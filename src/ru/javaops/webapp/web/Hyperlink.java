package ru.javaops.webapp.web;

import ru.javaops.webapp.model.ContactType;

import java.util.Map.Entry;

public class Hyperlink {
    public static String of(Entry<ContactType, String> contact) {
        ContactType type = contact.getKey();
        String value = contact.getValue();
        return switch (type) {
            case PHONE -> actionLink(type, value, "tel:");
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
