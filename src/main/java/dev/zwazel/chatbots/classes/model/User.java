package dev.zwazel.chatbots.classes.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.configs.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;
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
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@ToString
@JsonIgnoreProperties(value = {"password"})
@JsonFilter("UserFilter")
public class User {
    /**
     * User id
     *
     * @since 0.1
     */
    @Id
    @Column(name = "id", nullable = false, length = Constants.MAX_UUID_LENGTH)
    @GeneratedValue(generator = "uuid")
    @Builder.Default
    @Size(max = Constants.MAX_UUID_LENGTH)
    private String id = UUID.randomUUID().toString();

    /**
     * User name
     *
     * @since 0.1
     */
    @NonNull
    @Column(nullable = false, length = Constants.MAX_NAME_LENGTH)
    @Size(max = Constants.MAX_NAME_LENGTH)
    private String username;

    /**
     * User password
     *
     * @since 0.2
     */
    @NonNull
    @Column(nullable = false, length = Constants.MAX_PASSWORD_LENGTH)
    @Size(max = Constants.MAX_PASSWORD_LENGTH)
    private String password;

    /**
     * The role of the user
     *
     * @since 0.2
     */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @NonNull
    private UserRole userRole = UserRole.USER;

    /**
     * The users chatbots
     *
     * @since 1.1.0
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.REMOVE})
    @Builder.Default
    @ToString.Exclude
    private Set<Chatbot> chatbots = new LinkedHashSet<>();

    /**
     * All the ratings of the user
     *
     * @since 1.1.0
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private Set<Rating> ratings = new LinkedHashSet<>();
}
