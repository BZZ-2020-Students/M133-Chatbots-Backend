package dev.zwazel.chatbots.classes.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonFilter("ChatbotUnknownTextsFilter")
public class ChatbotUnknownTexts {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @Builder.Default
    @Size(max = 36)
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "unknown_text_id")
    @NonNull
    private Text unknownText;

    @ManyToOne
    @JoinColumn(name = "chatbot_id")
    @NonNull
    private Chatbot chatbot;

}
