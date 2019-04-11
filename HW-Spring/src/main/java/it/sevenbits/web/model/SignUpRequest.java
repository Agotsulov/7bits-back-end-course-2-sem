package it.sevenbits.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SignUpRequest {

    private final String login;
    private final String password;

    @JsonCreator
    public SignUpRequest(@JsonProperty("login") String login, @JsonProperty("password")String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
