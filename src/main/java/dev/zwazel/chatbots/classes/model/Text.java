package dev.zwazel.chatbots.classes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.UUID;

/**
 * Text class
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
public class Text {
    /**
     * Text ID
     *
     * @since 0.2
     */
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    /**
     * Text
     *
     * @since 0.2
     */
    @NonNull
    private String text;

    /**
     * How often this exact text has been used
     *
     * @since 0.2
     */
    @Builder.Default
    private Integer amountUsed = 0;

    private String toJson() {
        return "{\"id\":\"" + id + "\",\"text\":\"" + text + "\",\"amountUsed\":\"" + amountUsed + "\"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Text text = (Text) o;
        return id != null && Objects.equals(id, text.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
