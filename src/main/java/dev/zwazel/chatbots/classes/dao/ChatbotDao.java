package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.Chatbot;
import dev.zwazel.chatbots.classes.model.Text;
import jakarta.persistence.EntityManager;

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
