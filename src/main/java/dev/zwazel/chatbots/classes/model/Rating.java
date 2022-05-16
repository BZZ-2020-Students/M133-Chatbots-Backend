package dev.zwazel.chatbots.classes.model;

import dev.zwazel.chatbots.classes.enums.RatingEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a rating that a user gave a chatbot.
 *
 * @author Zwazel
 * @since 0.2
 */
@Getter
@Setter
public class Rating {
    /**
     * The user that gave the rating.
     *
     * @see User
     * @since 0.2
     */
    private User user;

    /**
     * The chatbot that was rated.
     *
     * @see Chatbot
     * @since 0.2
     */
    private Chatbot chatbot;

    /**
     * The rating value.
     *
     * @see RatingEnum
     * @since 0.2
     */
    private RatingEnum rating;

    /**
     * if the user set this chatbot as favorite.
     *
     * @since 0.2
     */
    private boolean favourite;
}
