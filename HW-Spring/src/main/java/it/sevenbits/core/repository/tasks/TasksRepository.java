package it.sevenbits.core.repository.tasks;

import it.sevenbits.core.model.Task;
import it.sevenbits.web.model.AddTaskRequest;

import java.util.List;

public interface TasksRepository {

    Task create(AddTaskRequest newTask);

    Task get(String id);

    Task remove(String id);

    void update(Task task);

    List<Task> getAll(String status, String order, int page, int size);

    int size();
}
