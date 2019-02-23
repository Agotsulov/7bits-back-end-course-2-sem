package it.sevenbits.servlet.repository;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TaskRepository {

    private static TaskRepository instance;
    private ConcurrentHashMap<UUID, String> tasks;

    private TaskRepository() {
        tasks = new ConcurrentHashMap<>();
    }

    public static TaskRepository getInstance() {
        if (instance == null) {
            instance = new TaskRepository();
        }
        return instance;
    }

    public UUID addTask(final String task) {
        UUID id = UUID.randomUUID();
        tasks.put(id, task);
        return id;
    }

    public String getTask(final UUID id) {
        return tasks.get(id);
    }

    public String getTasks() {
        StringBuilder result = new StringBuilder();
        result.append("[\n");
        for (UUID id: tasks.keySet()) {
            result.append("{\n" +
                    "\"id:\"" + id +
                    "\"name\"" + tasks.get(id) +
                    "}\n");
        }
        result.append("]");
        return result.toString();
    }
}
