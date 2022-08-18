package ru.javaops.webapp;

import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.model.SectionType;
import ru.javaops.webapp.model.TextSection;

public class ResumeTestData {
    static void printResume(Resume resume) {
        System.out.println(resume.getFullName());

        for (ContactType contactType : ContactType.values()) {
            String title = contactType.getTitle();
            String contact = resume.getContact(contactType);
            if (contact != null) {
                System.out.println(title + ": " + contact);
            }
        }

        for (SectionType sectionType : new SectionType[]{SectionType.OBJECTIVE, SectionType.PERSONAL}) {
            String title = sectionType.getTitle();
            String text = ((TextSection) resume.getSection(sectionType)).getText();
            System.out.println(title);
            System.out.println(text);
        }
    }

    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        resume.setContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.setContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.setContact(ContactType.MAIL, "gkislin@yandex.ru");
        resume.setContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.setContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.setContact(ContactType.HOMEPAGE, "http://gkislin.ru/");

        TextSection objective =
                new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.setSection(SectionType.OBJECTIVE, objective);
        TextSection personal =
                new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.setSection(SectionType.PERSONAL, personal);

        printResume(resume);
    }
}
