package dev.zwazel.chatbots.config;

import dev.zwazel.chatbots.classes.model.Text;

/**
 * Utility class for constants.
 *
 * @author Zwazel
 * @since 1.2.0
 */
public class Constants {
    /**
     * The max length of any Text.
     *
     * @see Text
     * @since 1.2.0
     */
    public static final int MAX_TEXT_LENGTH = 1024;

    /**
     * The max length of any UUID
     *
     * @since 1.2.0
     */
    public static final int UUID_LENGTH = 36;

    /**
     * The max length of any name
     *
     * @since 1.2.0
     */
    public static final int MAX_NAME_LENGTH = 32;

    /**
     * The min length of any name
     *
     * @since 1.3.0
     */
    public static final int MIN_NAME_LENGTH = 3;

    /**
     * The max length of any password
     *
     * @since 1.2.0
     */
    public static final int MAX_PASSWORD_LENGTH = 32;

    /**
     * The max length of a hashed password
     *
     * @since 1.4.0
     */
    public static final int MAX_HASHED_PASSWORD_LENGTH = 64;

    /**
     * The min length of any password
     *
     * @since 1.3.0
     */
    public static final int MIN_PASSWORD_LENGTH = 6;
}
