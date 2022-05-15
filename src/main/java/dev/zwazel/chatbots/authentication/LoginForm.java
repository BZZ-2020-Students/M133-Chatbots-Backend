package dev.zwazel.chatbots.authentication;

/**
 * Utility class for Login API.
 *
 * @author Zwazel
 * @since 0.1
 */
public class LoginForm {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
