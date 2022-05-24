package dev.zwazel.chatbots.classes.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import dev.zwazel.chatbots.classes.enums.UserRole;
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
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @Builder.Default
    @Size(max = 36)
    private String id = UUID.randomUUID().toString();

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
     * The users chatbots
     *
     * @since 1.1.0
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private Set<Chatbot> chatbots = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private Set<Rating> ratings = new LinkedHashSet<>();
}
