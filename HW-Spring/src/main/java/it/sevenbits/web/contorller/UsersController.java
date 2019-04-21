package it.sevenbits.web.contorller;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.UsersRepository;
import it.sevenbits.web.model.PatchUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

/**
 * Controller /users
 */
@RequestMapping("/users")
public class UsersController {

    private final UsersRepository usersRepository;

    /**
     * @param usersRepository UsersRepository
     */
    public UsersController(final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * GET all users
     * @return all users
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(usersRepository.findAll());
    }


    /**
     * GET user by id
     * @param id user id
     * @return user
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<User> getUserInfo(final @PathVariable("id") String id) {
        return Optional
                .ofNullable(usersRepository.findById(id))
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * PATCH user by id
     * @param id user id
     * @param patchUserRequest PatchUserRequest
     * @return 204 - Successful operation
     * 400 - Validation exception
     * 403 - User does not have access to the resource
     * 404 - User not found
     */
    @PatchMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity patchUserInfo(final @PathVariable("id") String id,
                                              final @RequestBody PatchUserRequest patchUserRequest) {
        User user = usersRepository.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        usersRepository.update(id, patchUserRequest);
        return ResponseEntity.noContent().build();
    }
}