package ru.javaops.webapp.web;

import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.Storage;
import ru.javaops.webapp.storage.util.Config;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Resume> resumes = storage.getAllSorted();
        StringBuilder str = new StringBuilder();
        str.append("<table border=1><tr><th>Uuid</th><th>Full name</th></tr>");
        for (Resume resume : resumes) {
            str.append("<tr><td>").append(resume.getUuid()).append("</td>");
            str.append("<td>").append(resume.getFullName()).append("</td></tr>");
        }
        str.append("</table>");

        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write(str.toString());
    }

}

