package it.sevenbits.core.model;

import it.sevenbits.core.other.Time;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

public class TaskTest {

    @Test
    public void testTaskCreateAndGetters() {
        String id = UUID.randomUUID().toString();
        String text = "TEST";
        String status = "inbox";
        String createAt = Time.getCurrentTime();
        Task task = new Task(id, text, status, createAt, createAt);

        assertEquals(id, task.getId().toString());
        assertEquals(text, task.getText());
        assertEquals(status, task.getStatus());
        assertEquals(createAt, task.getCreateAt());
        assertEquals(createAt, task.getUpdateAt());
    }
}
