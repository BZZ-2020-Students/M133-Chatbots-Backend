package dev.zwazel.chatbots.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import dev.zwazel.chatbots.classes.dao.UserDao;
import dev.zwazel.chatbots.classes.model.User;
import dev.zwazel.chatbots.util.ToJson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.persistence.EntityExistsException;

/**
 * UserResource is a class that handles all requests to the /user endpoint.
 *
 * @author Zwazel
 * @since 0.1
 */
@Path("/user")
public class UserResource {
    /**
     * Creates a new Text object and returns it.
     *
     * @param username The username of the Text object.
     * @return The created username object if successful.
     * @author Zwazel
     * @since 1.2.0
     */
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createText(@FormParam("username") String username) {
        UserDao userDao = new UserDao();
        User newUser = User.builder()
                .username(username)
                .build();
        try {
            userDao.save(newUser);
        } catch (EntityExistsException e) {
            e.printStackTrace();
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity("{\"error\":\"User already exists\"}")
                    .build();
        }

        try {
            return Response
                    .status(200)
                    .entity(ToJson.toJson(newUser))
                    .build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response
                    .status(500)
                    .entity("{\"error\": \"Could not return JSON.\"}")
                    .build();
        }
    }

    /**
     * Deletes a User by its id.
     * todo: Implement authorization
     *
     * @param id the id of the User to delete
     * @return 200 if successful
     * @author Zwazel
     * @since 1.1.0
     */
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUser(@PathParam("id") String id) {
        new UserDao().delete(id);

        return Response
                .status(200)
                .build();
    }

    /**
     * Deletes a User by its name.
     * todo: Implement authorization
     *
     * @param username the username of the User to delete
     * @return 200 if successful
     * @author Zwazel
     * @since 1.1.0
     */
    @DELETE
    @Path("/delete/name/{username}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUserByUsername(@PathParam("username") String username) {
        new UserDao().deleteByUsername(username);

        return Response
                .status(200)
                .build();
    }

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
                    .entity(ToJson.toJson(user, getFilterProvider()))
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
                    .entity(ToJson.toJson(users, getFilterProvider()))
                    .build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
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
                    .entity(ToJson.toJson(users, getFilterProvider()))
                    .build();
        } catch (JsonProcessingException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    /**
     * This method returns a FilterProvider to filter out specific fields.
     *
     * @return a FilterProvider to filter out specific fields.
     * @author Zwazel
     * @since 1.1.0
     */
    private FilterProvider getFilterProvider() {
        return new SimpleFilterProvider()
                .addFilter("ChatbotFilter", SimpleBeanPropertyFilter.filterOutAllExcept("chatbotName", "id"))
                .addFilter("RatingFilter", SimpleBeanPropertyFilter.filterOutAllExcept("rating", "id"))
                .addFilter("UserFilter", SimpleBeanPropertyFilter.serializeAll());
    }
}
