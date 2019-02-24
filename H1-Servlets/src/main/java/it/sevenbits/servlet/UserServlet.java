package it.sevenbits.servlet;

import it.sevenbits.servlet.repository.Repository;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class UserServlet extends HttpServlet {

    private Repository userRepository;

    public UserServlet() {
        this.userRepository = Repository.getInstance("Users");
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");

        String id = null;

        for (Cookie c : req.getCookies()) {
            if ("id".equals(c.getName())) {
                id = c.getValue();
            }
        }

        if (id != null) {
            resp.setStatus(200);
            resp.getWriter().write("Current User is " + userRepository.getTask(UUID.fromString(id)));
        } else {
            resp.setStatus(404);
        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("key");
        if (name == null) {
            resp.setStatus(404);
        } else {
            resp.setStatus(200);
            UUID id = userRepository.addTask(name);
            resp.getWriter().write("Успех");
            resp.addCookie(new Cookie("id",  id.toString()));
        }
    }
}
