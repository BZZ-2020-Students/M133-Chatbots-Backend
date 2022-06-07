package dev.zwazel.chatbots.classes.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.zwazel.chatbots.config.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;
import lombok.*;

import java.util.UUID;

/**
 * Model Class for all unknown texts of a chatbot.
 *
 * @author Zwazel
 * @since 1.1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonFilter("ChatbotUnknownTextsFilter")
@JsonIgnoreProperties({"chatbotId", "chatbotName"})
@ToString
public class ChatbotUnknownTexts {
    /**
     * The unique identifier of the unknown text.
     *
     * @since 1.1.0
     */
    @Id
    @Column(name = "id", nullable = false, length = Constants.UUID_LENGTH)
    @GeneratedValue(generator = "uuid")
    @Builder.Default
    @dev.zwazel.chatbots.util.annotation.UUID
    private String id = UUID.randomUUID().toString();

    /**
     * The text of the unknown text.
     *
     * @since 1.1.0
     */
    @ManyToOne
    @JoinColumn(name = "unknown_text_id")
    @NonNull
    @dev.zwazel.chatbots.util.annotation.Text
    private Text unknownText;

    /**
     * The chatbot of the unknown text.
     *
     * @since 1.1.0
     */
    @ManyToOne
    @JoinColumn(name = "chatbot_id")
    @NonNull
    private Chatbot chatbot;

    /**
     * the name of the chatbot of the unknown text.
     *
     * @since 1.3.0
     */
    @Transient
    @Size(min = Constants.MIN_NAME_LENGTH, max = Constants.MAX_NAME_LENGTH)
    @FormParam("chatbotName")
    private String chatbotName;

    /**
     * The chatbot id of the unknown text.
     *
     * @since 1.3.0
     */
    @Transient
    @dev.zwazel.chatbots.util.annotation.UUID
    @FormParam("chatbotId")
    private String chatbotId;

    /**
     * The amount of times this unknown text has been used.
     *
     * @since 1.3.0
     */
    @Builder.Default
    private int amountUsed = 0;
}
