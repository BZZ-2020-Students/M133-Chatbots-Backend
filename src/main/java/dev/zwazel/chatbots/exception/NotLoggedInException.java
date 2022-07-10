package dev.zwazel.chatbots.exception;

/**
 * Exception thrown when a user is not logged in.
 *
 * @author Zwazel
 * @since 1.4
 */
public class NotLoggedInException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @author Zwazel
     * @since 1.4
     */
    public NotLoggedInException() {
        super("User is not logged in");
    }
}
