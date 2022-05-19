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

    /**
     * Saves a Rating object.
     *
     * @param rating Rating object to save.
     * @author Zwazel
     * @since 0.3
     */
    // TODO: 19.05.2022 - need to find a way to check if the rating already exists. Users should not be able to rate the same thing twice.
    @Override
    public void save(Rating rating) {
        super.save(rating);
    }

    /**
     * saves an iterable of Rating objects.
     *
     * @param t The collection of entities to save.
     * @author Zwazel
     * @since 0.3
     */
    // TODO: 19.05.2022 - need to find a way to check if the rating already exists. Users should not be able to rate the same thing twice.
    @Override
    public void saveCollection(Iterable<Rating> t) {
        super.saveCollection(t);
    }
}
