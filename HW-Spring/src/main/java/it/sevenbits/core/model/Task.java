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


    @JsonCreator
    public Task(@JsonProperty("id") final String id,
                @JsonProperty("text") final String text,
                @JsonProperty("status") final String status,
                @JsonProperty("createAt") final String createAt
                ) {
        this.id = UUID.fromString(id);
        this.text = text;
        this.status = status;
        this.createAt = createAt;
    }


    // Это норм? Или лучше отдельной статической функцией сделать
    public Task(final String text) {
        UUID id = UUID.randomUUID();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

        date.setTimeZone(TimeZone.getTimeZone("UTC"));

        String createAt = date.format(new Date());

        this.id = id;
        this.text = text;
        this.status = "inbox";
        this.createAt = createAt;
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

}
