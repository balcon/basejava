package ru.javaops.webapp.web;

import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.Storage;
import ru.javaops.webapp.storage.util.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        if (uuid == null) {
            List<Resume> resumes = storage.getAllSorted();
            req.setAttribute("resumes", resumes);
            req.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(req, resp);
        } else {
            String action = req.getParameter("action");
            Resume resume = storage.get(uuid);
            req.setAttribute("resume", resume);
            if (action == null) {
                req.getRequestDispatcher("WEB-INF/jsp/view.jsp").forward(req, resp);
            }
            switch (action) {
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
        Resume resume = new Resume(uuid, fullName);
        for (ContactType contactType : ContactType.values()) {
            String value = req.getParameter(contactType.name());
            if (value != null && value.trim().length() != 0) {
                resume.setContact(contactType, value);
            }
        }
        storage.update(resume);
        resp.sendRedirect("resumes");
    }
}



