package it.sevenbits.core.service.signin;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.UsersRepository;
import it.sevenbits.web.model.SignInRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class SignInService {

    private final UsersRepository users;
    private final PasswordEncoder passwordEncoder;

    public SignInService(final UsersRepository users, final PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(final SignInRequest login) {
        User user = users.findByUserName(login.getUsername());

        if (user == null) {
            throw new LoginFailedException("User '" + login.getUsername() + "' not found");
        }

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new LoginFailedException("Wrong password");
        }
        return new User(user.getId(), user.getUsername(), user.getAuthorities());
    }

}
