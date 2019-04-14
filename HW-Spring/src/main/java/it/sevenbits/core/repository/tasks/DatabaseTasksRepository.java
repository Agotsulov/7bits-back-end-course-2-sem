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

    public List<Task> getAll(final String status, final String order,
                             final int page, final int size) {
        String s = "SELECT id, name, status, createAt, updateAt " +
                "FROM task WHERE status = ? ORDER BY createAt DESC LIMIT ? OFFSET ?";
        if ("asc".equals(order)) {
            s = "SELECT id, name, status, createAt, updateAt " +
                    "FROM task WHERE status = ? ORDER BY createAt ASC LIMIT ? OFFSET ?";
        } // Я не знаю как это сделать нормально, кроме тупо соединением строк. И я спрашивал на workshop.
        return jdbcOperations.query(
                s,
                taskRowMapper,
                status,
                size,
                (page - 1) * size);
    }

    @Override
    public Task create(final AddTaskRequest newTask) {
        Task task = TaskFactory.createTaskByText(newTask.getText());
        jdbcOperations.update(
                "INSERT INTO task (id, name, status, createAt, updateAt) VALUES (?, ?, ?, ?, ?)",
                task.getId(),
                task.getText(),
                task.getStatus(),
                task.getCreateAt(),
                task.getUpdateAt()
        );
        return task;
    }

    @Override
    public Task get(final UUID uuid) {
        return jdbcOperations.queryForObject(
                "SELECT id, name, status, createAt, updateAt FROM task WHERE id = ?",
                taskRowMapper, uuid.toString());
    }

    @Override
    public Task remove(final UUID uuid) {
        Task task = get(uuid);
        jdbcOperations.update("DELETE FROM task WHERE id = ?", uuid.toString());
        return task;
    }

    @Override
    public Task update(final UUID uuid, final PatchTaskRequest newTask) {
        String updateAt = Helper.getCurrentTime();
        jdbcOperations.update("UPDATE task SET status = ?, updateAt = ? WHERE id = ?",
                newTask.getStatus(),
                updateAt,
                uuid.toString());
        Task task = get(uuid);
        return task;
    }

    @Override
    public int size() {
        return jdbcOperations.queryForObject("SELECT COUNT(id) FROM task", Integer.class);
    }
}
