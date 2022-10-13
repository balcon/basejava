package ru.javaops.webapp.web;

import ru.javaops.webapp.model.*;
import ru.javaops.webapp.storage.Storage;
import ru.javaops.webapp.storage.util.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() {
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String uuid = req.getParameter("uuid");
        String action = req.getParameter("action");
        if (uuid == null) {
            if (action == null) {
                List<Resume> resumes = storage.getAllSorted();
                req.setAttribute("resumes", resumes);
                req.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(req, resp);
            } else if (action.equals("create")) {
                req.getRequestDispatcher("WEB-INF/jsp/edit.jsp").forward(req, resp);
            }
        } else {
            Resume resume = storage.get(uuid);
            req.setAttribute("resume", resume);
            if (action == null) {
                req.getRequestDispatcher("WEB-INF/jsp/view.jsp").forward(req, resp);
            } else switch (action) {
                case "edit" -> req.getRequestDispatcher("WEB-INF/jsp/edit.jsp").forward(req, resp);
                case "delete" -> {
                    storage.delete(uuid);
                    resp.sendRedirect("resumes");
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        String uuid = req.getParameter("uuid");
        String fullName = req.getParameter("fullName");
        Resume resume;
        if (uuid == null || uuid.isEmpty()) {
            resume = new Resume(fullName);
        } else {
            resume = new Resume(uuid, fullName);
        }
        for (ContactType contactType : ContactType.values()) {
            String value = req.getParameter(contactType.name());
            if (value != null && !value.trim().isEmpty()) {
                resume.setContact(contactType, value);
            }
        }
        for (SectionType sectionType : SectionType.values()) {
            switch (sectionType) {
                case OBJECTIVE, PERSONAL -> {
                    String value = req.getParameter(sectionType.name());
                    if (value != null && !value.trim().isEmpty()) {
                        String text = value.replace("\n", " ").replace("\r", "");
                        resume.setSection(sectionType, new TextSection(text));
                    }

                }
                case ACHIEVEMENT, QUALIFICATION -> {
                    String value = req.getParameter(sectionType.name());
                    if (value != null && !value.trim().isEmpty()) {
                        ListTextSection section = new ListTextSection();
                        Arrays.stream(value.replace("\r", "").split("\n"))
                                .filter(s -> !s.trim().isEmpty())
                                .forEach(section::add);
                        resume.setSection(sectionType, section);
                    }
                }
                case EXPERIENCE -> {
                    OrganizationSection section = new OrganizationSection();
                    String sName = sectionType.name();
                    int orgCount = Integer.parseInt(req.getParameter(sName + "_count"));
                    for (int i = 0; i < orgCount; i++) {
                        addOrganization(req, section, sName+"_"+i);
                    }
                    resume.setSection(sectionType, section);
                }
            }
        }
        if (uuid == null || uuid.isEmpty()) {
            storage.save(resume);
        } else {
            //TODO implemet edit organizations
            Resume resumeFromBase = storage.get(uuid);
//            AbstractSection experience = resumeFromBase.getSection(SectionType.EXPERIENCE);
            AbstractSection education = resumeFromBase.getSection(SectionType.EDUCATION);
//            if (experience != null) {
//                resume.setSection(SectionType.EXPERIENCE, experience);
//            }
            if (education != null) {
                resume.setSection(SectionType.EDUCATION, education);
            }

            storage.update(resume);
        }

        resp.sendRedirect("resumes?uuid=" + resume.getUuid());
    }

    private static void addOrganization(HttpServletRequest req, OrganizationSection section, String orgIndex) {
        String name = req.getParameter(orgIndex + "_name");
        String homepage = req.getParameter(orgIndex + "_homepage");
        if (!name.trim().isEmpty()) {
            Organization organization = new Organization(name, homepage);
            int periodsCount = Integer.parseInt((req.getParameter(orgIndex + "_periodsCount")));
            for (int j = 0; j < periodsCount; j++) {
                addPeriod(req, organization, orgIndex+"_"+j);
            }
            addPeriod(req, organization,orgIndex+"_new");
            section.add(organization);
        }
    }

    private static void addPeriod(HttpServletRequest req, Organization organization, String periodIndex) {
        String title = req.getParameter(periodIndex + "_title");
        String description = req.getParameter(periodIndex + "_description");
        String startDateStr = req.getParameter(periodIndex + "_startDate");
        String endDateStr = req.getParameter(periodIndex + "_endDate");
        if (!title.trim().isEmpty() && !startDateStr.trim().isEmpty()) {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = endDateStr.isEmpty() ? Period.NOW : LocalDate.parse(endDateStr);
            organization.addPeriod(new Period(title, startDate, endDate, description));
        }
    }
}



