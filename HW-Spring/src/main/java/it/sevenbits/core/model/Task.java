package it.sevenbits.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class Task {

    private final UUID id;
    private String text;
    private String status;
    private final String createAt;
    private final String updateAt;


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

    public void setText(final String text) {
        if ("".equals(text)) {
            this.text = text;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        if (("done".equals(status) || "inbox".equals(status))) {
            this.status = status;
        }
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

}
