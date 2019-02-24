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
        this.userRepository = Repository.getInstance("Users");
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        //Делаем максимально просто
        String auth = req.getHeader("Authorization");

        try {
            if (auth == null) {
                resp.setStatus(401);
                return;
            }

            if (userRepository.get(UUID.fromString(auth)) == null) {
                resp.setStatus(403);
                return;
            }

            String s = req.getQueryString().split("=")[1];
            if (s == null) {
                resp.setStatus(404);
            } else {
                resp.setStatus(200);
                resp.getWriter().write(taskRepository.get(UUID.fromString(s)));
            }
        } catch (Exception e) {
            resp.setStatus(404);
        }

    }

    @Override
    protected void doDelete(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        //Делаем максимально просто

        String auth = req.getHeader("Authorization");

        try {
            if (auth == null) {
                resp.setStatus(401);
                return;
            }
            if (userRepository.get(UUID.fromString(auth)) == null) {
                resp.setStatus(403);
                return;
            }

            String s = req.getQueryString().split("=")[1];
            if (s == null) {
                resp.setStatus(404);
            } else {
                resp.setStatus(200);
                UUID id = UUID.fromString(s);
                resp.getWriter().write("Delete :" + taskRepository.get(id));
                taskRepository.remove(id);
            }
        } catch (Exception e) {
            resp.setStatus(404);
        }
    }
}
