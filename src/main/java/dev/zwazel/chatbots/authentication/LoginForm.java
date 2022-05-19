package dev.zwazel.chatbots.authentication;

import lombok.*;

/**
 * Utility class for Login API.
 *
 * @author Zwazel
 * @since 0.1
 */
@Setter
@Getter
@Builder
@ToString
//@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class LoginForm {
    /**
     * The username.
     *
     * @since 0.1
     */
    @NonNull
    private String username;

    /**
     * The password.
     *
     * @since 0.1
     */
    @NonNull
    private String password;
}
