package ru.javaops.webapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private String homepage;
    private final List<Period> periods = new ArrayList<>();

    public Organization(String name, String homepage) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(homepage);
        this.name = name;
        this.homepage = homepage;
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

    public void setHomepage(String homepage) {
        this.homepage = homepage;
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
}
