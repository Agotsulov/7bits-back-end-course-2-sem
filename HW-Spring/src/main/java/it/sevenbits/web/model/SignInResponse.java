package it.sevenbits.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class SignInResponse {

    private final String token;

    /**
     * @param token token
     */
    @JsonCreator
    public SignInResponse(@JsonProperty("token") final  String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
