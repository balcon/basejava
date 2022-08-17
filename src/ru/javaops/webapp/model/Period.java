package ru.javaops.webapp.model;

import java.time.LocalDate;

public class Period {
    private final String title;
    private final LocalDate startDate;
    private final LocalDate endDate; // TODO do update for endDate
    private final String description;

    public Period(String title, LocalDate startDate, LocalDate endDate, String description) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }
}
