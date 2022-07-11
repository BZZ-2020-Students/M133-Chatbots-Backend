package dev.zwazel.chatbots.classes.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.zwazel.chatbots.config.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;
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
@JsonIgnoreProperties({"userID", "username"})
@JsonFilter("ChatbotFilter")
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
    @Column(name = "id", nullable = false, length = Constants.UUID_LENGTH)
    @GeneratedValue(generator = "uuid")
    @Builder.Default
    @dev.zwazel.chatbots.util.annotation.UUID
    private String id = UUID.randomUUID().toString();


    /**
     * the name of the chatbot
     *
     * @since 0.2
     */
    @NonNull
    @Column(nullable = false, length = Constants.MAX_NAME_LENGTH)
    @Size(min = Constants.MIN_NAME_LENGTH, max = Constants.MAX_NAME_LENGTH)
    @FormParam("name")
    private String chatbotName;

    /**
     * All the questions the chatbot can respond to
     *
     * @see QuestionAnswer
     * @since 0.2
     */
    @OneToMany(mappedBy = "chatbot", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @Builder.Default
    private Set<QuestionAnswer> questionAnswers = new LinkedHashSet<>();

    /**
     * All the ratings associated with the chatbot
     *
     * @see Rating
     * @since 1.1.0
     */
    @OneToMany(mappedBy = "chatbot", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @Builder.Default
    private Set<Rating> ratings = new LinkedHashSet<>();

    /**
     * the owner of the chatbot
     *
     * @see User
     * @since 0.2
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    // fetch everything
    private User user;

    /**
     * All the texts the chatbot can't respond to yet.
     *
     * @since 1.1.0
     */
    @OneToMany(mappedBy = "chatbot", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    @Builder.Default
    private Set<ChatbotUnknownTexts> chatbotUnknownTexts = new LinkedHashSet<>();

    /**
     * FormParam for User ID
     *
     * @since 1.3.0
     */
    @Transient
    @FormParam("userId")
    @dev.zwazel.chatbots.util.annotation.UUID
    private String userID;

    /**
     * FormParam for User Name
     *
     * @since 1.3.0
     */
    @Transient
    @Size(min = Constants.MIN_NAME_LENGTH, max = Constants.MAX_NAME_LENGTH)
    @FormParam("username")
    private String username;
}
