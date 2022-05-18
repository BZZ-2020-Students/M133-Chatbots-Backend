package dev.zwazel.chatbots.util;

import dev.zwazel.chatbots.classes.dao.UserDao;
import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import org.apache.log4j.Logger;

@Singleton
@Startup
public class InitDbOnStartup {
    private static final Logger LOG = Logger.getLogger(InitDbOnStartup.class.getName());

    @PostConstruct
    public void init() {
        LOG.info("Initializing database");

        User user = User.builder()
                .username("test")
                .password("test")
                .userRole(UserRole.USER)
                .build();

        UserDao userDao = new UserDao();
        User alreadyExistingUser = userDao.findByUsername(user.getUsername());
        if (alreadyExistingUser == null) {
            userDao.save(user);
        } else {
            LOG.info("User already exists");
        }

        LOG.info("Database initialized");
    }
}
