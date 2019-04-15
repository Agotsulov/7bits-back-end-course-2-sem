package it.sevenbits.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class User {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("username")
    private final String username;

    @JsonProperty("authorities")
    private final List<String> authorities;

    @JsonIgnore
    private final String password;

    public User(final String id,
                final String username,
                final String password,
                final List<String> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;

    }

    @JsonCreator
    public User(final String id,
                final String username,
                final List<String> authorities) {
        this.id = id;
        this.username = username;
        this.password = null;
        this.authorities = authorities;
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

    public List<String> getAuthorities() {
        return authorities;
    }
}

