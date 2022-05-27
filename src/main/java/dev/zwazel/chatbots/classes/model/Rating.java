package dev.zwazel.chatbots.classes.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import dev.zwazel.chatbots.classes.enums.RatingEnum;
import dev.zwazel.chatbots.configs.Constants;
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
@JsonFilter("RatingFilter")
public class Rating {
    /**
     * The unique identifier of the rating.
     *
     * @since 0.3
     */
    @Id
    @Column(name = "id", nullable = false, length = Constants.MAX_UUID_LENGTH)
    @GeneratedValue(generator = "uuid")
    @Builder.Default
    @Size(max = Constants.MAX_UUID_LENGTH)
    private String id = UUID.randomUUID().toString();

    /**
     * The user that gave the rating.
     *
     * @see User
     * @since 0.2
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;

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
}
