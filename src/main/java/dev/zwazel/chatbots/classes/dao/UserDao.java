package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.User;

import java.util.UUID;

public class UserDao extends Dao<User, UUID> {
    public UserDao() {
        super(User.class);
    }

    public User findByUsername(String username) {
        return findBy("username", username);
    }
}
