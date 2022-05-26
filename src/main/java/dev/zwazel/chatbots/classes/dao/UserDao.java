package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Locale;

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
     * saves a user and makes sure that the username is unique.
     *
     * @param user The user to save.
     * @author Zwazel
     * @since 0.3
     */
    @Override
    public void save(User user) throws IllegalArgumentException {
        User user1 = findByUsername(user.getUsername());
        if (user1 != null) {
            user.setId(user1.getId());
            throw new IllegalArgumentException("Username " + user.getUsername() + " already exists");
        }

        super.save(user);
    }

    /**
     * Delets a user by their username.
     *
     * @param username The username to delete.
     * @author Zwazel
     * @since 1.1.0
     */
    public void deleteByUsername(String username) {
        User user = findByUsername(username);
        if (user != null) {
            super.delete(user);
        } else {
            throw new IllegalArgumentException("Username " + username + " does not exist");
        }
    }

    /**
     * saves a bunch of users and makes sure that the username is unique.
     *
     * @param users The users to save.
     * @author Zwazel
     * @since 0.3
     */
    @Override
    public void saveCollection(Iterable<User> users) throws IllegalArgumentException {
        for (User user : users) {
            User user1 = findByUsername(user.getUsername());
            if (user1 != null) {
                user.setId(user1.getId());
                throw new IllegalArgumentException("Username " + user.getUsername() + " already exists");
            }
        }

        super.saveCollection(users);
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
        return findBy("username", username, false);
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
}
