package dev.zwazel.chatbots.util;

import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.User;

import java.util.UUID;

public class TestDbConnection {
    public static void main(String[] args) {
        User user = User.builder()
                .username("test")
                .password("test")
                .userRole(UserRole.USER)
                .build();


        Dao<User, UUID> userDao = new Dao<>(User.class);
        userDao.save(user);

        user = userDao.find(user.getId());

        System.out.println(user.getUsername());
    }
}
