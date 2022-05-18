import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.User;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing the User class.
 *
 * @author Zwazel
 * @see User
 * @since 0.2
 */
public class UserTest {
    /**
     * Tests if we get a NullPointerException when trying to build a User without a Name
     *
     * @author Zwazel
     * @since 0.2
     */
    @Test
    public void testBuilderMissingName() {
        // assert null pointer exception
        assertThrows(NullPointerException.class,
                () -> User.builder()
                        .password("password")
                        .userRole(UserRole.USER)
                        .build()
        );
    }

    /**
     * Tests if we get a NullPointerException when trying to build a User without a Password
     *
     * @author Zwazel
     * @since 0.2
     */
    @Test
    public void testBuilderMissingPassword() {
        // assert null pointer exception
        assertThrows(NullPointerException.class,
                () -> User.builder()
                        .name("name")
                        .userRole(UserRole.USER)
                        .build()
        );
    }

    /**
     * Tests if we get a NullPointerException when trying to build a User without a UserRole
     *
     * @author Zwazel
     * @see UserRole
     * @since 0.2
     */
    @Test
    public void testBuilderMissingRole() {
        // assert null pointer exception
        assertThrows(NullPointerException.class,
                () -> User.builder()
                        .password("password")
                        .name("name")
                        .build()
        );
    }

    /**
     * Tests if the toJson method returns the correct JSON String
     *
     * @author Zwazel
     * @since 0.2
     */
    @Test
    public void testToJson() {
        User user = User.builder()
                .password("password")
                .name("name")
                .userRole(UserRole.USER)
                .build();
        String json = user.toJson();

        String expected = "{\"id\":\"123\",\"username\":\"name\"}";

        assertEquals(expected, json);
    }

    /**
     * Tests if we can successfully build a User with the builder pattern
     *
     * @author Zwazel
     * @since 0.2
     */
    @Test
    public void testBuildingValidUser() {
        User user = User.builder()
                .password("password")
                .name("name")
                .userRole(UserRole.USER)
                .build();

        assertEquals("name", user.getName());
        assertEquals("password", user.getPassword());
        assertEquals(UserRole.USER, user.getUserRole());
    }

    /**
     * Tests if we can successfully construct a user with the required args constructor
     *
     * @author Zwazel
     * @since 0.2
     */
    @Test
    public void testRequiredArgsConstructor() {
        User user = new User("name", "password", UserRole.USER);

        assertEquals("name", user.getName());
        assertEquals("password", user.getPassword());
        assertEquals(UserRole.USER, user.getUserRole());
    }

    /**
     * Tests if we can successfully construct a user with the no args constructor
     *
     * @author Zwazel
     * @since 0.2
     */
    @Test
    public void testNoArgsConstructor() {
        User user = new User();

        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getPassword());
        assertNull(user.getUserRole());
    }
}
