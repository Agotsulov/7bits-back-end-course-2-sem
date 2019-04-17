package it.sevenbits.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class SignInRequest {

    private final String username;
    private final String password;

    /**
     * @param username username
     * @param password password
     */
    @JsonCreator
    public SignInRequest(@JsonProperty("username") final String username,
                         @JsonProperty("password") final String password) {
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
