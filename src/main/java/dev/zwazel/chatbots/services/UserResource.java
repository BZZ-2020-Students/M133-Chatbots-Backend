package dev.zwazel.chatbots.services;

import dev.zwazel.chatbots.classes.dao.UserDao;
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
        User user = new UserDao().find(id);

        if (user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        return Response
                .status(200)
                .entity(user)
                .build();
    }

    /**
     * this method searches for a user with the given username
     *
     * @param username the username of the user
     * @return the user with the given username, or 404 if no user was found
     * @author Zwazel
     * @since 0.3
     */
    @GET
    @Path("/name/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserByUsername(@PathParam("username") String username) {
        User user = new UserDao().findByUsername(username);

        if (user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        return Response
                .status(200)
                .entity(user)
                .build();
    }

    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsers() {
        return Response
                .status(200)
                .entity(new UserDao().findAll())
                .build();
    }
}
