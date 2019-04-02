package it.sevenbits.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class Task {

    private final UUID id;
    private final String text;
    private String status;
    private final String createAt;
    private String updateAt;


    @JsonCreator
    public Task(@JsonProperty("id") final String id,
                @JsonProperty("text") final String text,
                @JsonProperty("status") final String status,
                @JsonProperty("createAt") final String createAt,
                @JsonProperty("updateAt") final String updateAt
                ) {
        this.id = UUID.fromString(id);
        this.text = text;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}