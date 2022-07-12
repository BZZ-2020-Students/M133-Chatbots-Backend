package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.ChatbotUnknownTexts;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

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
