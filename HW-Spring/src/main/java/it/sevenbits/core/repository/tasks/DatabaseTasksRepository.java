package it.sevenbits.core.repository.tasks;

import it.sevenbits.core.model.Task;
import it.sevenbits.core.other.Helper;
import it.sevenbits.core.other.TaskFactory;
import it.sevenbits.core.other.TaskRowMapper;
import it.sevenbits.web.model.AddTaskRequest;
import it.sevenbits.web.model.PatchTaskRequest;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;
import java.util.UUID;

public class DatabaseTasksRepository implements TasksRepository {

    private JdbcOperations jdbcOperations;
    private final TaskRowMapper taskRowMapper = new TaskRowMapper();

    public DatabaseTasksRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Task> getAll(final String status, final String order,
                             final int page, final int size, final String owner) {
        String s = "SELECT id, text, status, createAt, updateAt " +
                "FROM task WHERE status = ? AND owner = ? ORDER BY createAt DESC LIMIT ? OFFSET ?";
        if ("asc".equals(order)) {
            s = "SELECT id, text, status, createAt, updateAt " +
                    "FROM task WHERE status = ? AND owner = ? ORDER BY createAt ASC LIMIT ? OFFSET ?";
        }
        return jdbcOperations.query(
                s,
                taskRowMapper,
                status,
                owner,
                size,
                (page - 1) * size
        );
    }

    @Override
    public Task create(final String text, final String owner) {
        Task task = TaskFactory.createTaskByText(text);
        jdbcOperations.update(
                "INSERT INTO task (id, text, status, createAt, updateAt, owner)" +
                        " VALUES (?, ?, ?, ?, ?, ?)",
                task.getId(),
                task.getText(),
                task.getStatus(),
                task.getCreateAt(),
                task.getUpdateAt(),
                owner
        );
        return task;
    }

    @Override
    public Task get(final String id, final String owner) {
        List<Task> result = jdbcOperations.query(
                "SELECT id, text, status, createAt, updateAt " +
                        "FROM task WHERE id = ? AND owner = ?",
                taskRowMapper, id, owner);
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public Task remove(final String id, final String owner) {
        Task task = get(id, owner);
        jdbcOperations.update("DELETE FROM task WHERE id = ? AND owner = ?",
                id,
                owner
        );
        return task;
    }

    @Override
    public void update(final Task task, final String owner) {
        String updateAt = Helper.getCurrentTime();
        jdbcOperations.update("UPDATE task SET text = ?, status = ?, updateAt = ? " +
                        "WHERE id = ? AND owner = ?",
                task.getText(),
                task.getStatus(),
                updateAt,
                task.getId().toString(),
                owner);
    }

    @Override
    public int size(final String owner) {
        return jdbcOperations.queryForObject("SELECT COUNT(id) FROM task WHERE owner = ?",
                Integer.class, owner);
    }
}
