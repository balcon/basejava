package ru.javaops.webapp;

import ru.javaops.webapp.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ResumeTestData {

    private static LocalDate dateOf(int month, int year) {
        return LocalDate.of(year, month, 1);
    }

    public static Resume buildResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.setContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.setContact(ContactType.SKYPE, "grigory.kislin");
        resume.setContact(ContactType.MAIL, "gkislin@yandex.ru");
        resume.setContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.setContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.setContact(ContactType.HOMEPAGE, "http://gkislin.ru/");

        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.setSection(SectionType.OBJECTIVE, objective);

        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.setSection(SectionType.PERSONAL, personal);

        ListTextSection achievement = new ListTextSection();
        achievement.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " + "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей " + "спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot " + "+ Vaadin проект для комплексных DIY смет");
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " + "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " + "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " + "Более 3500 выпускников.");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами " + "Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " + "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " + "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " + "интеграция CIFS/SMB java сервера.");
        resume.setSection(SectionType.ACHIEVEMENT, achievement);

        ListTextSection qualification = new ListTextSection();
        qualification.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualification.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualification.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, " + "SQLite, MS SQL, HSQLDB");
        qualification.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualification.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualification.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring " + "(MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), " + "Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualification.add("Python: Django.");
        resume.setSection(SectionType.QUALIFICATION, qualification);

        Organization organization;
        OrganizationSection experience = new OrganizationSection();
        organization = new Organization("Alcatel", "http://www.alcatel.ru/");
        organization.addPeriod(new Period("Инженер по аппаратному и программному тестированию", dateOf(9, 1997), dateOf(1, 2005)
                , "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
        experience.add(organization);
        organization = new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html");
        organization.addPeriod(new Period("Разработчик ПО", dateOf(1, 2005), dateOf(2, 2006),
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной " +
                        "IN платформе Siemens @vantage (Java, Unix)."));
        organization.addPeriod(new Period("Разработчик ПО (TEST)", dateOf(2, 2006), dateOf(2, 2007),
                "Случайное описание для другого периода"));
        experience.add(organization);
        organization = new Organization("Java Online Projects", "http://javaops.ru/");
        organization.addPeriod(new Period("Автор проекта.", dateOf(10, 2013), null,
                "Создание, организация и проведение Java онлайн проектов и стажировок."));
        experience.add(organization);
        resume.setSection(SectionType.EXPERIENCE, experience);

        OrganizationSection education = new OrganizationSection();
        organization = new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/");
        organization.addPeriod(new Period("Закончил с отличием", dateOf(9, 1984), dateOf(6, 1987), null));
        education.add(organization);
        organization = new Organization("Санкт-Петербургский национальный исследовательский университет информационных " +
                "технологий, механики и оптики", "http://www.ifmo.ru/");
        organization.addPeriod(new Period("Аспирантура (программист С, С++)", dateOf(9, 1993), dateOf(7, 1996), null));
        organization.addPeriod(new Period("Инженер (программист Fortran, C)", dateOf(9, 1987), dateOf(7, 1993), null));
        education.add(organization);
        resume.setSection(SectionType.EDUCATION, education);

        return resume;
    }

    private static void printResume(Resume resume) {
        System.out.println(resume.getFullName());

        for (ContactType contactType : ContactType.values()) {
            String title = contactType.getTitle();
            String contact = resume.getContact(contactType);
            if (contact != null) {
                System.out.println(title + ": " + contact);
            }
        }

        for (SectionType sectionType : SectionType.values()) {
            String title = sectionType.getTitle();
            switch (sectionType) {
                case OBJECTIVE, PERSONAL -> {
                    String text = ((TextSection) resume.getSection(sectionType)).getText();
                    System.out.println(title);
                    System.out.println(text);
                }
                case ACHIEVEMENT, QUALIFICATION -> {
                    List<String> lines = ((ListTextSection) resume.getSection(sectionType)).getTextList();
                    System.out.println(title);
                    for (String line : lines) {
                        System.out.println("* " + line);
                    }
                }
                case EXPERIENCE, EDUCATION -> {
                    List<Organization> organizations = ((OrganizationSection) resume.getSection(sectionType)).getContent();
                    System.out.println(title);
                    for (Organization organization : organizations) {
                        System.out.println(organization.getName());
                        System.out.println(organization.getHomepage());
                        for (Period period : organization.getPeriods()) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
                            String startDateStr = period.getStartDate().format(formatter);
                            LocalDate endDate = period.getEndDate();
                            String endDateStr;
                            if (endDate.equals(Period.NOW)) {
                                endDateStr = "Сейчас";
                            } else {
                                endDateStr = period.getEndDate().format(formatter);
                            }
                            System.out.println(startDateStr + " - " + endDateStr + " " + period.getTitle());
                            String description = period.getDescription();
                            if (!description.equals("")) {
                                System.out.println(description);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Resume resume = buildResume("uuid_1", "Григорий Кислин");
        printResume(resume);

        //System.out.println(resume);
    }
}
