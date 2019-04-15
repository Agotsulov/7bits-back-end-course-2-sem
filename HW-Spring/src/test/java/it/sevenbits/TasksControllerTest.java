package it.sevenbits;

import it.sevenbits.core.model.Task;
import it.sevenbits.core.repository.tasks.DatabaseTasksRepository;
import it.sevenbits.core.repository.tasks.TasksRepository;
import it.sevenbits.web.contorller.TasksController;
import it.sevenbits.web.model.AddTaskRequest;
import it.sevenbits.web.model.ListTaskWithMetaResponse;
import it.sevenbits.web.model.PatchTaskRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class TasksControllerTest {
    private TasksRepository repository;
    private TasksController tasksController;

    @Before
    public void setup() {
        repository = mock(DatabaseTasksRepository.class);
        tasksController = new TasksController(repository);
    }

    @Test
    public void testGetAllTasks() {
        List<Task> mockTasks = mock(List.class);
        when(repository.getAll("inbox", "asc", 1, 50)).thenReturn(mockTasks);

        ResponseEntity<ListTaskWithMetaResponse> answer = tasksController.all("inbox", "asc", 1, 50);
        verify(repository, times(1)).getAll("inbox", "asc", 1, 50);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
    }

    @Test
    public void testCreateTask() {
        Task task = mock(Task.class);
        AddTaskRequest taskRequest = new AddTaskRequest("TEST");
        when(repository.create(taskRequest)).thenReturn(task);

        ResponseEntity<Task> answer = tasksController.create(taskRequest);
        verify(repository, times(1)).create(taskRequest);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertSame(task, answer.getBody());
    }

    @Test
    public void testDeleteTask() {
        Task task = mock(Task.class);
        String id = UUID.randomUUID().toString();
        when(repository.remove(id)).thenReturn(task);

        ResponseEntity<Void> answer = tasksController.deleteTask(id.toString());
        verify(repository, times(1)).remove(id);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
    }


    @Test
    public void testGetTask() {
        Task task = mock(Task.class);
        String id = UUID.randomUUID().toString();
        when(repository.get(id)).thenReturn(task);

        ResponseEntity<Task> answer = tasksController.getTask(id.toString());
        verify(repository, times(1)).get(id);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertSame(task, answer.getBody());
    }

    @Test
    public void testPatchTask() {
        Task task = mock(Task.class);
        String id = UUID.randomUUID().toString();
        PatchTaskRequest patchTaskRequest = new PatchTaskRequest("done", "");
        when(repository.get(id)).thenReturn(task);

        ResponseEntity<Void> answer = tasksController.patchTask(id.toString(), patchTaskRequest);
        verify(repository, times(1)).update(task);
        assertEquals(HttpStatus.NO_CONTENT, answer.getStatusCode());
    }

}
