package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.User;

import java.util.UUID;

/**
 * Helper class for CRUD operations on User objects.
 *
 * @author Zwazel
 * @see Dao
 * @since 0.3
 */
public class UserDao extends Dao<User, String> {
    /**
     * No arg constructor.
     *
     * @since 0.3
     */
    public UserDao() {
        super(User.class);
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return The user with the given username.
     * @author Zwazel
     * @since 0.3
     */
    public User findByUsername(String username) {
        return findBy("username", username);
    }

    /**
     * Finds a user by their ID.
     *
     * @param id The ID to search for.
     * @return The user with the given ID.
     * @author Zwazel
     * @since 0.3
     */
    public User findById(String id) {
        return findBy("id", id);
    }

    /**
     * Finds a user by their ID.
     *
     * @param id The ID to search for.
     * @return The user with the given ID.
     * @author Zwazel
     * @since 0.3
     */
    public User findById(UUID id) {
        return findBy("id", id.toString());
    }
}
