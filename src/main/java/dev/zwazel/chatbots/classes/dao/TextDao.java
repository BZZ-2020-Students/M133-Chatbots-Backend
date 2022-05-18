package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.Chatbot;
import dev.zwazel.chatbots.classes.model.Text;

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
        Chatbot chatbot = chatbotDao.find(chatbotId);
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
        Set<Text> texts = chatbot.getUnknownTexts();
        chatbot.getQuestionAnswers().forEach(qa -> {
            texts.addAll(qa.getQuestions());
            texts.addAll(qa.getAnswers());
        });

        return texts;
    }
}
