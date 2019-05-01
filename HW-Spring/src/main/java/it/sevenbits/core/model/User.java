package it.sevenbits.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 */
public class User {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("username")
    private final String username;

    @JsonProperty("authorities")
    private final List<String> authorities;

    @JsonProperty("email")
    private final String email;

    @JsonIgnore
    private final String password;

    @JsonIgnore
    private final boolean enabled;

    /**
     * @param id id
     * @param username username
     * @param password password
     * @param authorities auth
     */
    public User(final String id,
                final String username,
                final String email,
                final String password,
                final List<String> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = false;
        this.email = email;
    }

    /**
     * @param id id
     * @param username username
     * @param authorities auth
     */
    @JsonCreator
    public User(final String id,
                final String username,
                final List<String> authorities) {
        this.id = id;
        this.username = username;
        this.password = null;
        this.authorities = authorities;
        this.enabled = false;
        this.email = null;
    }

    /**
     * @param id id
     * @param username username
     * @param password password
     */
    @JsonCreator
    public User(final String id,
                final String username,
                final String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = null;
        this.email = null;
    }
    
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}

