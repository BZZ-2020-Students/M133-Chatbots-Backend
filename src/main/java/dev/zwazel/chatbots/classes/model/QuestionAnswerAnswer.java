package dev.zwazel.chatbots.classes.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import dev.zwazel.chatbots.config.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

/**
 * Model class for QuestionAnswerAnswer. Stores Answers for QuestionAnswer
 *
 * @author Zwazel
 * @since 1.1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@JsonFilter("QuestionAnswerAnswerFilter")
public class QuestionAnswerAnswer {
    /**
     * The unique identifier for the QuestionAnswerAnswer
     *
     * @since 1.1.0
     */
    @Id
    @Column(name = "id", nullable = false, length = Constants.UUID_LENGTH)
    @GeneratedValue(generator = "uuid")
    @Builder.Default
    @dev.zwazel.chatbots.util.annotation.UUID
    private String id = UUID.randomUUID().toString();

    /**
     * The answer for the QuestionAnswer
     *
     * @since 1.1.0
     */
    @ManyToOne
    @JoinColumn(name = "answer_id")
    @NonNull
    private Text answer;

    /**
     * The QuestionAnswer for the QuestionAnswerAnswer
     *
     * @since 1.1.0
     */
    @ManyToOne
    @JoinColumn(name = "question_answer_id")
    private QuestionAnswer questionAnswer;

    /**
     * How often this Answer has been used
     *
     * @since 1.3.0
     */
    @Builder.Default
    private int amountUsed = 0;
}
