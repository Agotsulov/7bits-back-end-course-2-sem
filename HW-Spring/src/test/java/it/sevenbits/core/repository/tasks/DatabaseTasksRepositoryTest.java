package it.sevenbits.core.repository.tasks;

import it.sevenbits.core.model.Task;
import it.sevenbits.core.repository.tasks.DatabaseTasksRepository;
import it.sevenbits.core.repository.tasks.TasksRepository;
import it.sevenbits.web.model.AddTaskRequest;
import it.sevenbits.web.model.PatchTaskRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class DatabaseTasksRepositoryTest {
    private JdbcOperations mockJdbcOperations;
    private TasksRepository repository;

    @Before
    public void setup() {
        mockJdbcOperations = mock(JdbcOperations.class);
        repository = new DatabaseTasksRepository(mockJdbcOperations);
    }

    @Test
    public void testGetAllTasks() {
        List<Task> mockTasks = mock(List.class);

        when(mockJdbcOperations.query(anyString(),
                any(RowMapper.class),
                eq("inbox"),
                eq(25),
                eq(0))).thenReturn(mockTasks);

        List<Task> actual = repository.getAll("inbox", "asc", 1, 25);
        verify(mockJdbcOperations, times(1)).query(
                eq("SELECT id, text, status, createAt, updateAt " +
                        "FROM task WHERE status = ? ORDER BY createAt ASC LIMIT ? OFFSET ?"),
                any(RowMapper.class),
                eq("inbox"),
                eq(25),
                eq(0)
        );
        assertSame(mockTasks, actual);
    }

    @Test
    public void testGetTask() {
        Task mockTask = mock(Task.class);

        String id = UUID.randomUUID().toString();

        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyString())).thenReturn(mockTask);

        repository.get(id);
        verify(mockJdbcOperations, times(1)).query(
                eq("SELECT id, text, status, createAt, updateAt FROM task WHERE id = ?"),
                any(RowMapper.class),
                eq(id)
        );
    }

    @Test
    public void testRemoveTask() {
        String id = UUID.randomUUID().toString();

        repository.remove(id);
        verify(mockJdbcOperations, times(1)).update(
                eq("DELETE FROM task WHERE id = ?"),
                eq(id)
        );
    }

    @Test
    public void testPatchTask() {
        String id = UUID.randomUUID().toString();
        Task task = new Task(id, "test", "done", "0", "0");

        repository.update(task);
        verify(mockJdbcOperations, times(1)).update(
                eq("UPDATE task SET text = ?, status = ?, updateAt = ? WHERE id = ?"),
                eq(task.getText()),
                eq(task.getStatus()),
                anyString(),
                eq(id)
        );
    }

    @Test
    public void testCreateTask() {

        AddTaskRequest addTaskRequest = new AddTaskRequest("TEST");

        Task task = repository.create(addTaskRequest);
        verify(mockJdbcOperations, times(1)).update(
                eq("INSERT INTO task (id, text, status, createAt, updateAt) VALUES (?, ?, ?, ?, ?)"),
                eq(task.getId()),
                eq(task.getText()),
                eq(task.getStatus()),
                eq(task.getCreateAt()),
                eq(task.getUpdateAt())
        );
    }



}
