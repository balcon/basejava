package ru.javaops.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final String name;
    private String website;
    private final List<Period> periods = new ArrayList<>();

    public Organization(String name, String website) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(website);
        this.name = name;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!name.equals(that.name)) return false;
        if (!website.equals(that.website)) return false;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + website.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }
}
