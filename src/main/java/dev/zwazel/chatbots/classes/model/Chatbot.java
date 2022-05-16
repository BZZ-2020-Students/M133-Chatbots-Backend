package dev.zwazel.chatbots.classes.model;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Chatbot class
 *
 * @author Zwazel
 * @since 0.2
 */
@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Chatbot {
    /**
     * The date the chatbot was created
     *
     * @since 0.2
     */
    @Builder.Default
    @NonNull
    private final LocalDate createdAt = LocalDate.now();

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
    @NonNull
    private User owner;

    /**
     * the name of the chatbot
     *
     * @since 0.2
     */
    @NonNull
    private String name;

    /**
     * All the questions the chatbot can respond to
     *
     * @see QuestionAnswer
     * @since 0.2
     */
    @NonNull
    private List<QuestionAnswer> questionAnswers;

    /**
     * all the questions the chatbot can't yet respond to
     *
     * @see Text
     * @since 0.2
     */
    @Builder.Default
    private List<Text> unknownTexts = new ArrayList<>();

    // TODO: 16.05.2022 LEVENSHTEIN DISTANCE
}
