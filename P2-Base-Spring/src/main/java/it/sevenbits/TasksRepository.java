package it.sevenbits;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TasksRepository implements Repository<UUID, Task>{

    private ConcurrentHashMap<UUID, Task> map;

    public TasksRepository() {
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public Task get(UUID uuid) {
        return map.get(uuid);
    }

    @Override
    public Task put(UUID uuid, Task s) {
        return map.put(uuid, s);
    }

    @Override
    public List<Task> getAll() {
        return (List<Task>) map.values();
    }
}

