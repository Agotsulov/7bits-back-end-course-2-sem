package it.sevenbits.core.repository.users;

import it.sevenbits.core.model.User;

import java.util.List;

public interface UsersRepository {

    void create(User user);

    User findByUserName(String username);

    List<User> findAll();

}
