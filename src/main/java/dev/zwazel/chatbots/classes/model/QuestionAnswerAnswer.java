package dev.zwazel.chatbots.classes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class QuestionAnswerAnswer {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @Builder.Default
    @Size(max = 36)
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "answer_id")
    @NonNull
    private Text answer;

    @ManyToOne
    @JoinColumn(name = "question_answer_id")
    private QuestionAnswer questionAnswer;

}
