package it.sevenbits.web.controller;

import it.sevenbits.core.model.Task;
import it.sevenbits.core.repository.tasks.DatabaseTasksRepository;
import it.sevenbits.core.repository.tasks.TasksRepository;
import it.sevenbits.core.repository.users.DatabaseUsersRepository;
import it.sevenbits.core.repository.users.UsersRepository;
import it.sevenbits.web.contorller.TasksController;
import it.sevenbits.web.contorller.UsersController;
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

    private TasksRepository tasksRepository;
    private UsersRepository usersRepository;

    private TasksController tasksController;

    @Before
    public void setup() {
        tasksRepository = mock(DatabaseTasksRepository.class);
        usersRepository = mock(DatabaseUsersRepository.class);
        tasksController = new TasksController(tasksRepository, usersRepository);
    }

    @Test
    public void testGetAllTasks() {
        List<Task> mockTasks = mock(List.class);
        when(tasksRepository.getAll("inbox", "asc", 1, 1, "")).thenReturn(mockTasks);

        ResponseEntity<ListTaskWithMetaResponse> answer = tasksController.all("inbox", "asc", 1, 50);
        verify(tasksRepository, times(1))
                .getAll("inbox",
                        "asc",
                        1, 1,
                        "");
        assertEquals(HttpStatus.OK, answer.getStatusCode());
    }

    @Test
    public void testCreateTask() {
        Task task = mock(Task.class);
        AddTaskRequest taskRequest = new AddTaskRequest("TEST");

        when(tasksRepository.create("TEST", "")).thenReturn(task);
        when(task.getId()).thenReturn(UUID.randomUUID());


        ResponseEntity<Task> answer = tasksController.create(taskRequest);
        verify(tasksRepository, times(1))
                .create("TEST", "");
        assertEquals(HttpStatus.NO_CONTENT, answer.getStatusCode());
    }

    @Test
    public void testDeleteTask() {
        Task task = mock(Task.class);
        String id = UUID.randomUUID().toString();
        when(tasksRepository.remove(id, "")).thenReturn(task);

        ResponseEntity<Void> answer = tasksController.deleteTask(id);
        verify(tasksRepository, times(1))
                .remove(id, "");
        assertEquals(HttpStatus.OK, answer.getStatusCode());
    }


    @Test
    public void testGetTask() {
        Task task = mock(Task.class);
        String id = UUID.randomUUID().toString();

        when(tasksRepository.get(id, "")).thenReturn(task);

        ResponseEntity<Task> answer = tasksController.getTask(id);
        verify(tasksRepository, times(1))
                .get(id, "");
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertSame(task, answer.getBody());
    }

    @Test
    public void testPatchTask() {
        Task task = mock(Task.class);
        String id = UUID.randomUUID().toString();
        PatchTaskRequest patchTaskRequest = new PatchTaskRequest("done", "");
        when(tasksRepository.get(id, "")).thenReturn(task);

        ResponseEntity<Void> answer = tasksController.patchTask(id, patchTaskRequest);
        verify(tasksRepository, times(1)).update(task, "");
        assertEquals(HttpStatus.NO_CONTENT, answer.getStatusCode());
    }

}
