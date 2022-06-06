package dev.zwazel.chatbots.classes.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import dev.zwazel.chatbots.configs.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
@ToString
public class ChatbotUnknownTexts {
    /**
     * The unique identifier of the unknown text.
     *
     * @since 1.1.0
     */
    @Id
    @Column(name = "id", nullable = false, length = Constants.MAX_UUID_LENGTH)
    @GeneratedValue(generator = "uuid")
    @Builder.Default
    @Size(max = Constants.MAX_UUID_LENGTH)
    private String id = UUID.randomUUID().toString();

    /**
     * The text of the unknown text.
     *
     * @since 1.1.0
     */
    @ManyToOne
    @JoinColumn(name = "unknown_text_id")
    @NonNull
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
     * The amount of times this unknown text has been used.
     *
     * @since 1.3.0
     */
    @Builder.Default
    private int amountUsed = 0;
}
