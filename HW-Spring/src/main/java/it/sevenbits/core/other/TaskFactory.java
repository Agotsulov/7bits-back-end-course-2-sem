package it.sevenbits.core.other;

import it.sevenbits.core.model.Task;

import java.util.UUID;

public final class TaskFactory {

    private TaskFactory() {}

    public static Task createTaskByText(final String text) {
        String id = UUID.randomUUID().toString();
        String createAt = Helper.getCurrentTime();
        return new Task(id, text, "inbox", createAt, createAt);
    }

}
