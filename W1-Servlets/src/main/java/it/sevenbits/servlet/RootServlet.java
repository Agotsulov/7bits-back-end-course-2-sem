
package it.sevenbits.servlet;

import it.sevenbits.servlet.repository.TaskRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.UUID;

public class RootServlet extends HttpServlet {

    private TaskRepository taskRepository;

    public RootServlet() {
        this.taskRepository = TaskRepository.getInstance();
    }

    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("GET");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(taskRepository.getTasks());
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("POST");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String s = req.getParameter("key");
        if (s == null) {
            resp.getWriter().write("KEY NOT FOUND");
        } else {
            UUID id = taskRepository.addTask(s);
            resp.getWriter().write(taskRepository.getTask(id));
        }
        System.out.println(req.getParameter("key"));

    }
}
