package ru.javaops.webapp.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String homepage;
    private final List<Period> periods = new ArrayList<>();

    public Organization(String name, String homepage) {
        this.name = Objects.requireNonNull(name);
        this.homepage = Objects.toString(homepage, "");
    }

    public String getName() {
        return name;
    }

    public String getHomepage() {
        return homepage;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        if (!name.equals(that.name)) return false;
        if (!homepage.equals(that.homepage)) return false;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + homepage.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.join(", ", name, homepage, periods.toString());
    }
}
