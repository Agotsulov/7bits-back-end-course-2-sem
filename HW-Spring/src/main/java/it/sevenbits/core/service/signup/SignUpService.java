package it.sevenbits.core.service.signup;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.UsersRepository;
import it.sevenbits.web.model.SignUpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SignUpService {

    private final UsersRepository users;
    private final PasswordEncoder passwordEncoder;

    public SignUpService(final UsersRepository users, final PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUp(final SignUpRequest login) {
        List<String> authorities = new ArrayList<>();
        authorities.add("USER");

        User checkUsername = users.findByUserName(login.getUsername());
        if (checkUsername != null) {
            return null;
        }

        User user = new User(UUID.randomUUID().toString(),
                login.getUsername(),
                passwordEncoder.encode(login.getPassword()), authorities);

        users.create(user);

        return user;
    }

}
