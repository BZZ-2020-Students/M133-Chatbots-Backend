package dev.zwazel.chatbots.util;

import dev.zwazel.chatbots.classes.dao.UserDao;
import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.User;

public class TestDbConnection {
    public static void main(String[] args) {
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
            System.out.println("User already exists");
        }

        System.out.println(user.getUsername());
    }
}
