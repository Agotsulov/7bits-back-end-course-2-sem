package it.sevenbits.core.repository.users;

import it.sevenbits.core.model.User;
import it.sevenbits.web.model.PatchUserRequest;

import java.util.List;

/**
 * Users repository
 */
public interface UsersRepository {

    /**
     * Add new user
     * @param user user
     */
    void add(User user);

    /**
     * @param username username
     * @return first user with this username
     */
    User findByUserName(String username);

    /**
     * @return All users
     */
    List<User> findAll();

    /**
     * @param id user id
     * @return user with this id
     */
    User findById(String id);

    /**
     * @param id user id
     * @param patchUserRequest PatchUserRequest
     */
    void update(String id, PatchUserRequest patchUserRequest);

    /**
     * @param id id for activate user
     * @return User to be added
     */
    User activateUserById(String id);

    /**
     * @param user user
     */
    void addToSignUp(User user);

}

