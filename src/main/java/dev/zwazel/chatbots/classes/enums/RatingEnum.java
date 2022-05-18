package dev.zwazel.chatbots.classes.enums;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Enum for the rating of a chatbot.
 *
 * @author Zwazel
 * @since 0.2
 */
@Getter
@RequiredArgsConstructor
public enum RatingEnum {
    /**
     * The user likes the chatbot.
     *
     * @since 0.2
     */
    UPVOTE(1),

    /**
     * The user dislikes the chatbot.
     *
     * @since 0.2
     */
    DOWNVOTE(-1);

    /**
     * The value of the rating.
     *
     * @since 0.2
     */
    @NonNull
    private final Integer value;
}
