package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.ChatbotUnknownTexts;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

/**
 * DAO for the ChatbotUnknownTexts class.
 *
 * @author Zwazel
 * @since 1.4
 */
public class UnknownTextsDao extends Dao<ChatbotUnknownTexts, String> {
    /**
     * Default constructor.
     *
     * @author Zwazel
     * @since 1.4
     */
    public UnknownTextsDao() {
        super(ChatbotUnknownTexts.class);
    }

    @Override
    public void save(ChatbotUnknownTexts chatbotUnknownTexts) throws IllegalArgumentException {
        TextDao textDao = new TextDao();
        try {
            textDao.save(chatbotUnknownTexts.getUnknownText());
        } catch (EntityExistsException ignored) {

        }

        super.save(chatbotUnknownTexts);
    }

    /**
     * Finds a chatbotUnknownTexts by its text.
     *
     * @param text the text to find the chatbotUnknownTexts for
     * @return the chatbotUnknownTexts with the given text or null if none was found
     * @author Zwazel
     * @since 1.4
     */
    public ChatbotUnknownTexts findByText(String text) {
        EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();

        entityManager.getTransaction().begin();
        ChatbotUnknownTexts chatbotUnknownTexts = null;
        try {
            chatbotUnknownTexts = entityManager.createQuery("SELECT t FROM ChatbotUnknownTexts t where lower(t.unknownText.text) = lower(:text)", ChatbotUnknownTexts.class)
                    .setParameter("text", text)
                    .getSingleResult();

            entityManager.getTransaction().commit();
        } catch (NoResultException ignored) {
        }

        return chatbotUnknownTexts;
    }
}
