package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.Chatbot;
import dev.zwazel.chatbots.classes.model.Text;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

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
}
