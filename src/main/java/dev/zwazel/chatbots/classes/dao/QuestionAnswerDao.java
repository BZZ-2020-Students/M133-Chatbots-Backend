package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.QuestionAnswer;

/**
 * Dao Class for QuestionAnswer objects
 *
 * @author Zwazel
 * @since 0.3
 */
public class QuestionAnswerDao extends Dao<QuestionAnswer, String> {
    /**
     * Default constructor.
     *
     * @author Zwazel
     * @since 0.3
     */
    public QuestionAnswerDao() {
        super(QuestionAnswer.class);
    }
}