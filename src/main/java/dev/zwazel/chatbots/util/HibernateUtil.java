package dev.zwazel.chatbots.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 * <br>
 * <a href="https://stackoverflow.com/a/28250966/12287687">Reference</a>
 *
 * @author Zwazel
 * @since 0.3
 */
public class HibernateUtil {
    /**
     * The SessionFactory object.
     *
     * @since 0.3
     */
    private static SessionFactory sessionFactory;
    private static Session session;

    /**
     * Builds the SessionFactory object.
     *
     * @return The SessionFactory object.
     * @author Zwazel
     * @since 0.3
     */
    private static SessionFactory buildSessionFactory() {
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("hibernate.properties"));
        } catch (Exception e) {
            System.out.println("cannot load properties file.");
        }
        return new Configuration().setProperties(properties)
                .addAnnotatedClass(dev.zwazel.chatbots.classes.model.User.class)
                .addAnnotatedClass(dev.zwazel.chatbots.classes.model.Chatbot.class)
                .addAnnotatedClass(dev.zwazel.chatbots.classes.model.QuestionAnswer.class)
                .addAnnotatedClass(dev.zwazel.chatbots.classes.model.Text.class)
                .addAnnotatedClass(dev.zwazel.chatbots.classes.model.Rating.class)
                .buildSessionFactory();
    }

    /**
     * Gets the SessionFactory object.
     *
     * @return The SessionFactory object.
     * @author Zwazel
     * @since 0.3
     */
    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }

        return sessionFactory;
    }

    /**
     * Gets a Session object.
     *
     * @return The Session object.
     * @author Zwazel
     * @since 0.3
     */
    public static Session getSession() {
        if (session == null) {
            session = getSessionFactory().openSession();
        } else if (!session.isOpen()) {
            session = getSessionFactory().openSession();
        }

        return session;
    }

    /**
     * Closes the Session object.
     *
     * @author Zwazel
     * @since 0.3
     */
    public static void closeSession() {
        if (session != null) {
            session.close();
        }
    }
}
