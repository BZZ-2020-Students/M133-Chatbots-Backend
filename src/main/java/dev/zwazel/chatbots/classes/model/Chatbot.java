package dev.zwazel.chatbots.classes.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Chatbot class
 *
 * @author Zwazel
 * @since 0.2
 */
@Getter
@Setter
public class Chatbot {
    /**
     * The date the chatbot was created
     *
     * @since 0.2
     */
    private final LocalDate createdAt;

    /**
     * the id of the chatbot
     *
     * @since 0.2
     */
    private String id;

    /**
     * the owner of the chatbot
     *
     * @see User
     * @since 0.2
     */
    private User owner;

    /**
     * the name of the chatbot
     *
     * @since 0.2
     */
    private String name;

    /**
     * All the questions the chatbot can respond to
     *
     * @see QuestionAnswer
     * @since 0.2
     */
    private List<QuestionAnswer> questionAnswers;

    /**
     * all the questions the chatbot can't yet respond to
     *
     * @see Text
     * @since 0.2
     */
    private List<Text> unknownTexts;

    // TODO: 16.05.2022 LEVENSHTEIN DISTANCE

    /**
     * default constructor
     *
     * @author Zwazel
     * @since 0.2
     */
    public Chatbot() {
        createdAt = LocalDate.now();
    }

    /**
     * constructor with id and owner
     *
     * @param id    the id of the chatbot
     * @param owner the owner of the chatbot
     * @author Zwazel
     * @since 0.2
     */
    public Chatbot(String id, User owner) {
        this.id = id;
        this.owner = owner;
        createdAt = LocalDate.now();
    }
}
