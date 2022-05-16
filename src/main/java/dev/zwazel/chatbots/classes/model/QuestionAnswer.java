package dev.zwazel.chatbots.classes.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * QuestionAnswer class.
 * This class contains a list of questions with corresponding answers which the chatbot will respond with.
 *
 * @author Zwazel
 * @since 0.2
 */
@Getter
@Setter
public class QuestionAnswer {
    /**
     * A List containing all the questions.
     *
     * @see Text
     * @since 0.2
     */
    private List<Text> questions;

    /**
     * A List containing all the answers.
     *
     * @see Text
     * @since 0.2
     */
    private List<Text> answers;
}
