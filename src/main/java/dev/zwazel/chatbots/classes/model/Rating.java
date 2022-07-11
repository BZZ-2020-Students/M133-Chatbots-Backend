package dev.zwazel.chatbots.classes.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.zwazel.chatbots.classes.enums.RatingEnum;
import dev.zwazel.chatbots.config.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;
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
@JsonIgnoreProperties({"userId", "username", "chatbotId", "chatbotName"})
public class Rating {
    /**
     * The unique identifier of the rating.
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
    @NotNull
    @FormParam("rating")
    @Enumerated(EnumType.STRING)
    private RatingEnum rating;

    /**
     * if the user set this chatbot as favorite.
     *
     * @since 0.2
     */
    @Builder.Default
    @FormParam("favourite")
    private boolean favourite = false;

    /**
     * The chatbot that was rated.
     *
     * @see Chatbot
     * @since 0.2
     */
    @ManyToOne
    @JoinColumn(name = "chatbot_id")
    @NonNull
    private Chatbot chatbot;

    /**
     * The name of the chatbot that got rated
     *
     * @since 1.3.0
     */
    @Transient
    @Size(min = Constants.MIN_NAME_LENGTH, max = Constants.MAX_NAME_LENGTH)
    @FormParam("chatbotName")
    private String chatbotName;

    /**
     * The id of the chatbot that got rated
     *
     * @since 1.3.0
     */
    @Transient
    @dev.zwazel.chatbots.util.annotation.UUID
    @FormParam("chatbotId")
    private String chatbotId;
}
