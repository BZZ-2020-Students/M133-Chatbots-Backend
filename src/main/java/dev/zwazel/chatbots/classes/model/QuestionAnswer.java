package dev.zwazel.chatbots.classes.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.zwazel.chatbots.config.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * QuestionAnswer class.
 * This class contains a list of questions with corresponding answers which the chatbot will respond with.
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
@Entity
@JsonFilter("QuestionAnswerFilter")
@JsonIgnoreProperties({"chatbotId", "chatbotName"})
public class QuestionAnswer {
    /**
     * The unique identifier of the question answer.
     *
     * @since 0.3
     */
    @Id
    @Column(name = "id", nullable = false, length = Constants.UUID_LENGTH)
    @GeneratedValue(generator = "uuid")
    @Builder.Default
    @dev.zwazel.chatbots.util.annotation.UUID
    private String id = UUID.randomUUID().toString();

    /**
     * the Chatbot which this question answer belongs to.
     *
     * @see Chatbot
     * @since 0.3
     */
    @ManyToOne()
    @JoinColumn(name = "chatbot_id")
    @ToString.Exclude
    @NonNull
    private Chatbot chatbot;

    /**
     * The name of the chatbot which this question answer belongs to.
     *
     * @since 1.3.0
     */
    @Transient
    @Size(min = Constants.MIN_NAME_LENGTH, max = Constants.MAX_NAME_LENGTH)
    @FormParam("chatbotName")
    private String chatbotName;

    /**
     * The unique identifier of the chatbot which this question answer belongs to.
     *
     * @since 1.3.0
     */
    @Transient
    @dev.zwazel.chatbots.util.annotation.UUID
    @FormParam("chatbotId")
    private String chatbotId;

    /**
     * All questions which this question answer contains.
     *
     * @see QuestionAnswerQuestion
     * @since 1.1.0
     */
    @OneToMany(mappedBy = "questionAnswer", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Builder.Default
    @ToString.Exclude
    private Set<QuestionAnswerQuestion> questionAnswerQuestions = new LinkedHashSet<>();

    /**
     * All answers which this question answer contains.
     *
     * @see QuestionAnswerAnswer
     * @since 1.1.0
     */
    @OneToMany(mappedBy = "questionAnswer", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @Builder.Default
    private Set<QuestionAnswerAnswer> questionAnswerAnswers = new LinkedHashSet<>();

    public QuestionAnswerAnswer getRandomAnswer() {
        if (questionAnswerAnswers.isEmpty()) {
            return null;
        }
        int randomIndex = (int) (Math.random() * questionAnswerAnswers.size());
        return questionAnswerAnswers.toArray(new QuestionAnswerAnswer[0])[randomIndex];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        QuestionAnswer that = (QuestionAnswer) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
