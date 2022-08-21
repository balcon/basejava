package ru.javaops.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private final String title;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String description;

    public Period(String title, LocalDate startDate, LocalDate endDate, String description) {
        Objects.requireNonNull(title);
        Objects.requireNonNull(startDate);
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public Period(String title, LocalDate startDate, String description) {
        this(title, startDate, null, description);
    }
//
//    public Period(String title, LocalDate startDate, LocalDate endDate) {
//        this(title, startDate, endDate, "");
//    }

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
