package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.Rating;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

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
     * Gets all Rating objects that are with a given Chatbot
     *
     * @param chatbotId The id of the Chatbot to get the ratings for.
     * @return A collection of Rating objects.
     * @author Zwazel
     * @since 0.3
     */
    public Iterable<Rating> findByChatbotId(String chatbotId) {
        EntityManagerFactory entityManagerFactory = getEntityManagerFactory();
        List<Rating> t = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            t = entityManager.createQuery("SELECT t FROM Rating t where t.chatbot.id = :chatbotID", Rating.class)
                    .setParameter("chatbotID", chatbotId)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception ignored) {

        }
        return t;
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
