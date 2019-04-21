package it.sevenbits.web.contorller;

import it.sevenbits.core.service.signup.SignUpService;
import it.sevenbits.web.model.SignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for sign up user
 */
@RequestMapping("/signup")
public class SignUpController {

    private final SignUpService signUpService;

    /**
     * @param signUpService SignUpService
     */
    public SignUpController(final SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    /**
     * POST for sign up user
     * @param signUpRequest SignUpRequest
     * @return noContent if success else status CONFLICT
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity create(@RequestBody final SignUpRequest signUpRequest) {
        if (signUpService.signUp(signUpRequest) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.noContent().build();
    }

}
