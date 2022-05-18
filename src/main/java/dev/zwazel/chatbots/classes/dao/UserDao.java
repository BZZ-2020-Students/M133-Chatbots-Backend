package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.User;

public class UserDao extends Dao<User, String> {
    public UserDao() {
        super(User.class);
    }

    public User findByUsername(String username) {
        return findBy("username", username);
    }

    public User findById(String id) {
        return findBy("id", id);
    }
}
