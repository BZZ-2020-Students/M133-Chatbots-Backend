package dev.zwazel.chatbots.util;

import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.User;
import org.hibernate.Session;

import java.util.UUID;

public class TestDbConnection {
    public static void main(String[] args) {
        User user = User.builder().id(new UUID(0, 0)).name("test").password("test").userRole(UserRole.USER).build();

        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        HibernateUtil.closeSession();
    }
}
