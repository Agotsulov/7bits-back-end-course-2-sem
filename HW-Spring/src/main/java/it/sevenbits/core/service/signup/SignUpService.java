package it.sevenbits.core.service.signup;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.UsersRepository;
import it.sevenbits.web.model.SignUpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service for sign up users
 */
@Service
public class SignUpService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * @param usersRepository UsersRepository
     * @param passwordEncoder PasswordEncoder
     */
    public SignUpService(final UsersRepository usersRepository, final PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @param signUpRequest SignUpRequest
     * @return user
     */
    public User signUp(final SignUpRequest signUpRequest) {
        
    }

    public boolean acitvate(final String id) {
        List<String> authorities = new ArrayList<>();
        authorities.add("USER");

        User checkId = usersRepository.activateUserById(id);
        if (checkId == null) {
            return false;
        }

        User user = new User(UUID.randomUUID().toString(),
                checkId.getUsername(),
                passwordEncoder.encode(checkId.getPassword()), authorities);

        usersRepository.add(user);

        return true;
    }

}
