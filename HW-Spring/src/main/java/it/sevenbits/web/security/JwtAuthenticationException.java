package it.sevenbits.web.security;

import org.springframework.security.core.AuthenticationException;

/**
 * Generic exception related to Jwt.
 */
public class JwtAuthenticationException extends AuthenticationException {

    /**
     * @param message message
     */
    public JwtAuthenticationException(final String message) {
        super(message);
    }

    /**
     * @param message message
     * @param cause cause
     */
    public JwtAuthenticationException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
