package dev.zwazel.chatbots.classes.model;

import dev.zwazel.chatbots.classes.enums.UserRole;
import lombok.*;

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
public class User {
    /**
     * User id
     *
     * @since 0.1
     */
    private String id;

    /**
     * User name
     *
     * @since 0.1
     */
    @NonNull
    private String name;

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
                "\"username\":\"" + name + "\"" +
                "}";
    }
}
