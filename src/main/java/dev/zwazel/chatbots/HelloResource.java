package dev.zwazel.chatbots;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * A simple resource class that returns a simple text/plain response.
 *
 * @author Zwazel
 * @since 0.1
 */
@ApplicationScoped
@PermitAll
@Path("/hello-world")
public class HelloResource {
    /**
     * A simple method that returns a simple text/plain response.
     *
     * @return A text/plain response containing the string "Hello, World!"
     * @author Zwazel
     * @since 0.1
     */
    @GET
    @Produces("text/plain")
    public Response hello() {
        return Response
                .status(200)
                .entity("Hello, World!")
                .build();
    }
}