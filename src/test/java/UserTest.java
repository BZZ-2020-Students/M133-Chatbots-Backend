import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.User;
import org.junit.jupiter.api.Test;

import java.util.UUID;

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
     * Test UUID
     *
     * @since 0.3
     */
    private final String testUuid = "2684e7b0-ab8a-4ac2-99d2-bde7b4529ad1";

    /**
     * Tests if we get a NullPointerException when trying to build a User without a Name
     *
     * @author Zwazel
     * @since 0.2
     */
    @Test
    public void testBuilderMissingName() {
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
                        .username("name")
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
                        .username("name")
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
                .id(UUID.fromString(testUuid).toString())
                .password("password")
                .username("name")
                .userRole(UserRole.USER)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String expected = "{\"id\":\"" + testUuid + "\",\"username\":\"name\",\"userRole\":\"USER\"}";

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
                .id(UUID.fromString(testUuid).toString())
                .password("password")
                .username("name")
                .userRole(UserRole.USER)
                .build();

        assertEquals(user.getId(), UUID.fromString(testUuid).toString());
        assertEquals("name", user.getUsername());
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

        assertEquals("name", user.getUsername());
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

        assertNotNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getUserRole());
    }
}
