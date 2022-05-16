import dev.zwazel.chatbots.classes.model.User;
import dev.zwazel.chatbots.services.UserResource;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserResourceTest {
    @Test
    public void testGetUser() throws Exception {
        UserResource userResource = new UserResource();
        String username = userResource.getUser("test", "test");
        assertNotNull(username);
        assertEquals("test", username);
    }

    @Test
    public void testGetUserOnlyID() throws Exception {
        UserResource userResource = new UserResource();
        Response response = userResource.getUser("test");
        String user = response.readEntity(String.class);

        User testUser = User.builder().name("Zwazel").id("test").build();

        assertNotNull(user);
        assertEquals(testUser.toJson(), user);
    }
}
