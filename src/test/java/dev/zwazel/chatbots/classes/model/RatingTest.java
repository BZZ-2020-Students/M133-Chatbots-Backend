package dev.zwazel.chatbots.classes.model;

import dev.zwazel.chatbots.classes.enums.RatingEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Rating class.
 *
 * @author Zwazel
 * @since 1.1.0
 */
class RatingTest {
    /**
     * Chatbot object used for testing.
     */
    private Chatbot chatbot;

    /**
     * User object used for testing.
     */
    private User user;

    /**
     * Sets up the test environment.
     *
     * @author Zwazel
     * @since 1.1.0
     */
    @BeforeEach
    void setUp() {
        chatbot = new Chatbot();
        user = new User();
    }

    /**
     * Tests if, when building without specifying a user, a NullPointerException is thrown.
     *
     * @author Zwazel
     * @since 1.1.0
     */
    @Test
    void testBuildingRatingWithoutSpecifyingUser() {
        assertThrows(NullPointerException.class, () ->
                Rating.builder()
                        .rating(RatingEnum.UPVOTE)
                        .chatbot(chatbot)
                        .favourite(true)
                        .build()
        );
    }

    /**
     * Tests if, when building without a chatbot, a NullPointerException is thrown.
     *
     * @author Zwazel
     * @since 1.1.0
     */
    @Test
    void testBuildingRatingWithoutSpecifyingChatbot() {
        assertThrows(NullPointerException.class, () ->
                Rating.builder()
                        .rating(RatingEnum.UPVOTE)
                        .user(user)
                        .favourite(true)
                        .build()
        );
    }

    /**
     * Tests if, when building without specifying a rating, a NullPointerException is thrown.
     *
     * @author Zwazel
     * @since 1.1.0
     */
    @Test
    void testBuildingRatingWithoutSpecifyingRating() {
        assertThrows(NullPointerException.class, () ->
                Rating.builder()
                        .user(user)
                        .chatbot(chatbot)
                        .favourite(true)
                        .build()
        );
    }

    /**
     * Tests if the building method is successful when everything is specified.
     *
     * @author Zwazel
     * @since 1.1.0
     */
    @Test
    void testBuildingRatingWithSpecifyingEverythingNeeded() {
        Rating rating = Rating.builder()
                .user(user)
                .chatbot(chatbot)
                .rating(RatingEnum.UPVOTE)
                .favourite(true)
                .build();

        assertEquals(user, rating.getUser());
        assertEquals(chatbot, rating.getChatbot());
        assertEquals(rating.getRating(), RatingEnum.UPVOTE);
        assertTrue(rating.isFavourite());
    }

    /**
     * Tests if, when building without a favourite, the favourite is set to the correct value.
     *
     * @author Zwazel
     * @since 1.1.0
     */
    @Test
    void testBuildingRatingWithoutSpecifyingFavourite() {
        Rating rating = Rating.builder()
                .user(user)
                .chatbot(chatbot)
                .rating(RatingEnum.UPVOTE)
                .build();

        assertEquals(user, rating.getUser());
        assertEquals(chatbot, rating.getChatbot());
        assertEquals(rating.getRating(), RatingEnum.UPVOTE);
        assertFalse(rating.isFavourite());
    }
}