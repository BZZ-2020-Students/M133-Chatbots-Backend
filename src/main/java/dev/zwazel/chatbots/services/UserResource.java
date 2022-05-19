package dev.zwazel.chatbots.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.zwazel.chatbots.classes.dao.UserDao;
import dev.zwazel.chatbots.classes.model.User;
import dev.zwazel.chatbots.util.ToJson;
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
        User user = new UserDao().findById(id);

        return getResponseForUser(user);
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

        return getResponseForUser(user);
    }

    /**
     * Helper method for returning a response for a user
     *
     * @param user the user to return
     * @return a response for the user
     * @author Zwazel
     * @since 0.3
     */
    private Response getResponseForUser(User user) {
        if (user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        try {
            return Response
                    .status(200)
                    .entity(ToJson.toJson(user))
                    .build();
        } catch (JsonProcessingException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    /**
     * this method returns a list of all users
     *
     * @return a list of all users
     * @author Zwazel
     * @since 0.3
     */
    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsers() {
        Iterable<User> users = new UserDao().findAll();

        try {
            return Response
                    .status(200)
                    .entity(ToJson.toJson(users))
                    .build();
        } catch (JsonProcessingException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    /**
     * Returns all users with the given role
     *
     * @param role the role to search for
     * @return a list of all users with the given role
     * @author Zwazel
     * @since 0.3
     */
    @GET
    @Path("/role/{role}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUsersByRole(@PathParam("role") String role) {
        Iterable<User> users = new UserDao().filterByRole(role);

        try {
            return Response
                    .status(200)
                    .entity(ToJson.toJson(users))
                    .build();
        } catch (JsonProcessingException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
