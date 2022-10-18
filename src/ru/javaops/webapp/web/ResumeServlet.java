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
                case EXPERIENCE, EDUCATION -> {
                    OrganizationSection section = new OrganizationSection();
                    String sName = sectionType.name();
                    String organizationCount = req.getParameter(sName + "_count");
                    if (!organizationCount.isEmpty()) {
                        int count = Integer.parseInt(organizationCount);
                        for (int i = 0; i < count; i++) {
                            addOrganization(req, section, sName + "_" + i);
                        }
                    }
                    addOrganization(req, section, sName + "_new");
                    if (!section.getContent().isEmpty()) {
                        resume.setSection(sectionType, section);
                    }
                }
            }
        }
        if (uuid == null || uuid.isEmpty()) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }

        resp.sendRedirect("resumes?uuid=" + resume.getUuid());
    }

    private static void addOrganization(HttpServletRequest req, OrganizationSection section, String orgPrefix) {
        String name = req.getParameter(orgPrefix + "_name");
        String homepage = req.getParameter(orgPrefix + "_homepage");
        if (!name.trim().isEmpty()) {
            Organization organization = new Organization(name, homepage);
            String periodsCount = req.getParameter(orgPrefix + "_periodsCount");
            if (periodsCount != null) {
                int count = Integer.parseInt(periodsCount);
                for (int j = 0; j < count; j++) {
                    addPeriod(req, organization, orgPrefix + "_" + j);
                }
            }
            addPeriod(req, organization, orgPrefix + "_new");
            if (!organization.getPeriods().isEmpty()) {
                section.add(organization);
            }
        }
    }

    private static void addPeriod(HttpServletRequest req, Organization organization, String periodPrefix) {
        String title = req.getParameter(periodPrefix + "_title");
        String description = req.getParameter(periodPrefix + "_description");
        description = description != null ? description.replace("\n", " ").replace("\r", "") : null;
        String startDateStr = req.getParameter(periodPrefix + "_startDate");
        String endDateStr = req.getParameter(periodPrefix + "_endDate");
        if (!title.trim().isEmpty() && !startDateStr.trim().isEmpty()) {
            LocalDate startDate = LocalDate.parse(startDateStr);
            // TODO If date close to now() - write NOW
            LocalDate endDate = endDateStr.isEmpty() ? Period.NOW : LocalDate.parse(endDateStr);
            organization.addPeriod(new Period(title, startDate, endDate, description));
        }
    }
}



