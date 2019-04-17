package it.sevenbits.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PatchUserRequest {

    private boolean enabled;
    private List<String> authorities;

    /**
     * @param enabled new enabled
     * @param authorities authorities
     */
    @JsonCreator
    public PatchUserRequest(@JsonProperty("enabled") final boolean enabled,
                            @JsonProperty("authorities") final List<String> authorities) {
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}
