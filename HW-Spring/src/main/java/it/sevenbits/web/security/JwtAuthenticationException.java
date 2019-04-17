package it.sevenbits.web.security;

import org.springframework.security.core.AuthenticationException;

/**
 * Generic exception related to Jwt.
 */
public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(final String message) {
        super(message);
    }

    public JwtAuthenticationException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
