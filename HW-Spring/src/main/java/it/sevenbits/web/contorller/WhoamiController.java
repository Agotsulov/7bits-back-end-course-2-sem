package it.sevenbits.web.contorller;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * Controller to display the current user.
 */
@Controller
@RequestMapping("/whoami")
public class WhoamiController {

    private final UsersRepository usersRepository;

    public WhoamiController(final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<User> get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = usersRepository.findByUserName(username);

        return ResponseEntity.ok(user);
    }

}
