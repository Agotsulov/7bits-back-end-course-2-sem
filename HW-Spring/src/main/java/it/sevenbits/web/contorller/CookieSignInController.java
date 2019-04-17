package it.sevenbits.web.contorller;

import it.sevenbits.core.model.User;
import it.sevenbits.core.service.signin.SignInService;
import it.sevenbits.web.model.SignInRequest;
import it.sevenbits.web.model.SignInResponse;
import it.sevenbits.web.security.JwtTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/signin")
public class CookieSignInController {

    private final SignInService loginService;
    private final JwtTokenService tokenService;

    private final int thousand = 1000;

    public CookieSignInController(final SignInService loginService, final JwtTokenService tokenService) {
        this.loginService = loginService;
        this.tokenService = tokenService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<SignInResponse> create(@RequestBody final SignInRequest login, final HttpServletResponse response) {
        User user = loginService.login(login);
        String token = tokenService.createToken(user);

        SignInResponse signInResponse = new SignInResponse(token);

        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) (tokenService.getTokenExpiredIn().toMillis() / thousand));
        response.addCookie(cookie);

        return ResponseEntity.ok().body(signInResponse);
    }

}
