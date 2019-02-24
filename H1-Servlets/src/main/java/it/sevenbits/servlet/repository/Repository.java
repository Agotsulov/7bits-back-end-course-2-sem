package it.sevenbits.servlet.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Repository {

    private static ConcurrentHashMap<String, Repository> instances = new ConcurrentHashMap<>();

    private ConcurrentHashMap<UUID, String> tasks;

    private Repository() {
        tasks = new ConcurrentHashMap<>();
    }

    public static Repository getInstance(final String name) {
        Repository instance = instances.get(name);
        if (instance == null) {
            instance = new Repository();
            instances.put(name, instance);
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

    public void removeTask(final UUID id) {
        tasks.remove(id);
    }

    public String getTasks() {
        return new Gson().toJson(tasks);
    }
}
