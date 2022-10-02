package ru.javaops.webapp.web;

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
            } else {
                req.getRequestDispatcher("WEB-INF/jsp/edit.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        String fullName = req.getParameter("fullName");
        Resume resume = new Resume(uuid, fullName);
        storage.update(resume);
    }
}



