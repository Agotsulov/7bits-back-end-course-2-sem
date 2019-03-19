package it.sevenbits.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PatchTaskRequest {

    private final String status;

    @JsonCreator
    public PatchTaskRequest(@JsonProperty("status") final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
