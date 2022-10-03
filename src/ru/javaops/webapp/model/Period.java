package ru.javaops.webapp.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Period implements Serializable, Comparable<Period> {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final LocalDate NOW = LocalDate.of(1, 1, 1);

    private final String title;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String description;

    public Period(String title, LocalDate startDate, LocalDate endDate, String description) {
        this.title = Objects.requireNonNull(title);
        this.startDate = Objects.requireNonNull(startDate);
        this.endDate = Objects.requireNonNullElse(endDate, NOW);
        this.description = Objects.toString(description, "");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        if (!title.equals(period.title)) return false;
        if (!startDate.equals(period.startDate)) return false;
        if (!Objects.equals(endDate, period.endDate)) return false;
        return Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return title + ": " + startDate + " - " + endDate + ", " + description;
    }

    @Override
    public int compareTo(Period o) {
        return startDate.compareTo(o.startDate);
    }
}
