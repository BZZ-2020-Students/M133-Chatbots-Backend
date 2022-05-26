package dev.zwazel.chatbots.classes.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
public class QuestionAnswer {
    /**
     * The unique identifier of the question answer.
     *
     * @since 0.3
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @Builder.Default
    @Size(max = 36)
    private String id = UUID.randomUUID().toString();

    /**
     * the Chatbot which this question answer belongs to.
     *
     * @see Chatbot
     * @since 0.3
     */
    @ManyToOne(cascade = {})
    @JoinColumn(name = "chatbot_id")
    @ToString.Exclude
    @NonNull
    private Chatbot chatbot;

    @OneToMany(mappedBy = "questionAnswer", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Builder.Default
    @ToString.Exclude
    private Set<QuestionAnswerQuestion> questionAnswerQuestions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "questionAnswer", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @Builder.Default
    private Set<QuestionAnswerAnswer> questionAnswerAnswers = new LinkedHashSet<>();

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
