package it.sevenbits.servlet.repository;

import com.google.gson.Gson;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Repository {

    private static ConcurrentHashMap<String, Repository> instances = new ConcurrentHashMap<>();

    private ConcurrentHashMap<UUID, String> map;

    private Repository() {
        map = new ConcurrentHashMap<>();
    }

    public static Repository getInstance(final String name) {
        Repository instance = instances.get(name);
        if (instance == null) {
            instance = new Repository();
            instances.put(name, instance);
        }
        return instance;
    }

    public UUID add(final String task) {
        UUID id = UUID.randomUUID();
        map.put(id, task);
        return id;
    }

    public String get(final UUID id) {
        return map.get(id);
    }

    public void remove(final UUID id) {
        map.remove(id);
    }

    public String toJson() {
        return new Gson().toJson(map);
    }
}
