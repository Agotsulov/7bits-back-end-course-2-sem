package it.sevenbits.servlet;

import it.sevenbits.servlet.repository.Repository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

        try {
            String s = getQueryMap(req.getQueryString()).get("taskid");
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

        try {
            String s = getQueryMap(req.getQueryString()).get("taskid");
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


    private Map<String, String> getQueryMap(final String query)
    {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<>();
        for (String param : params) {
            String s[] = param.split("=");
            map.put(s[0], s[1]);
        }
        return map;
    }
}
