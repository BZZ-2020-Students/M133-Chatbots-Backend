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
     * Gets all Rating objects that are made by a given user.
     *
     * @param userId The id of the User to get the ratings for.
     * @return A collection of Rating objects.
     * @author Zwazel
     * @since 0.3
     */
    public Iterable<Rating> findByUserId(String userId) {
        EntityManagerFactory entityManagerFactory = getEntityManagerFactory();
        List<Rating> t = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            t = entityManager.createQuery("SELECT t FROM Rating t where t.user.id = :userID", Rating.class)
                    .setParameter("userID", userId)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception ignored) {

        }
        return t;
    }

    /**
     * Gets the one rating object made by one user for one chatbot
     *
     * @param userId    The id of the User to get the rating for.
     * @param chatbotId The id of the Chatbot to get the rating for.
     * @return The Rating object.
     * @author Zwazel
     * @since 1.3.0
     */
    public Rating findByUserAndChatbot(String userId, String chatbotId) {
        EntityManagerFactory entityManagerFactory = getEntityManagerFactory();
        Rating t = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            t = entityManager.createQuery("SELECT t FROM Rating t where t.user.id = :userID and t.chatbot.id = :chatbotID", Rating.class)
                    .setParameter("userID", userId)
                    .setParameter("chatbotID", chatbotId)
                    .getSingleResult();
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
    @Override
    public void save(Rating rating) throws IllegalArgumentException {
        //noinspection ConstantConditions
        if (rating.getChatbot() == null || rating.getUser() == null) {
            throw new IllegalArgumentException("Rating object is missing Chatbot or User.");
        }

        Rating ratingInDb = findByUserAndChatbot(rating.getUser().getId(), rating.getChatbot().getId());

        if (ratingInDb == null) {
            super.save(rating);
        } else {
            rating.setId(ratingInDb.getId());
            throw new IllegalArgumentException("You can not rate the same chatbot twice.");
        }
    }

    /**
     * saves an iterable of Rating objects.
     *
     * @param t The collection of entities to save.
     * @author Zwazel
     * @since 0.3
     */
    @Override
    public void saveCollection(Iterable<Rating> t) {
        for (Rating rating : t) {
            try {
                save(rating);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
