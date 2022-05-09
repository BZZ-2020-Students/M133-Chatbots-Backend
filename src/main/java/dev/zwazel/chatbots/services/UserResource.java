package dev.zwazel.chatbots.services;

import dev.zwazel.chatbots.authentication.TokenHandler;
import dev.zwazel.chatbots.classes.enums.DurationsInMilliseconds;
import dev.zwazel.chatbots.classes.model.User;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
public class UserResource {
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUser(@PathParam("id") String id) {
        User user = new User(id, "Zwazel");
        System.out.println("user = " + user);
        return Response
                .status(200)
                .entity(user)
                .build();
    }

    @GET
    @Path("/{id}/{name}")
    @Produces("text/plain")
    public String getUser(@PathParam("id") String id, @PathParam("name") String name) {
        User user = new User(id, name);
        String jwt = TokenHandler.createJWT(id, "idk what subject to use", name, DurationsInMilliseconds.DAY.getDuration());
        String usernameFromJWT = TokenHandler.getUsername(jwt);
        System.out.println("user = " + user);
        System.out.println("jwt = " + jwt);
        System.out.println("usernameFromJWT = " + usernameFromJWT);
        return usernameFromJWT;
    }
}
