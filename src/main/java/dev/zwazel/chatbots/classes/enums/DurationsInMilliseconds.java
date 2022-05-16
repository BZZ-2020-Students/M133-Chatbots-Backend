package dev.zwazel.chatbots.classes.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Utility class for getting the milliseconds of a duration. This can be used to set the expiration time of the JWT.
 *
 * @author Zwazel
 * @since 0.1
 */
@Getter
@RequiredArgsConstructor
public enum DurationsInMilliseconds {
    /**
     * The duration of a minute in milliseconds.
     */
    MINUTE(60000),

    /**
     * The duration of an hour in milliseconds.
     */
    HOUR(3600000),

    /**
     * The duration of a day in milliseconds.
     */
    DAY(86400000),

    /**
     * The duration of a week in milliseconds.
     */
    WEEK(604800000),

    /**
     * The duration of a month in milliseconds.
     */
    MONTH(2592000000L),

    /**
     * The duration of a year in milliseconds.
     */
    YEAR(31536000000L);

    /**
     * The duration in milliseconds.
     *
     * @since 0.1
     */
    private final long duration;
}
