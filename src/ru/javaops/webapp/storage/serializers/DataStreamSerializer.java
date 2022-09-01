package ru.javaops.webapp.storage.serializers;

import ru.javaops.webapp.model.*;

import java.io.*;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(OutputStream outputStream, Resume resume) throws IOException {
        try (DataOutputStream output = new DataOutputStream(outputStream)) {
            output.writeUTF(resume.getUuid());
            output.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            output.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
                output.writeUTF(contact.getKey().name());
                output.writeUTF(contact.getValue());
            }
            Map<SectionType, AbstractSection> sections = resume.getSections();
            output.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> section : sections.entrySet()) {
                switch (section.getKey()) {
                    case OBJECTIVE, PERSONAL -> {
                        output.writeUTF(section.getKey().name());
                        output.writeUTF(((TextSection) section.getValue()).getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATION -> {
                        output.writeUTF(section.getKey().name());
                        List<String> textList = ((ListTextSection) section.getValue()).getContent();
                        output.writeInt(textList.size());
                        for (String text : textList) {
                            output.writeUTF(text);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        output.writeUTF(section.getKey().name());
                        List<Organization> organizations = ((OrganizationSection) section.getValue()).getContent();
                        output.writeInt(organizations.size());
                        for (Organization organization : organizations) {
                            output.writeUTF(organization.getName());
                            output.writeUTF(organization.getHomepage());
                            List<Period> periods = organization.getPeriods();
                            output.writeInt(periods.size());
                            for (Period period : periods) {
                                output.writeUTF(period.getTitle());
                                output.writeUTF(period.getStartDate().toString());
                                output.writeUTF(period.getEndDate()!=null?period.getEndDate().toString():"NULL");
                                output.writeUTF(period.getDescription()!=null?period.getDescription():"NULL");
                            }
                        }
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
//            int sectionsSize = input.readInt();
//            for (int i = 0; i < sectionsSize; i++) {
//                SectionType sectionType = SectionType.valueOf(input.readUTF());
//                switch (sectionType) {
//                    case OBJECTIVE, PERSONAL -> resume.setSection(sectionType, new TextSection(input.readUTF()));
//
//                }
//            }
            return resume;
        }
    }
}
