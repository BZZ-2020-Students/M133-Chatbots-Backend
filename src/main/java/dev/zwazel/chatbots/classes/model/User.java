package dev.zwazel.chatbots.classes.model;

import dev.zwazel.chatbots.classes.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

/**
 * User class
 *
 * @author Zwazel
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
     * User password
     *
     * @since 0.2
     */
    private String password;

    /**
     * The role of the user
     *
     * @since 0.2
     */
    private UserRole userRole;

    /**
     * Default User constructor
     *
     * @author Zwazel
     * @since 0.2
     */
    public User() {
    }


    /**
     * User constructor without id
     *
     * @param name     name of user
     * @param password password of user
     * @author Zwazel
     * @since 0.2
     */
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * User constructor with userRole
     *
     * @param name     name of user
     * @param password password of user
     * @param userRole role of user
     * @author Zwazel
     * @since 0.2
     */
    public User(String name, String password, UserRole userRole) {
        this.name = name;
        this.password = password;
        this.userRole = userRole;
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
     * @return A JSON string of the user
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
