package it.sevenbits.core.repository.users;

import it.sevenbits.core.model.User;
import it.sevenbits.web.model.PatchUserRequest;

import java.util.List;

public interface UsersRepository {

    void create(User user);

    User findByUserName(String username);

    List<User> findAll();

    User findById(String id);

    void update(String id, PatchUserRequest patchUserRequest);
}
