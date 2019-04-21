package it.sevenbits.config;

import it.sevenbits.core.service.signin.SignInService;
import it.sevenbits.web.contorller.CookieSignInController;
import it.sevenbits.web.security.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for sign in
 */
@Configuration
public class SignInConfig {

    /**
     * Bean
     * @param signInService SignInService
     * @param jwtTokenService JwtTokenService
     * @return CookieSignInController
     */
    @Bean
    public Object cookieSignInController(final SignInService signInService, final JwtTokenService jwtTokenService) {
        return new CookieSignInController(signInService, jwtTokenService);
    }
}

