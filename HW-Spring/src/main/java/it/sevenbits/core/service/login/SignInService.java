package it.sevenbits.core.service.login;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.UsersRepository;
import it.sevenbits.web.model.SignInRequest;
import it.sevenbits.web.model.SignUpRequest;
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
        User user = users.findByUserName(login.getLogin());

        if (user == null) {
            throw new LoginFailedException("User '" + login.getLogin() + "' not found");
        }

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new LoginFailedException("Wrong password");
        }
        return new User(user.getUsername(), user.getAuthorities());
    }

}
