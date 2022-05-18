package dev.zwazel.chatbots.util;

import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.UUID;

@Singleton
@Startup
public class InitDbOnStartup {
    private static final Logger LOG = Logger.getLogger(InitDbOnStartup.class.getName());

    @PostConstruct
    public void init() {
        LOG.info("Initializing database");

        User user = User.builder()
                .userRole(UserRole.USER)
                .name("Zwazel")
                .password("securePassword")
                .id(new UUID(0, 0))
                .build();

        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        HibernateUtil.closeSession();

        LOG.info("Database initialized");
    }
}
