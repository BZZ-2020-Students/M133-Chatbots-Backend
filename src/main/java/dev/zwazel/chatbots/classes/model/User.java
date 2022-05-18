package dev.zwazel.chatbots.classes.model;

import dev.zwazel.chatbots.classes.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * User class
 *
 * @author Zwazel
 * @since 0.1
 */
@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class User {
    /**
     * User id
     *
     * @since 0.1
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID id;

    /**
     * User name
     *
     * @since 0.1
     */
    @NonNull
    private String username;

    /**
     * User password
     *
     * @since 0.2
     */
    @NonNull
    private String password;

    /**
     * The role of the user
     *
     * @since 0.2
     */
    @NonNull
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    /**
     * User toJson method
     *
     * @return A JSON string of the user
     * @author Zwazel
     * @since 0.1
     */
    public String toJson() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"username\":\"" + username + "\"" +
                "}";
    }
}
