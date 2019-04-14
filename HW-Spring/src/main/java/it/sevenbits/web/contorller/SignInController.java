package it.sevenbits.web.contorller;

import it.sevenbits.core.model.User;
import it.sevenbits.core.service.signin.SignInService;
import it.sevenbits.web.model.SignInRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/signin")
public class SignInController {

    private final SignInService signInService;

    public SignInController(final SignInService signInService) {
        this.signInService = signInService;
    }

    @PostMapping
    @ResponseBody
    public User create(@RequestBody SignInRequest login) {
        return signInService.login(login);
    }

}
