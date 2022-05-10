package dev.zwazel.chatbots;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public Response hello() {
        return Response
                .status(200)
                .entity("Hello, World!")
                .build();
    }
}