package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.Chatbot;
import dev.zwazel.chatbots.classes.model.ChatbotUnknownTexts;
import dev.zwazel.chatbots.classes.model.Text;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Helper class for CRUD operations on User objects.
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

    public List<Chatbot> findByText(Text text) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        List<Chatbot> chatbots = em.createQuery(
                        "SELECT c FROM Chatbot c WHERE :text IN (c.unknownTexts)"
                        , Chatbot.class)
                .setParameter("text", text.getText())
                .getResultList();
        em.getTransaction().commit();
        em.close();
        return chatbots;
    }

    @Override
    public void save(Chatbot chatbot) {
        TextDao textDao = new TextDao();

        Set<ChatbotUnknownTexts> unknownTextsChatbot = chatbot.getChatbotUnknownTexts();
        List<Text> unknownTexts = new ArrayList<>();

        unknownTextsChatbot.forEach(ut -> {
            unknownTexts.add(ut.getUnknownText());
        });

        textDao.saveCollection(unknownTexts);

        super.save(chatbot);
    }

    @Override
    public void saveCollection(Iterable<Chatbot> t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
