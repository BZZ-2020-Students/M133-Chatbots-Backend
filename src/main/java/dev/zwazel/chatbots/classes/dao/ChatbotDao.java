package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.Chatbot;
import dev.zwazel.chatbots.classes.model.Rating;
import dev.zwazel.chatbots.classes.model.Text;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.List;

/**
 * Helper class for CRUD operations on Chatbot objects.
 *
 * @author Zwazel
 * @see Dao
 * @since 0.3
 */
public class ChatbotDao extends Dao<Chatbot, String> {
    /**
     * Default constructor.
     *
     * @since 0.3
     */
    public ChatbotDao() {
        super(Chatbot.class);
    }

    /**
     * Updates a Chatbot in the database. Duplicate names are not allowed.
     *
     * @param entity The entity to update.
     * @throws EntityExistsException If the entity already exists in the database.
     * @author Zwazel
     * @since 1.3.0
     */
    @Override
    public void update(Chatbot entity) {
        Chatbot chatbotInDb = findByName(entity.getChatbotName());
        if (chatbotInDb == null || chatbotInDb.getId().equals(entity.getId())) {
            super.update(entity);
        } else {
            throw new EntityExistsException("Chatbot already exists.");
        }
    }

    @Override
    public void save(Chatbot chatbot) {
        Chatbot existingChatbot = findByName(chatbot.getChatbotName());

        if (existingChatbot == null) {
            super.save(chatbot);
        } else {
            throw new IllegalArgumentException("Chatbot with name " + chatbot.getChatbotName() + " already exists.");
        }
    }

    @Override
    public void saveCollection(Iterable<Chatbot> t) {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    /**
     * Finds all chatbots of a User
     *
     * @param userId The id of the user
     * @return A list of chatbots of the user
     * @author Zwazel
     * @since 1.2.0
     */
    public Iterable<Chatbot> findByUserId(String userId) {
        EntityManagerFactory entityManagerFactory = getEntityManagerFactory();
        List<Chatbot> t = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            t = entityManager.createQuery("SELECT t FROM Chatbot t where t.user.id = :userID", Chatbot.class)
                    .setParameter("userID", userId)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception ignored) {

        }
        return t;
    }

    /**
     * Finds a chatbot by its name.
     *
     * @param name The name of the chatbot to find.
     * @return The chatbot with the given name.
     * @since 0.3
     */
    public Chatbot findByName(String name) {
        return this.findBy("chatbotName", name, false);
    }

    /**
     * Finds all chatbots with a given unknown text.
     *
     * @param text The unknown text to find.
     * @return A list of chatbots with the given unknown text.
     * @author Zwazel
     * @since 1.1.0
     */
    public List<Chatbot> findByUnknownText(Text text) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        List<Chatbot> chatbots = em.createQuery(
                        "SELECT c FROM Chatbot c, ChatbotUnknownTexts ut WHERE LOWER(:text) = LOWER(ut.unknownText.text) AND c.id = ut.chatbot.id", Chatbot.class)
                .setParameter("text", text.getText())
                .getResultList();
        em.getTransaction().commit();
        em.close();
        return chatbots;
    }

    /**
     * Deletes a chatbot by its name.
     *
     * @param name The name of the chatbot to delete.
     * @author Zwazel
     * @since 1.1.0
     */
    public void deleteByName(String name) {
        Chatbot chatbot = this.findByName(name);

        if (chatbot != null) {
            delete(chatbot);
        } else {
            throw new IllegalArgumentException("Chatbot with name " + name + " does not exist.");
        }
    }

    @Override
    public void delete(Chatbot chatbot) {
        RatingDao ratingDao = new RatingDao();
        Iterable<Rating> ratings = ratingDao.findByChatbotId(chatbot.getId());

        for (Rating rating : ratings) {
            ratingDao.delete(rating);
        }
        chatbot.setRatings(new HashSet<>(0));

        super.delete(chatbot);
    }

    @Override
    public void delete(String s) throws IllegalArgumentException {
        Chatbot t = findById(s);

        if (t != null) {
            delete(t);
        } else {
            throw new IllegalArgumentException("Entity with id " + s + " does not exist.");
        }
    }
}
