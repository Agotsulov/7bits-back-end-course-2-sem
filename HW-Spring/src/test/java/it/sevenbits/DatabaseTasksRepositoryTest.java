package it.sevenbits;

import it.sevenbits.core.model.Task;
import it.sevenbits.core.other.Helper;
import it.sevenbits.core.repository.DatabaseTasksRepository;
import it.sevenbits.core.repository.Repository;
import it.sevenbits.web.contorller.TasksController;
import it.sevenbits.web.model.AddTaskRequest;
import it.sevenbits.web.model.PatchTaskRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class DatabaseTasksRepositoryTest {
    private JdbcOperations mockJdbcOperations;
    private Repository repository;

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
                eq("inbox"))).thenReturn(mockTasks);

        List<Task> actual = repository.getAll("inbox");
        verify(mockJdbcOperations, times(1)).query(
                eq("SELECT id, name, status, createAt, updateAt FROM task WHERE status = ?"),
                any(RowMapper.class),
                eq("inbox")
        );
        assertSame(mockTasks, actual);
    }

    @Test
    public void testGetTask() {
        Task mockTask = mock(Task.class);

        UUID id = UUID.randomUUID();

        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyString())).thenReturn(mockTask);

        Task actual = repository.get(id);
        verify(mockJdbcOperations, times(1)).queryForObject(
                eq("SELECT id, name, status, createAt, updateAt FROM task WHERE id = ?"),
                any(RowMapper.class),
                eq(id.toString())
        );
        assertSame(mockTask, actual);
    }

    @Test
    public void testRemoveTask() {
        UUID id = UUID.randomUUID();

        repository.remove(id);
        verify(mockJdbcOperations, times(1)).update(
                eq("DELETE FROM task WHERE id = ?"),
                eq(id.toString())
        );
    }

    @Test
    public void testPatchTask() {
        UUID id = UUID.randomUUID();
        PatchTaskRequest patchTaskRequest = new PatchTaskRequest("done");

        repository.update(id, patchTaskRequest);
        verify(mockJdbcOperations, times(1)).update(
                eq("UPDATE task SET status = ?, updateAt = ? WHERE id = ?"),
                eq(patchTaskRequest.getStatus()),
                anyString(),
                eq(id.toString())
        );
    }

    @Test
    public void testCreateTask() {

        AddTaskRequest addTaskRequest = new AddTaskRequest("TEST");

        Task task = repository.create(addTaskRequest);
        verify(mockJdbcOperations, times(1)).update(
                eq("INSERT INTO task (id, name, status, createAt, updateAt) VALUES (?, ?, ?, ?, ?)"),
                eq(task.getId()),
                eq(task.getText()),
                eq(task.getStatus()),
                eq(task.getCreateAt()),
                eq(task.getUpdateAt())
        );
    }



}
