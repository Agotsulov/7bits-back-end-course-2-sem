package it.sevenbits.web.contorller;

import it.sevenbits.core.model.User;
import it.sevenbits.core.service.signin.SignInService;
import it.sevenbits.web.model.SignInRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller to sign in
 */
@RequestMapping("/signin")
public class SignInController {

    private final SignInService signInService;

    /**
     * @param signInService SignInService
     */
    public SignInController(final SignInService signInService) {
        this.signInService = signInService;
    }

    /**
     * POST
     * @param signInRequest SignInRequest
     * @return body with user
     */
    @PostMapping
    @ResponseBody
    public User create(@RequestBody final SignInRequest signInRequest) {
        return signInService.signIn(signInRequest);
    }

}
