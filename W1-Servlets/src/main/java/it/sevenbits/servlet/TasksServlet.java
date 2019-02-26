
package it.sevenbits.servlet;

import it.sevenbits.servlet.repository.Repository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class TasksServlet extends HttpServlet {

    private Repository taskRepository;
    private Repository userRepository;

    public TasksServlet() {
        this.taskRepository = Repository.getInstance("Tasks");
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().write(taskRepository.toJson());
        resp.setStatus(200);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");


        String s = req.getParameter("key");
        if (s == null) {
            resp.setStatus(404);
        } else {
            UUID id = taskRepository.add(s);
            resp.getWriter().write(taskRepository.get(id));
            resp.setStatus(200);
        }
    }
}
