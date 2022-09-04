package ru.javaops.webapp.storage.serializers;

import ru.javaops.webapp.model.*;
import ru.javaops.webapp.storage.function.ConsumerWithException;
import ru.javaops.webapp.storage.function.SupplierWithException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
            writeWithException(resume.getSections().entrySet(), output, (section) -> {
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
            });
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream input = new DataInputStream(inputStream)) {
            String uuid = input.readUTF();
            String fullName = input.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readWithException(input, ()-> {
                resume.setContact(ContactType.valueOf(input.readUTF()), input.readUTF());
                return Collections.emptyList();
            });
            readWithException(input, () -> {
                SectionType sectionType = SectionType.valueOf(input.readUTF());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> resume.setSection(sectionType, new TextSection(input.readUTF()));
                    case ACHIEVEMENT, QUALIFICATION -> resume.setSection(sectionType,
                            new ListTextSection(readWithException(input, input::readUTF)));
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizations = readWithException(input, () ->
                                new Organization(input.readUTF(), input.readUTF(), readWithException(input, () ->
                                        new Period(input.readUTF(),
                                                LocalDate.parse(input.readUTF()),
                                                LocalDate.parse(input.readUTF()),
                                                input.readUTF()))));
                        resume.setSection(sectionType, new OrganizationSection(organizations));
                    }
                }
                return Collections.emptyList();
            });
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

    private static <T> List<T> readWithException(DataInputStream input,
                                                 SupplierWithException<T> action) throws IOException {
        List<T> objects = new ArrayList<>();
        int size = input.readInt();
        for (int i = 0; i < size; i++) {
            objects.add(action.get());
        }
        return objects;
    }
}
