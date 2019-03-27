package it.sevenbits;

import it.sevenbits.core.model.Task;
import it.sevenbits.core.repository.DatabaseTasksRepository;
import it.sevenbits.core.repository.Repository;
import it.sevenbits.web.contorller.TasksController;
import it.sevenbits.web.model.AddTaskRequest;
import it.sevenbits.web.model.PatchTaskRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.codec.AbstractDataBufferDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class TasksControllerTest {
    private Repository repository;
    private TasksController tasksController;

    @Before
    public void setup() {
        repository = mock(DatabaseTasksRepository.class);
        tasksController = new TasksController(repository);
    }

    @Test
    public void testGetAllTasks() {
        List<Task> mockTasks = mock(List.class);
        when(repository.getAll("inbox")).thenReturn(mockTasks);

        ResponseEntity<List<Task>> answer = tasksController.all("inbox");
        verify(repository, times(1)).getAll("inbox");
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertSame(mockTasks, answer.getBody());
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
        UUID id = UUID.randomUUID();
        when(repository.remove(id)).thenReturn(task);

        ResponseEntity<Void> answer = tasksController.deleteTask(id.toString());
        verify(repository, times(1)).remove(id);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
    }


    @Test
    public void testGetTask() {
        Task task = mock(Task.class);
        UUID id = UUID.randomUUID();
        when(repository.get(id)).thenReturn(task);

        ResponseEntity<Task> answer = tasksController.getTask(id.toString());
        verify(repository, times(1)).get(id);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertSame(task, answer.getBody());
    }

    @Test
    public void testPatchTask() {
        Task task = mock(Task.class);
        UUID id = UUID.randomUUID();
        PatchTaskRequest patchTaskRequest = new PatchTaskRequest("done");
        when(repository.update(id, patchTaskRequest)).thenReturn(task);

        ResponseEntity<Void> answer = tasksController.patchTask(id.toString(), patchTaskRequest);
        verify(repository, times(1)).update(id, patchTaskRequest);
        assertEquals(HttpStatus.NO_CONTENT, answer.getStatusCode());
    }

}
