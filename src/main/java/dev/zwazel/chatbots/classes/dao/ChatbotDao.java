package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.Chatbot;

/**
 * Helper class for CRUD operations on User objects.
 *
 * @author Zwazel
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
        return this.findBy("chatbot_name", name);
    }


}
