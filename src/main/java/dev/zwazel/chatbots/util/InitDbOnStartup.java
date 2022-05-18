package dev.zwazel.chatbots.util;

import dev.zwazel.chatbots.classes.dao.UserDao;
import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

/**
 * This class is used to initialize the database with Data on startup.
 *
 * @author Zwazel
 * @since 0.3
 */
@Singleton
@Startup
public class InitDbOnStartup {
    /**
     * This method is used to initialize the database with Data on startup.
     *
     * @author Zwazel
     * @since 0.3
     */
    @PostConstruct
    public void init() {
        System.out.println("Initiation of DB");

        User user = User.builder()
                .username("user")
                .password("user")
                .userRole(UserRole.USER)
                .build();

        User admin = User.builder()
                .username("admin")
                .password("admin")
                .userRole(UserRole.ADMIN)
                .build();

        UserDao userDao = new UserDao();
        User alreadyExistingUser = userDao.findByUsername(user.getUsername());
        User alreadyExistingAdmin = userDao.findByUsername(admin.getUsername());

        if (alreadyExistingUser == null) {
            userDao.save(user);
        } else {
            System.out.println("User already exists");
        }

        if (alreadyExistingAdmin == null) {
            userDao.save(admin);
        } else {
            System.out.println("Admin already exists");
        }

        System.out.println("Available users: " + userDao.findAll());

        System.out.println("DB initiation finished");
    }
}
