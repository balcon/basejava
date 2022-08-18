package ru.javaops.webapp;

import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.model.SectionType;

public class ResumeTestData {
    static void printResume(Resume resume) {
        // FullName
        System.out.println(resume.getFullName());
        // Contacts
        for (ContactType contactType : ContactType.values()) {
            String title = contactType.getTitle();
            String contact = resume.getContact(contactType);
            if (contact != null) {
                System.out.println(title + ": " + contact);
            }
        }
        // Sections
        for (SectionType sectionType : SectionType.values()) {
            String title = sectionType.getTitle();
            System.out.println(title);
        }
    }

    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        resume.setContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.setContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.setContact(ContactType.TELEGRAM, "@gkislin");
        resume.setContact(ContactType.MAIL, "gkislin@yandex.ru");

        printResume(resume);
    }
}
