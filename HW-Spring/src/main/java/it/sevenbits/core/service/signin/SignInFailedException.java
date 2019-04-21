package it.sevenbits.core.service.signin;

import org.springframework.security.core.AuthenticationException;

/**
 *
 */
public class SignInFailedException extends AuthenticationException {

    /**
     * @param message message
     */
    public SignInFailedException(final String message) {
        super(message);
    }

}
