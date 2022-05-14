package dev.zwazel.chatbots.classes.model;

import lombok.Getter;
import lombok.Setter;

/**
 * User class
 *
 * @author Zwazel
 * @version 0.1
 * @since 0.1
 */
@Setter
@Getter
public class User {
    /**
     * User id
     *
     * @since 0.1
     */
    private String id;

    /**
     * User name
     *
     * @since 0.1
     */
    private String name;

    /**
     * User constructor with id and name
     *
     * @param id   user id
     * @param name name of user
     * @author Zwazel
     * @since 0.1
     */
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * User to String method
     *
     * @author Zwazel
     * @since 0.1
     */
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * User toJson method
     *
     * @author Zwazel
     * @since 0.1
     */
    public String toJson() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"username\":\"" + name + "\"" +
                "}";
    }
}
