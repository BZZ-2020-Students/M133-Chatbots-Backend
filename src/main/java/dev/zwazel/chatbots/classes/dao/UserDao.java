package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.Rating;
import dev.zwazel.chatbots.classes.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Locale;
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
     * Finds all users with the given role.
     *
     * @param role The role to search for.
     * @return A list of users with the given role.
     * @author Zwazel
     * @since 0.3
     */
    public List<User> filterByRole(UserRole role) {
        EntityManagerFactory entityManagerFactory = getEntityManagerFactory();
        List<User> t = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            t = entityManager.createQuery("SELECT u FROM User u where u.userRole = :userRole", User.class)
                    .setParameter("userRole", role)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception ignored) {

        }
        return t;
    }

    /**
     * Finds all users with the given role.
     *
     * @param role The role to search for.
     * @return A list of users with the given role.
     * @author Zwazel
     * @since 0.3
     */
    public List<User> filterByRole(String role) {
        UserRole userRole = UserRole.valueOf(role.toUpperCase(Locale.ROOT));

        return filterByRole(userRole);
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
