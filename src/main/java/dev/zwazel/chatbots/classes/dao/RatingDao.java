package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.Rating;

/**
 * Dao class for Rating Objects.
 *
 * @author Zwazel
 * @since 0.3
 */
public class RatingDao extends Dao<Rating, String> {
    /**
     * Default constructor.
     *
     * @since 0.3
     */
    public RatingDao() {
        super(Rating.class);
    }
}
