package it.sevenbits.core.service.signin;

import org.springframework.security.core.AuthenticationException;

public class LoginFailedException extends AuthenticationException {

    public LoginFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(final String message) {
        super(message);
    }

}
