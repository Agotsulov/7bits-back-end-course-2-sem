package it.sevenbits.core.repository;

import it.sevenbits.core.model.Task;
import it.sevenbits.core.other.TaskRowMapper;
import it.sevenbits.web.model.AddTaskRequest;
import it.sevenbits.web.model.PatchTaskRequest;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;
import java.util.UUID;

public class DatabaseTasksRepository implements Repository{

    private JdbcOperations jdbcOperations;
    private final TaskRowMapper taskRowMapper = new TaskRowMapper();

    public DatabaseTasksRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public List<Task> getAll(final String status) {
        return jdbcOperations.query(
                "SELECT id, name, status, createAt FROM task WHERE status = ?",
                taskRowMapper, status);
    }

    @Override
    public Task create(final AddTaskRequest newTask) {
        Task task = new Task(newTask.getText());

        jdbcOperations.update(
                "INSERT INTO task (id, name, status, createAt) VALUES (?, ?, ?, ?)",
                task.getId(), task.getText(), task.getStatus(), task.getCreateAt()
        );

        return task;
    }

    @Override
    public Task get(final UUID uuid) {
        return jdbcOperations.queryForObject(
                "SELECT id, name, status, createAt FROM task WHERE id = ?",
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
        jdbcOperations.update("UPDATE task SET status = ? WHERE id = ?",
                newTask.getStatus(),
                uuid.toString());
        Task task = get(uuid); //Че лучше 2 раза обращатся к базе или один большой запрос?
        return task;
    }
}
