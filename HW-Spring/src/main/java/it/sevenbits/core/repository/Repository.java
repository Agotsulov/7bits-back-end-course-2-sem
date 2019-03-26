package it.sevenbits.core.repository;

import it.sevenbits.core.model.Task;
import it.sevenbits.web.model.AddTaskRequest;
import it.sevenbits.web.model.PatchTaskRequest;

import java.util.List;
import java.util.UUID;

public interface Repository {

    Task create(AddTaskRequest newTask);

    Task get(UUID id);

    Task remove(UUID id);

    Task update(UUID id, PatchTaskRequest newTask);

    List<Task> getAll(String status);
}
