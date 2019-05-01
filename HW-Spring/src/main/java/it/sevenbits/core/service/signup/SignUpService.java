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
     * Add new not enable user and sign up request in repository
     * @param signUpRequest SignUpRequest class
     * @return UUID sign up request for enable this user or null if this user exists
     */
    public String signUp(final SignUpRequest signUpRequest) {
        List<String> authorities = new ArrayList<>();
        authorities.add("USER");

        User check = usersRepository.findByUserName(signUpRequest.getUsername());
        if (check != null) {
            return null;
        }
        User user = new User(UUID.randomUUID().toString(),
                signUpRequest.getUsername(),
                passwordEncoder.encode(signUpRequest.getPassword()), authorities);

        String requestId = UUID.randomUUID().toString();


        return requestId;
    }

    public boolean acitvate(final String id) {
        User checkId = usersRepository.activateUserById(id);
        if (checkId == null) {
            return false;
        }

        User user = new User(UUID.randomUUID().toString(),
                checkId.getUsername(),
                passwordEncoder.encode(checkId.getPassword()));

        usersRepository.addUser(user);

        return true;
    }

}
