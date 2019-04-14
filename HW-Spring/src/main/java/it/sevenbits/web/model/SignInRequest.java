package it.sevenbits.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SignInRequest {

    private final String username;
    private final String password;

    @JsonCreator
    public SignInRequest(@JsonProperty("username") String username, @JsonProperty("password")String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
