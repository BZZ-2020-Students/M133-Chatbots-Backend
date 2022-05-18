package dev.zwazel.chatbots.classes.model;

import dev.zwazel.chatbots.classes.enums.RatingEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

/**
 * This class represents a rating that a user gave a chatbot.
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
public class Rating {
    /**
     * The unique identifier of the rating.
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
     * The user that gave the rating.
     *
     * @see User
     * @since 0.2
     */
    @ManyToOne(cascade = {})
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;

    /**
     * The chatbot that was rated.
     *
     * @see Chatbot
     * @since 0.2
     */
    @ManyToOne(cascade = {})
    @JoinColumn(name = "chatbot_id")
    @NonNull
    private Chatbot chatbot;

    /**
     * The rating value.
     *
     * @see RatingEnum
     * @since 0.2
     */
    @NonNull
    @Enumerated(EnumType.STRING)
    private RatingEnum rating;

    /**
     * if the user set this chatbot as favorite.
     *
     * @since 0.2
     */
    @Builder.Default
    private boolean favourite = false;
}
