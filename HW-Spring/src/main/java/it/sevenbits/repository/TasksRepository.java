package it.sevenbits.repository;

import it.sevenbits.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TasksRepository{

    private ConcurrentHashMap<UUID, Task> map;

    public TasksRepository() {
        this.map = new ConcurrentHashMap<>();
    }

    public Task get(final UUID uuid) {
        return map.get(uuid);
    }

    public Task put(final UUID uuid, final Task s) {
        return map.put(uuid, s);
    }

    public Task remove(final UUID uuid) {
       return map.remove(uuid);
    }

    public List<Task> getAll() {
        return new ArrayList(map.values());
    }
}

