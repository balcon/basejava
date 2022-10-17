package ru.javaops.webapp.web;

import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Organization;

public class Hyperlink {

    public static String of(ContactType type, String value) {
        return switch (type) {
            case PHONE -> actionLink(type, value, "tel:");
            case TELEGRAM -> actionLink(type, value, "https://t.me/");
            case SKYPE -> actionLink(type, value, "skype:");
            case MAIL -> actionLink(type, value, "mailto:");
            case LINKEDIN, STACKOVERFLOW, GITHUB, HOMEPAGE -> "<a href=\"" + value + "\">" + type.getTitle() + "</a>";
        };
    }

    public static String of(Organization organization) {
        if (!organization.getHomepage().isEmpty()) {
            return "<a href=\"" + organization.getHomepage() + "\">" + organization.getName() + "</a>";
        } else {
            return "<u>" + organization.getName() + "</u>";
        }
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

    private static String actionLink(ContactType type, String value, String action) {
        return "<b>" + type.getTitle() + ":</b> <a href=\"" + action + value + "\">" + value + "</a>";
    }
}
