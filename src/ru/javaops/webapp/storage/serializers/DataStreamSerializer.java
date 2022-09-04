package ru.javaops.webapp.storage.serializers;

import ru.javaops.webapp.model.*;
import ru.javaops.webapp.storage.function.ConsumerWithException;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(OutputStream outputStream, Resume resume) throws IOException {
        try (DataOutputStream output = new DataOutputStream(outputStream)) {
            output.writeUTF(resume.getUuid());
            output.writeUTF(resume.getFullName());
            writeWithException(resume.getContacts().entrySet(), output, (entry) -> {
                output.writeUTF(entry.getKey().name());
                output.writeUTF(entry.getValue());
            });
            Map<SectionType, AbstractSection> sections = resume.getSections();
            output.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> section : sections.entrySet()) {
                output.writeUTF(section.getKey().name());
                switch (section.getKey()) {
                    case OBJECTIVE, PERSONAL -> output.writeUTF(((TextSection) section.getValue()).getText());
                    case ACHIEVEMENT, QUALIFICATION -> {
                        List<String> textList = ((ListTextSection) section.getValue()).getTextList();
                        writeWithException(textList, output, output::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizations = ((OrganizationSection) section.getValue()).getContent();
                        writeWithException(organizations, output, (organization) -> {
                            output.writeUTF(organization.getName());
                            output.writeUTF(organization.getHomepage());
                            writeWithException(organization.getPeriods(), output, (period) -> {
                                output.writeUTF(period.getTitle());
                                output.writeUTF(period.getStartDate().toString());
                                output.writeUTF(period.getEndDate().toString());
                                output.writeUTF(period.getDescription());
                            });
                        });
                    }

                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream input = new DataInputStream(inputStream)) {
            String uuid = input.readUTF();
            String fullName = input.readUTF();
            int contactsSize = input.readInt();
            Resume resume = new Resume(uuid, fullName);
            for (int i = 0; i < contactsSize; i++) {
                resume.setContact(ContactType.valueOf(input.readUTF()), input.readUTF());
            }
            int sectionsSize = input.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionType = SectionType.valueOf(input.readUTF());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> resume.setSection(sectionType, new TextSection(input.readUTF()));
                    case ACHIEVEMENT, QUALIFICATION -> {
                        ListTextSection listTextSection = new ListTextSection();
                        int listSize = input.readInt();
                        for (int j = 0; j < listSize; j++) {
                            listTextSection.add(input.readUTF());
                        }
                        resume.setSection(sectionType, listTextSection);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        OrganizationSection organizationSection = new OrganizationSection();
                        int organizationsSize = input.readInt();
                        for (int j = 0; j < organizationsSize; j++) {
                            Organization organization = new Organization(input.readUTF(), input.readUTF());
                            int periodsSize = input.readInt();
                            for (int k = 0; k < periodsSize; k++) {
                                String title = input.readUTF();
                                LocalDate startDate = LocalDate.parse(input.readUTF());
                                LocalDate endDate = LocalDate.parse(input.readUTF());
                                String description = input.readUTF();
                                organization.addPeriod(new Period(title, startDate, endDate, description));
                            }
                            organizationSection.add(organization);
                        }
                        resume.setSection(sectionType, organizationSection);
                    }
                }
            }
            return resume;
        }
    }

    private static <T> void writeWithException(Collection<T> collection,
                                               DataOutputStream output,
                                               ConsumerWithException<T> action) throws IOException {
        output.writeInt(collection.size());
        for (T object : collection) {
            action.accept(object);
        }
    }
}
