
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

            resp.getWriter().write(taskRepository.toJson());
            resp.setStatus(200);
        } catch (Exception e) {
            resp.setStatus(404);
        }

    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
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

            String s = req.getParameter("key");
            if (s == null) {
                resp.setStatus(404);
            } else {
                UUID id = taskRepository.add(s);
                resp.getWriter().write(taskRepository.get(id));
                resp.setStatus(200);
            }

        } catch (Exception e) {
            resp.setStatus(404);
        }


    }
}
