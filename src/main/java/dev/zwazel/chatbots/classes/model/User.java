package dev.zwazel.chatbots.classes.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.config.Constants;
import dev.zwazel.chatbots.util.SHA256;
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
@JsonFilter("UserFilter")
@JsonIgnoreProperties({"formPassword"})
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
    @Size(min = Constants.MIN_NAME_LENGTH, max = Constants.MAX_NAME_LENGTH)
    @FormParam("username")
    private String username;

    /**
     * User password for the forms, not stored in the database, not hashed.
     *
     * @since 1.4
     */
    @Transient
    @NonNull
    @Password
    @FormParam("password")
    private String formPassword;

    /**
     * User password for the database, hashed.
     *
     * @since 0.1
     */
    @Column(nullable = false, length = Constants.MAX_HASHED_PASSWORD_LENGTH)
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

    /**
     * Custom setter for the password field to hash the password before storing it in the database (if it is not null)
     *
     * @author Zwazel
     * @since 1.4
     */
    public void setPassword() {
        if (formPassword != null && !formPassword.isEmpty()) {
            this.password = SHA256.getHexStringInstant(this.getFormPassword());
        } else if (this.password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
    }
}
