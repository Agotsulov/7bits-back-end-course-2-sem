package it.sevenbits.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class SignUpRequest {

    private final String username;
    private final String password;
    private final String email;

    /**
     * @param username username
     * @param password password
     * @param email email
     */
    @JsonCreator
    public SignUpRequest(@JsonProperty("username") final String username,
                         @JsonProperty("password") final String password,
                         @JsonProperty("email") final String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
