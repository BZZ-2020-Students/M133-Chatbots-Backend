package dev.zwazel.chatbots.classes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

;

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
    @ManyToOne
    @JoinColumn(name = "owner_id")
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
    @OneToMany(mappedBy = "chatbot", orphanRemoval = true)
    private Set<QuestionAnswer> questionAnswers = new LinkedHashSet<>();

    /**
     * all the questions the chatbot can't yet respond to
     *
     * @see Text
     * @since 0.2
     */
    @Builder.Default
    @ManyToMany
    @JoinTable(name = "Chatbot_unknownTexts",
            joinColumns = @JoinColumn(name = "chatbot_id"),
            inverseJoinColumns = @JoinColumn(name = "unknownTexts_id"))
    private Set<Text> unknownTexts = new LinkedHashSet<>();

// TODO: 16.05.2022 LEVENSHTEIN DISTANCE

    public String toJson() {
        return "{\n" +
                "  \"owner\": \"" + owner.getUsername() + "\",\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"questionAnswers\": " + questionAnswers + ",\n" +
                "  \"unknownTexts\": " + unknownTexts + "\n" +
                "}";
    }
}
