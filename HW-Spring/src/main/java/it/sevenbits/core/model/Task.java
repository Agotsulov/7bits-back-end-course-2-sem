package it.sevenbits.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Task {

    @JsonProperty("id")
    private final UUID id;
    @JsonProperty("text")
    private String text;
    @JsonProperty("status")
    private String status;
    @JsonProperty("createAt")
    private final String createAt;
    @JsonProperty("updateAt")
    private final String updateAt;


    public Task(final String id,
                final String text,
                final String status,
                final String createAt,
                final String updateAt
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
        if (text != null && !"".equals(text)) {
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
