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

    @Override
    public List<Organization> getContent() {
        return organizations;
    }
}
