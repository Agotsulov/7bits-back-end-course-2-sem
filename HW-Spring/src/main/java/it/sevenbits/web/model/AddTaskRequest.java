package it.sevenbits.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request for addUser new task.
 */
public class AddTaskRequest {

    private final String text;

    /**
     * @param text text
     */
    @JsonCreator
    public AddTaskRequest(@JsonProperty("text") final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
