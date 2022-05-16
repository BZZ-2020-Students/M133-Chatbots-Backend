package dev.zwazel.chatbots.classes.model;

import dev.zwazel.chatbots.classes.enums.RatingEnum;
import lombok.*;

/**
 * This class represents a rating that a user gave a chatbot.
 *
 * @author Zwazel
 * @since 0.2
 */
@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Rating {
    /**
     * The user that gave the rating.
     *
     * @see User
     * @since 0.2
     */
    @NonNull
    private User user;

    /**
     * The chatbot that was rated.
     *
     * @see Chatbot
     * @since 0.2
     */
    @NonNull
    private Chatbot chatbot;

    /**
     * The rating value.
     *
     * @see RatingEnum
     * @since 0.2
     */
    @NonNull
    private RatingEnum rating;

    /**
     * if the user set this chatbot as favorite.
     *
     * @since 0.2
     */
    @Builder.Default
    private boolean favourite = false;
}
