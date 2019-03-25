package it.sevenbits.core.repository;

import it.sevenbits.core.model.Task;
import org.springframework.jdbc.core.JdbcOperations;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

public class DatabaseTasksRepository extends TasksRepository{

    private JdbcOperations jdbcOperations;

    public DatabaseTasksRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public List<Task> getAll() {
        return jdbcOperations.query(
                "SELECT id, name, status, createAt FROM task",
                (resultSet, i) -> {
                    String id = resultSet.getString(1);
                    String name = resultSet.getString(2);
                    String status = resultSet.getString(3);
                    String createAt = resultSet.getString(4);
                    return new Task(id, name, status, createAt);
                });

    }

    public Task put(final UUID id, final Task newTask) {
        String name = newTask.getText();
        String status = newTask.getStatus();
        String createAt = newTask.getCreateAt();
        int rows = jdbcOperations.update(
                "INSERT INTO task (id, name, status, createAt) VALUES (?, ?, ?, ?)",
                id, name, status, createAt
        );
        return new Task(id.toString(), name, status, createAt);
    }

    @Override
    public Task get(final UUID uuid) {
        return jdbcOperations.queryForObject(
                "SELECT id, name, status FROM task WHERE id = ?",
                (resultSet, i) -> {
                    String id = resultSet.getString(1);
                    String name = resultSet.getString(2);
                    String status = resultSet.getString(3);
                    String createAt = resultSet.getString(4);
                    return new Task(id, name, status, createAt);
                }, uuid.toString());
    }

    @Override
    public Task remove(final UUID uuid) {
        return jdbcOperations.queryForObject(
                "DELETE FROM task WHERE id = ?",
                (resultSet, i) -> {
                    String id = resultSet.getString(1);
                    String name = resultSet.getString(2);
                    String status = resultSet.getString(3);
                    String createAt = resultSet.getString(4);
                    return new Task(id, name, status, createAt);
                }, uuid.toString());
    }
}
