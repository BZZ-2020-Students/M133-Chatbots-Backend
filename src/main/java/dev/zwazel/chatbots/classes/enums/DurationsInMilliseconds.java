package dev.zwazel.chatbots.classes.enums;

import lombok.Getter;

/**
 * Utility class for getting the milliseconds of a duration. This can be used to set the expiration time of the JWT.
 *
 * @author Zwazel
 * @since 0.1
 */
@Getter
public enum DurationsInMilliseconds {
    MINUTE(60000),
    HOUR(3600000),
    DAY(86400000),
    WEEK(604800000),
    MONTH(2592000000L),
    YEAR(31536000000L);

    /**
     * The duration in milliseconds.
     *
     * @since 0.1
     */
    private final long duration;

    /**
     * Constructor for the enum.
     *
     * @param duration The duration in milliseconds.
     * @author Zwazel
     * @since 0.1
     */
    DurationsInMilliseconds(long duration) {
        this.duration = duration;
    }
}
