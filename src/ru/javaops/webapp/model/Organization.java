package ru.javaops.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private final String name;
    private final String website;
    private final List<Period> periods = new ArrayList<>();

    public Organization(String name, String website) {
        this.name = name;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    // TODO do update period
    // TODO for getting Periods implement Iterator
}