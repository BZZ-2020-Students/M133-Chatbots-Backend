package dev.zwazel.chatbots.classes.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.config.Constants;
import dev.zwazel.chatbots.util.annotation.Password;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;
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
    @Column(name = "id", nullable = false, length = Constants.UUID_LENGTH)
    @GeneratedValue(generator = "uuid")
    @Builder.Default
    @dev.zwazel.chatbots.util.annotation.UUID
    private String id = UUID.randomUUID().toString();

    /**
     * User name
     *
     * @since 0.1
     */
    @NonNull
    @Column(nullable = false, length = Constants.MAX_NAME_LENGTH)
    @Size(max = Constants.MAX_NAME_LENGTH)
    @FormParam("username")
    private String username;

    /**
     * User password
     *
     * @since 0.2
     */
    @NonNull
    @Column(nullable = false, length = Constants.MAX_PASSWORD_LENGTH)
    @Size(min = Constants.MIN_PASSWORD_LENGTH, max = Constants.MAX_PASSWORD_LENGTH)
    @Password
    @FormParam("password")
    private String password;

    /**
     * The role of the user
     *
     * @since 0.2
     */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @NonNull
    @FormParam("role")
    private UserRole userRole = UserRole.USER;

    /**
     * The users chatbots
     *
     * @since 1.1.0
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @Builder.Default
    @ToString.Exclude
    private Set<Chatbot> chatbots = new LinkedHashSet<>();

    /**
     * All the ratings of the user
     *
     * @since 1.1.0
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @Builder.Default
    @ToString.Exclude
    private Set<Rating> ratings = new LinkedHashSet<>();
}
