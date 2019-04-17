package it.sevenbits.core.repository.tasks;

import it.sevenbits.core.model.Task;

import java.util.List;

public interface TasksRepository {

    Task create(String text, String owner);

    Task get(String id, String owner);

    Task remove(String id, String owner);

    void update(Task task, String owner);

    List<Task> getAll(String status,
                      String order,
                      int page,
                      int size,
                      String owner);

    int size(String owner);
}
