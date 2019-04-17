package it.sevenbits.core.repository.users;

import it.sevenbits.core.model.User;
import it.sevenbits.web.model.PatchUserRequest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DatabaseUsersRepository implements UsersRepository {

    private final JdbcOperations jdbcOperations;

    private final String ID = "id";
    private final String AUTHORITY = "authority";
    private final String USERNAME = "username";
    private final String PASSWORD = "password";

    public DatabaseUsersRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    private List<String> findAuthorities(final String id) {
        List<String> authorities = new ArrayList<>();
        jdbcOperations.query(
                "SELECT id, authority FROM authorities" +
                        " WHERE id = ?",
                resultSet -> {
                    String authority = resultSet.getString(AUTHORITY);
                    authorities.add(authority);
                },
                id
        );
        return authorities;
    }

    public User findByUserName(final String username) {
        Map<String, Object> rawUser;

        try {
            rawUser = jdbcOperations.queryForMap(
                    "SELECT id, username, password FROM users u" +
                            " WHERE u.enabled = true AND u.username = ?",
                    username
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

        String id = String.valueOf(rawUser.get(ID));
        String password = String.valueOf(rawUser.get(PASSWORD));

        List<String> authorities = findAuthorities(id);

        return new User(id, username, password, authorities);
    }

    public User findById(final String id) {
        Map<String, Object> rawUser;

        try {
            rawUser = jdbcOperations.queryForMap(
                    "SELECT username, password FROM users u" +
                            " WHERE u.enabled = true AND u.id = ?",
                    id
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

        String username = String.valueOf(rawUser.get(USERNAME));
        String password = String.valueOf(rawUser.get(PASSWORD));

        List<String> authorities = findAuthorities(id);

        return new User(id, username, password, authorities);
    }

    public List<User> findAll() {
        HashMap<String, User> users = new HashMap<>();

        for (Map<String, Object> row : jdbcOperations.queryForList(
                "SELECT a.id AS id, a.authority AS authority, u.username AS username FROM authorities a " +
                        "INNER JOIN users u ON u.id = a.id " +
                        "WHERE u.enabled = true")) {

            String id = String.valueOf(row.get(ID));
            String username = String.valueOf(row.get(USERNAME));
            String newRole = String.valueOf(row.get(AUTHORITY));
            User user = users.computeIfAbsent(id, name -> new User(id, username, new ArrayList<>()));
            List<String> roles = user.getAuthorities();
            roles.add(newRole);
        }

        return new ArrayList<>(users.values());
    }

    @Override
    public void create(final User user) {
        jdbcOperations.update(
                "INSERT INTO users (id, username, password, enabled) VALUES (?, ?, ?, true)",
                user.getId(),
                user.getUsername(),
                user.getPassword()
        );
        jdbcOperations.update(
                "INSERT INTO authorities (id, authority) VALUES (?, ?)",
                user.getId(),
                "USER"
        );
    }

    @Override
    public void update(final String id, final PatchUserRequest patchUserRequest) {
        jdbcOperations.update(
                "UPDATE users SET enabled = ? WHERE id = ?",
                patchUserRequest.isEnabled(),
                id
        );
        if (patchUserRequest.getAuthorities() != null) {
            List<String> current = findAuthorities(id);

            List<String> delete = new ArrayList<>(current); // TODO: fix
            delete.removeAll(patchUserRequest.getAuthorities());
            List<String> create = new ArrayList<>(patchUserRequest.getAuthorities());
            create.removeAll(current);

            for (String c : create) {
                jdbcOperations.update(
                        "INSERT INTO authorities (id, authority) VALUES (?, ?)",
                        id,
                        c
                );
            }
            for (String d : delete) {
                jdbcOperations.update(
                        "DELETE FROM authorities WHERE id = ? AND authority = ?",
                        id,
                        d
                );
            }
        }
    }
}
