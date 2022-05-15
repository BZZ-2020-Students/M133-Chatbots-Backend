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

/**
 * UserResource is a class that handles all requests to the /user endpoint.
 *
 * @author Zwazel
 * @since 0.1
 */
@Path("/user")
public class UserResource {
    /**
     * this method returns the user with the given ID
     * TODO: 15.05.2022 - implement DB + test if user.toJson() works
     *
     * @param id the ID of the user
     * @return the user with the given ID
     * @author Zwazel
     * @since 0.1
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUser(@PathParam("id") String id) {
        User user = new User(id, "Zwazel");
        return Response
                .status(200)
                .entity(user.toJson())
                .build();
    }

    /**
     * this method is purely for testing purposes. it creates a user with the given id and name and creates a token for it. it then returns the token.
     *
     * @param id   the id of the user
     * @param name the name of the user
     * @return the token
     * @author Zwazel
     * @since 0.1
     */
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
