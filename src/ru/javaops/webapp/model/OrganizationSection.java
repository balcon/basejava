package ru.javaops.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private final List<Organization> organizations = new ArrayList<>();

    public void add(Organization organization) {
        Objects.requireNonNull(organization);
        organizations.add(organization);
    }

    public List<Organization> getContent() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }
}
