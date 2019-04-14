package it.sevenbits.web.contorller;

import it.sevenbits.core.model.User;
import it.sevenbits.core.service.signup.SignUpService;
import it.sevenbits.web.model.SignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    private final SignUpService signUpService;

    public SignUpController(final SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity create(@RequestBody SignUpRequest signUp) {
        if (signUpService.signUp(signUp) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.noContent().build();
    }

}
