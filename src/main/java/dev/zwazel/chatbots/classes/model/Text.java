package dev.zwazel.chatbots.classes.model;

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
 * Text class
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
@Entity
@JsonIgnoreProperties({"questionAnswers_questions", "questionAnswers_answers", "chatbots"})
public class Text {
    /**
     * Text ID
     *
     * @since 0.2
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @Builder.Default
    @Size(max = 36)
    private String id = UUID.randomUUID().toString();

    /**
     * Text
     *
     * @since 0.2
     */
    @NonNull
    private String text;

    /**
     * How often this exact text has been used
     *
     * @since 0.2
     */
    @Builder.Default
    private Integer amountUsed = 0;

    /**
     * All the questions this text is in.
     *
     * @since 1.1.0
     */
    @ManyToMany(mappedBy = "questions", cascade = {CascadeType.REMOVE})
    @ToString.Exclude
    private Set<QuestionAnswer> questionAnswers_questions = new LinkedHashSet<>();

    /**
     * All the questions this text is in.
     *
     * @since 1.1.0
     */
    @ManyToMany(mappedBy = "answers", cascade = {CascadeType.REMOVE})
    @ToString.Exclude
    private Set<QuestionAnswer> questionAnswers_answers = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Text text = (Text) o;
        return id != null && Objects.equals(id, text.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
