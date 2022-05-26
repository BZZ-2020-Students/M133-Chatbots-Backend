package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.Chatbot;
import dev.zwazel.chatbots.classes.model.ChatbotUnknownTexts;
import dev.zwazel.chatbots.classes.model.Text;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Set;

/**
 * DAO for Text objects.
 *
 * @author Zwazel
 * @see Dao
 * @since 0.3
 */
public class TextDao extends Dao<Text, String> {
    /**
     * Default constructor.
     *
     * @author Zwazel
     * @since 0.3
     */
    public TextDao() {
        super(Text.class);
    }

    /**
     * Finds all texts that are associated with the given chatbot.
     *
     * @param chatbotId The id of the chatbot.
     * @return A set of texts.
     * @author Zwazel
     * @since 0.3
     */
    public Iterable<Text> findAllByChatbotId(String chatbotId) {
        ChatbotDao chatbotDao = new ChatbotDao();
        Chatbot chatbot = chatbotDao.findById(chatbotId);

        if (chatbot == null) {
            return List.of();
        }

        return this.findAllByChatbotFromDB(chatbot);
    }

    /**
     * Finds all texts that are associated with the given chatbot.
     *
     * @param chatbot The chatbot.
     * @return A set of texts.
     * @author Zwazel
     * @since 0.3
     */
    public Iterable<Text> findAllByChatbot(Chatbot chatbot) {
        return this.findAllByChatbotId(chatbot.getId());
    }

    /**
     * Finds all texts that are associated with the given chatbot from DB.
     *
     * @param chatbot The chatbot.
     * @return A set of texts.
     * @author Zwazel
     * @since 0.3
     */
    private Iterable<Text> findAllByChatbotFromDB(Chatbot chatbot) {
        Set<Text> texts = new java.util.HashSet<>(Set.of());

        Set<ChatbotUnknownTexts> unknownTextsChatbot = chatbot.getChatbotUnknownTexts();
        unknownTextsChatbot.forEach(ut -> {
            texts.add(ut.getUnknownText());
        });


        chatbot.getQuestionAnswers().forEach(qa -> {
            qa.getQuestionAnswerAnswers().forEach(qaa -> {
                texts.add(qaa.getAnswer());
            });

            qa.getQuestionAnswerQuestions().forEach(qaq -> {
                texts.add(qaq.getQuestion());
            });
        });

        return texts;
    }

    /**
     * Finds Text with the same text as the given text.
     *
     * @param text The text.
     * @return The Text.
     * @author Zwazel
     * @since 0.3
     */
    private Text findByText(String text) {
        return findBy("text", text);
    }

    /**
     * If the text is not in the database, it will be added.
     *
     * @param text The text.
     * @author Zwazel
     * @since 0.3
     */
    @Override
    public void save(Text text) {
        Text textInDB = findByText(text.getText());
        if (textInDB == null) {
            super.save(text);
        } else {
            text.setId(textInDB.getId());
        }
    }

    /**
     * If the text is not in the database, it will be added.
     *
     * @param t The collection of entities to save.
     * @author Zwazel
     * @since 0.3
     */
    @Override
    public void saveCollection(Iterable<Text> t) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        t.forEach(text -> {
            Text textInDB = findByText(text.getText());
            if (textInDB == null) {
                em.persist(text);
            } else {
                text.setId(textInDB.getId());
            }
        });
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Deletes a text by its text.
     *
     * @param text The text to delete.
     * @author Zwazel
     * @since 1.1.0
     */
    public void deleteByText(String text) {
        Text textObject = findByText(text);
        if (textObject != null) {
            delete(textObject);
        } else {
            throw new IllegalArgumentException("Text object with text \"" + text + "\" does not exist");
        }
    }
}
