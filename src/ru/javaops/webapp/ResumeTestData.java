package ru.javaops.webapp;

import ru.javaops.webapp.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
            String text = ((TextSection) resume.getSection(sectionType)).getContent();
            System.out.println(title);
            System.out.println(text);
        }

        for (SectionType sectionType : new SectionType[]{SectionType.ACHIEVEMENT, SectionType.QUALIFICATION}) {
            String title = sectionType.getTitle();
            ListTextSection listTextSection = (ListTextSection) resume.getSection(sectionType);
            System.out.println(title);
            for (String content : listTextSection.getContent()) {
                System.out.println("* " + content);
            }
        }

        for (SectionType sectionType : new SectionType[]{SectionType.EXPERIENCE}) {
            String title = sectionType.getTitle();
            OrganizationSection organizationSection = (OrganizationSection) resume.getSection(sectionType);
            System.out.println(title);
            for (Organization organization : organizationSection.getOrganizations()) {
                System.out.println(organization.getName());
                for (Period period : organization.getPeriods()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
                    String startDateStr = period.getStartDate().format(formatter);
                    LocalDate endDate = period.getEndDate();
                    String endDateStr;
                    if (endDate == null) {
                        endDateStr = "Сейчас";
                    } else {
                        endDateStr = period.getEndDate().format(formatter);
                    }
                    System.out.println(startDateStr + " - " + endDateStr + " " + period.getTitle());
                    System.out.println(period.getDescription());
                }
            }
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

        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.setSection(SectionType.OBJECTIVE, objective);

        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.setSection(SectionType.PERSONAL, personal);

        ListTextSection achievement = new ListTextSection();
        achievement.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " + "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей " + "спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot " + "+ Vaadin проект для комплексных DIY смет");
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " + "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " + "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " + "Более 3500 выпускников.");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами " + "Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " + "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " + "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " + "интеграция CIFS/SMB java сервера.");
        achievement.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, " + "Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievement.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " + "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о " + "состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и " + "мониторинга системы по JMX (Jython/ Django).");
        achievement.add("Реализация протоколов по приему платежей всех основных платежных системы России " + "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.setSection(SectionType.ACHIEVEMENT, achievement);

        ListTextSection qualification = new ListTextSection();
        qualification.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualification.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualification.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, " + "SQLite, MS SQL, HSQLDB");
        qualification.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualification.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualification.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring " + "(MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), " + "Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualification.add("Python: Django.");
        qualification.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualification.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualification.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, " + "DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, " + "OAuth1, OAuth2, JWT.");
        qualification.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualification.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, " + "iReport, OpenCmis, Bonita, pgBouncer");
        qualification.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " + "архитектурных шаблонов, UML, функционального программирования");
        qualification.add("Родной русский, английский upper intermediate");
        resume.setSection(SectionType.QUALIFICATION, qualification);


        OrganizationSection experience = new OrganizationSection();
        Organization organization = new Organization("Alcatel", "http://www.alcatel.ru/");
        organization.addPeriod(new Period("Инженер по аппаратному и программному тестированию",
                LocalDate.of(1997, 9, 1), LocalDate.of(2005, 1, 1),
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
        experience.add(organization);

        organization = new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html");
        organization.addPeriod(new Period("Разработчик ПО", LocalDate.of(2005, 1, 1), LocalDate.of(2007, 2, 1),
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной " +
                        "IN платформе Siemens @vantage (Java, Unix)."));
        experience.add(organization);

        organization = new Organization("Java Online Projects", "http://javaops.ru/");
        organization.addPeriod(new Period("Автор проекта.", LocalDate.of(2013, 10, 1),
                "Создание, организация и проведение Java онлайн проектов и стажировок."));
        experience.add(organization);
        resume.setSection(SectionType.EXPERIENCE, experience);

        printResume(resume);
    }
}
