package dev.zwazel.chatbots.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import dev.zwazel.chatbots.classes.dao.UserDao;
import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.User;
import dev.zwazel.chatbots.util.json.ToJson;
import jakarta.validation.Valid;
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
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update/{id}")
    public Response updateUser(@PathParam("id") String id, @Valid @BeanParam User user) {
        UserDao userDao = new UserDao();
        User userToUpdate = userDao.findById(id);
        if (userToUpdate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        boolean changed = false;
        if (user.getUsername() != null && !user.getUsername().equals(userToUpdate.getUsername())) {
            userToUpdate.setUsername(user.getUsername());
            changed = true;
        }

        if (user.getPassword() != null && !user.getPassword().equals(userToUpdate.getPassword())) {
            userToUpdate.setPassword(user.getPassword());
            changed = true;
        }

        if (user.getUserRole() != null && !user.getUserRole().equals(userToUpdate.getUserRole())) {
            userToUpdate.setUserRole(user.getUserRole());
            changed = true;
        }

        if (changed) {
            userDao.update(userToUpdate);
        }

        try {
            return Response
                    .status(201)
                    .entity(ToJson.toJson(userToUpdate, getFilterProvider(false)))
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
     * Creates a new User object and returns it.
     *
     * @param username The username of the User object.
     * @param password The password of the User object.
     * @param role     The role of the User object.
     * @return The created user object if successful.
     * @author Zwazel
     * @since 1.2.0
     */
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(
            @FormParam("username") String username,
            @FormParam("password") String password,
            @DefaultValue("") @FormParam("role") String role
    ) {
        UserRole userRole = (role.isEmpty() || role.isBlank()) ? null : UserRole.valueOf(role);

        UserDao userDao = new UserDao();
        User newUser = (userRole == null) ?
                User.builder()
                        .username(username)
                        .password(password)
                        .build() :
                User.builder()
                        .username(username)
                        .password(password)
                        .userRole(userRole)
                        .build();
        try {
            userDao.save(newUser);
        } catch (EntityExistsException | IllegalArgumentException e) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity("{\"error\":\"User already exists\"}")
                    .build();
        }

        try {
            return Response
                    .status(201)
                    .entity(ToJson.toJson(newUser, getFilterProvider(true)))
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
                    .entity(ToJson.toJson(user, getFilterProvider(true)))
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
                    .entity(ToJson.toJson(users, getFilterProvider(true)))
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
                    .entity(ToJson.toJson(users, getFilterProvider(true)))
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
    private FilterProvider getFilterProvider(boolean filterOutPassword) {
        if (filterOutPassword) {
            return new SimpleFilterProvider()
                    .addFilter("ChatbotFilter", SimpleBeanPropertyFilter.filterOutAllExcept("chatbotName", "id"))
                    .addFilter("RatingFilter", SimpleBeanPropertyFilter.filterOutAllExcept("rating", "id"))
                    .addFilter("UserFilter", SimpleBeanPropertyFilter.serializeAllExcept("password"));
        }

        return new SimpleFilterProvider()
                .addFilter("ChatbotFilter", SimpleBeanPropertyFilter.filterOutAllExcept("chatbotName", "id"))
                .addFilter("RatingFilter", SimpleBeanPropertyFilter.filterOutAllExcept("rating", "id"))
                .addFilter("UserFilter", SimpleBeanPropertyFilter.serializeAll());
    }
}
