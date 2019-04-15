package it.sevenbits.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PatchTaskRequest {

    private final String status;
    private final String text;

    @JsonCreator
    public PatchTaskRequest(@JsonProperty("status") final String status,
                            @JsonProperty("text") final String text) {
        this.text = text;
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public String getStatus() {
        return status;
    }
}
