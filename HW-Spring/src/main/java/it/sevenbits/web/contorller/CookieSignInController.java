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

import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@RequestMapping("/signin")
public class CookieSignInController {

    private final SignInService signInService;
    private final JwtTokenService tokenService;

    //private static final int THOUSAND = 1000;

    /**
     * @param signInService SignInService
     * @param tokenService JwtTokenService
     */
    public CookieSignInController(final SignInService signInService, final JwtTokenService tokenService) {
        this.signInService = signInService;
        this.tokenService = tokenService;
    }

    /**
     * POST
     * @param signInRequest SignInRequest
     * @param response HttpServletResponse
     * @return 200 - Successfull auth
     * 403 - User does not have access to the resource
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<SignInResponse> create(@RequestBody final SignInRequest signInRequest, final HttpServletResponse response) {
        User user = signInService.signIn(signInRequest);
        String token = tokenService.createToken(user);

        SignInResponse signInResponse = new SignInResponse(token);

        /*
        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) (tokenService.getTokenExpiredIn().toMillis() / THOUSAND));
        response.addCookie(cookie);
        */

        return ResponseEntity.ok().body(signInResponse);
    }

}
