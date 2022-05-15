package dev.zwazel.chatbots.authentication;

import lombok.Getter;
import lombok.Setter;

/**
 * Utility class for Login API.
 *
 * @author Zwazel
 * @since 0.1
 */
@Getter
@Setter
public class LoginForm {
    /**
     * The username.
     *
     * @since 0.1
     */
    private String username;

    /**
     * The password.
     *
     * @since 0.1
     */
    private String password;
}
