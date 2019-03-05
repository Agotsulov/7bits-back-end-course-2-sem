package it.sevenbits;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {

    private final String text;

    @JsonCreator
    public Task(@JsonProperty("text") final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
