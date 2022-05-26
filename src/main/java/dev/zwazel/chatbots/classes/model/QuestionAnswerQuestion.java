package dev.zwazel.chatbots.classes.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

/**
 * Model class for QuestionAnswerQuestion. Stores the question for QuestionAnswer
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
@JsonFilter("QuestionAnswerQuestionFilter")
public class QuestionAnswerQuestion {
    /**
     * The unique identifier for the QuestionAnswerQuestion
     *
     * @since 1.1.0
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @Builder.Default
    @Size(max = 36)
    private String id = UUID.randomUUID().toString();

    /**
     * The question for the QuestionAnswerQuestion
     *
     * @since 1.1.0
     */
    @ManyToOne
    @JoinColumn(name = "question_id")
    @NonNull
    private Text question;

    /**
     * The questionAnswer
     *
     * @since 1.1.0
     */
    @ManyToOne
    @JoinColumn(name = "question_answer_id")
    private QuestionAnswer questionAnswer;
}
