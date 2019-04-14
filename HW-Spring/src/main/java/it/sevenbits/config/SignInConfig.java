package it.sevenbits.config;

import it.sevenbits.core.service.signin.SignInService;
import it.sevenbits.web.contorller.CookieSignInController;
import it.sevenbits.web.security.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignInConfig {

    @Bean
    public Object cookieSignInController(final SignInService loginService, final JwtTokenService jwtTokenService) {
        return new CookieSignInController(loginService, jwtTokenService);
    }
}

