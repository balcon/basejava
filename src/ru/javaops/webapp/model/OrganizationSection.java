package ru.javaops.webapp.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Organization> organizations;

    public OrganizationSection() {
        organizations = new ArrayList<>();
    }

    public OrganizationSection(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public void add(Organization organization) {
        organizations.add(organization);
    }

    public List<Organization> getContent() {
        return organizations;
    }
    public List<Organization> getSortedContent() {
        organizations.sort(((o1, o2) -> o2.getPeriods().get(0).compareTo(o1.getPeriods().get(0))));
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

    @Override
    public String toString() {
        return organizations.toString();
    }
}
