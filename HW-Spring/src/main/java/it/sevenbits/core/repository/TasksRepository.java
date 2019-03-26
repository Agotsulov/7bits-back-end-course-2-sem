package it.sevenbits.core.repository;

import it.sevenbits.core.model.Task;
import it.sevenbits.web.model.AddTaskRequest;
import it.sevenbits.web.model.PatchTaskRequest;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TasksRepository implements Repository{

    private ConcurrentHashMap<UUID, Task> map;

    public TasksRepository() {
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public Task create(final AddTaskRequest newTask) {
        Task task = new Task(newTask.getText());
        map.put(task.getId(), task);
        return task;
    }


    public Task get(final UUID uuid) {
        return map.get(uuid);
    }

    public Task remove(final UUID uuid) {
       return map.remove(uuid);
    }

    public List<Task> getAll(final String status) {
        List<Task> result = new ArrayList<>();
        for (Map.Entry<UUID, Task> stringTaskEntry : map.entrySet()) {
            Task task = stringTaskEntry.getValue();
            if (task.getStatus().equals(status)) {
                result.add(task);
            }
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public Task update(final UUID id, final PatchTaskRequest newTask) {
        Task task = get(id);
        task.setStatus(newTask.getStatus());
        return task;
    }
}

