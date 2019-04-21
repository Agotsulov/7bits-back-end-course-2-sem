package it.sevenbits.core.other;

import it.sevenbits.core.model.Task;

import java.util.UUID;

/**
 * Factory for task
 */
public final class TaskFactory {

    private TaskFactory() {}

    /**
     * Create task with random UUID and current time by text
     * @param text task text
     * @return created task
     */
    public static Task createTaskByText(final String text) {
        String id = UUID.randomUUID().toString();
        String createAt = Time.getCurrentTime();
        return new Task(id, text, "inbox", createAt, createAt);
    }

}
