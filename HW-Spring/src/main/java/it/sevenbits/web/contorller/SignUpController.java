package it.sevenbits.web.contorller;

import it.sevenbits.core.service.signup.SignUpService;
import it.sevenbits.web.model.SignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for sign up user
 */
@Controller
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

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    @ResponseBody
    public ResponseEntity activate(@PathVariable("id") final String id) {
        if (signUpService.acitvate(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
