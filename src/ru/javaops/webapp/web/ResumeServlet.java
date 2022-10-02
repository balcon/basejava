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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        if (uuid == null) {
            List<Resume> resumes = storage.getAllSorted();
            request.setAttribute("resumes", resumes);
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
        } else {
            Resume resume = storage.get(uuid);
            request.setAttribute("resume", resume);
            request.getRequestDispatcher("WEB-INF/jsp/view.jsp").forward(request, response);
        }
    }
}



