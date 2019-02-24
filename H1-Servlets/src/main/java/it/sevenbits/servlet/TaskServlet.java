package it.sevenbits.servlet;

import it.sevenbits.servlet.repository.Repository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class TaskServlet extends HttpServlet {

    private Repository taskRepository;
    private Repository userRepository;

    public TaskServlet() {
        this.taskRepository = Repository.getInstance("Tasks");
        this.userRepository = Repository.getInstance("User");
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String auth = req.getHeader("Authorization");
        if (auth == null) {
            resp.setStatus(401);
            return;
        }


        String s = req.getQueryString().split("=")[1];
        if (s == null) {
            resp.setStatus(404);
        }
        resp.getWriter().write(taskRepository.getTask(UUID.fromString(s)));

    }

    @Override
    protected void doDelete(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String s = req.getQueryString().split("=")[1];
        if (s != null) {
            taskRepository.removeTask((UUID.fromString(s)));
            resp.getWriter().write(s);
        } else {
            resp.getWriter().write("\"Task not found\"");
        }
    }
}
