package dev.zwazel.chatbots.classes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

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
@Entity
@JsonIgnoreProperties({"createdAt"})
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
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @Builder.Default
    @Size(max = 36)
    private String id = UUID.randomUUID().toString();

    /**
     * the owner of the chatbot
     *
     * @see User
     * @since 0.2
     */
    @ManyToOne(cascade = {})
    @JoinColumn(name = "owner_id")
    @NonNull
    private User owner;

    /**
     * the name of the chatbot
     *
     * @since 0.2
     */
    @NonNull
    @Column(nullable = false)
    @Size(max = 16)
    private String chatbotName;

    /**
     * All the questions the chatbot can respond to
     *
     * @see QuestionAnswer
     * @since 0.2
     */
    @NonNull
    @OneToMany(mappedBy = "chatbot", orphanRemoval = true, cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private Set<QuestionAnswer> questionAnswers = new LinkedHashSet<>();

    /**
     * all the questions the chatbot can't yet respond to
     *
     * @see Text
     * @since 0.2
     */
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "Chatbot_unknownTexts",
            joinColumns = @JoinColumn(name = "chatbot_id"),
            inverseJoinColumns = @JoinColumn(name = "unknownTexts_id"))
    @ToString.Exclude
    private Set<Text> unknownTexts = new LinkedHashSet<>();

// TODO: 16.05.2022 LEVENSHTEIN DISTANCE
}
