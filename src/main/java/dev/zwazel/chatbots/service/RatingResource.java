package dev.zwazel.chatbots.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import dev.zwazel.chatbots.classes.dao.ChatbotDao;
import dev.zwazel.chatbots.classes.dao.RatingDao;
import dev.zwazel.chatbots.classes.dao.UserDao;
import dev.zwazel.chatbots.classes.model.Chatbot;
import dev.zwazel.chatbots.classes.model.Rating;
import dev.zwazel.chatbots.classes.model.User;
import dev.zwazel.chatbots.util.json.ToJson;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Resource class for the Rating entity.
 *
 * @author Zwazel
 * @since 0.3
 */
@Path("/rating")
public class RatingResource {
    /**
     * Updates an already existing rating in the database.
     *
     * @param id     The id of the rating to update.
     * @param rating The rating to update.
     * @return The updated rating.
     * @author Zwazel
     * @since 1.3.0
     */
    @RolesAllowed({"admin", "user"})
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update/{id}")
    public Response updateUser(@PathParam("id") String id, @Valid @BeanParam Rating rating) {
        RatingDao ratingDao = new RatingDao();
        Rating ratingToUpdate = ratingDao.findById(id);
        if (ratingToUpdate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        boolean changed = false;
        if (rating.getRating() != null && !rating.getRating().equals(ratingToUpdate.getRating())) {
            ratingToUpdate.setRating(rating.getRating());
            changed = true;
        }

        if (rating.isFavourite() != ratingToUpdate.isFavourite()) {
            ratingToUpdate.setFavourite(rating.isFavourite());
            changed = true;
        }

        if (changed) {
            ratingDao.update(ratingToUpdate);
        }

        try {
            return Response
                    .status(201)
                    // the password isn't being filtered out only for testing purposes
                    .entity(ToJson.toJson(ratingToUpdate, getFilterProvider()))
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
     * Creates a new rating for a chatbot. A user can only rate a chatbot once.
     *
     * @param rating The rating to create.
     * @return The created rating.
     * @author Zwazel
     * @since 1.3.0
     */
    @RolesAllowed({"admin", "user"})
    @POST
    @Path("/create")
    @Produces("application/json")
    public Response createRating(
            @Valid @BeanParam Rating rating
    ) {
        boolean userIdNotSpecified = rating.getUserId() == null ||
                rating.getUserId().isBlank() ||
                rating.getUserId().isEmpty();
        boolean usernameNotSpecified = rating.getUsername() == null ||
                rating.getUsername().isBlank() ||
                rating.getUsername().isEmpty();
        boolean chatbotIdNotSpecified = rating.getChatbotId() == null ||
                rating.getChatbotId().isBlank() ||
                rating.getChatbotId().isEmpty();
        boolean chatbotNameNotSpecified = rating.getChatbotName() == null ||
                rating.getChatbotName().isBlank() ||
                rating.getChatbotName().isEmpty();

        if (userIdNotSpecified && usernameNotSpecified) {
            return Response
                    .status(400)
                    .entity("{\"error\": \"userId or username must be provided\"}")
                    .build();
        }

        if (chatbotIdNotSpecified && chatbotNameNotSpecified) {
            return Response
                    .status(400)
                    .entity("{\"error\": \"chatbotId or chatbotName must be provided\"}")
                    .build();
        }

        UserDao userDao = new UserDao();
        User user = (userIdNotSpecified) ? userDao.findByUsername(rating.getUsername()) :
                userDao.findById(rating.getUserId());
        if (user == null) {
            return Response
                    .status(404)
                    .entity("{\"error\": \"user not found\"}")
                    .build();
        }

        ChatbotDao chatbotDao = new ChatbotDao();
        Chatbot chatbot = (chatbotIdNotSpecified) ? chatbotDao.findByName(rating.getChatbotName()) :
                chatbotDao.findById(rating.getChatbotId());
        if (chatbot == null) {
            return Response
                    .status(404)
                    .entity("{\"error\": \"chatbot not found\"}")
                    .build();
        }

        // Needed for the save method to work
        rating.setUser(user);
        rating.setChatbot(chatbot);

        RatingDao ratingDao = new RatingDao();
        try {
            ratingDao.save(rating);
        } catch (IllegalArgumentException e) {
            return Response
                    .status(409)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }

        try {
            return Response
                    .status(201)
                    .entity(ToJson.toJson(rating, getFilterProvider()))
                    .build();
        } catch (JsonProcessingException e) {
            return Response
                    .status(500)
                    .entity("{\"error\": \"unable to serialize object\"}")
                    .build();
        }
    }

    /**
     * Deletes a rating by its id.
     *
     * @param id the id of the rating
     * @return 200 if successful
     * @author Zwazel
     * @since 1.1.0
     */
    @RolesAllowed({"admin", "user"})
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteRating(@PathParam("id") String id) {
        new RatingDao().delete(id);

        return Response
                .status(200)
                .build();
    }

    /**
     * Gets a single rating by its id.
     *
     * @param id id of the rating
     * @return rating
     * @author Zwazel
     * @since 0.3
     */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getRating(@PathParam("id") String id) {
        RatingDao ratingDao = new RatingDao();

        Rating rating = ratingDao.findById(id);

        if (rating == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            return Response.status(200).entity(ToJson.toJson(rating, getFilterProvider())).build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    /**
     * gets all ratings
     *
     * @return all ratings
     * @author Zwazel
     * @since 0.3
     */
    @GET
    @Path("/list")
    @Produces("application/json")
    public Response getRatings() {
        RatingDao ratingDao = new RatingDao();

        try {
            return Response.status(200).entity(ToJson.toJson(ratingDao.findAll(), getFilterProvider())).build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    /**
     * Gets all Ratings for a specific chatbot.
     *
     * @param id id of the chatbot
     * @return all ratings for the chatbot
     * @author Zwazel
     * @since 0.3
     */
    @GET
    @Path("/chatbot/{id}")
    @Produces("application/json")
    public Response getRatingsByChatbot(@PathParam("id") String id) {
        RatingDao ratingDao = new RatingDao();

        try {
            return Response.status(200).entity(ToJson.toJson(ratingDao.findByChatbotId(id), getFilterProvider())).build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    /**
     * Gets all Ratings made by a specific user.
     *
     * @param id id of the  user
     * @return all ratings for the user
     * @author Zwazel
     * @since 0.3
     */
    @GET
    @Path("/user/{id}")
    @Produces("application/json")
    public Response getRatingsByUser(@PathParam("id") String id) {
        RatingDao ratingDao = new RatingDao();

        try {
            return Response.status(200).entity(ToJson.toJson(ratingDao.findByUserId(id), getFilterProvider())).build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    /**
     * Utility method to get a filter provider for the chatbot. So that we don't get all the texts from the chatbot, and only its name and id.
     *
     * @return filter provider
     * @author Zwazel
     * @since 0.3
     */
    private FilterProvider getFilterProvider() {
        return new SimpleFilterProvider()
                .addFilter("ChatbotFilter", SimpleBeanPropertyFilter.filterOutAllExcept("chatbotName", "id"))
                .addFilter("UserFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id", "username"))
                .addFilter("RatingFilter", SimpleBeanPropertyFilter.serializeAll());
    }
}
