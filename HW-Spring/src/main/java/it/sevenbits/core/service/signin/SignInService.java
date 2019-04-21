package it.sevenbits.core.service.signin;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.UsersRepository;
import it.sevenbits.web.model.SignInRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for sign in users in repository
 */
@Service
public class SignInService {

    private final UsersRepository users;
    private final PasswordEncoder passwordEncoder;

    /**
     * @param usersRepository UsersRepository
     * @param passwordEncoder PasswordEncoder
     */
    public SignInService(final UsersRepository usersRepository, final PasswordEncoder passwordEncoder) {
        this.users = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @param signInRequest SignInRequest
     * @return user
     */
    public User signIn(final SignInRequest signInRequest) {
        User user = users.findByUserName(signInRequest.getUsername());

        if (user == null) {
            throw new SignInFailedException("User '" + signInRequest.getUsername() + "' not found");
        }

        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new SignInFailedException("Wrong password");
        }
        return new User(user.getId(), user.getUsername(), user.getAuthorities());
    }

}
