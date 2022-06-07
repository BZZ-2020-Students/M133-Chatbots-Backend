package dev.zwazel.chatbots.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import dev.zwazel.chatbots.classes.dao.ChatbotDao;
import dev.zwazel.chatbots.classes.dao.RatingDao;
import dev.zwazel.chatbots.classes.dao.UserDao;
import dev.zwazel.chatbots.classes.enums.RatingEnum;
import dev.zwazel.chatbots.classes.model.Chatbot;
import dev.zwazel.chatbots.classes.model.Rating;
import dev.zwazel.chatbots.classes.model.User;
import dev.zwazel.chatbots.util.json.ToJson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;

/**
 * Resource class for the Rating entity.
 *
 * @author Zwazel
 * @since 0.3
 */
@Path("/rating")
public class RatingResource {
    @POST
    @Path("/create")
    @Produces("application/json")
    public Response createRating(
            @DefaultValue("") @FormParam("userId") String userId,
            @DefaultValue("") @FormParam("username") String username,
            @DefaultValue("") @FormParam("chatbotId") String chatbotId,
            @DefaultValue("") @FormParam("chatbotName") String chatbotName,
            @FormParam("rating") String rating,
            @FormParam("favourite") boolean favourite
    ) {
        if ((userId.isBlank() || userId.isEmpty()) && (username.isBlank() || username.isEmpty())) {
            return Response
                    .status(400)
                    .entity("{\"error\": \"userId or username must be provided\"}")
                    .build();
        }

        if ((chatbotId.isBlank() || chatbotId.isEmpty()) && (chatbotName.isBlank() || chatbotName.isEmpty())) {
            return Response
                    .status(400)
                    .entity("{\"error\": \"chatbotId or chatbotName must be provided\"}")
                    .build();
        }

        UserDao userDao = new UserDao();
        User user = (userId.isBlank() || userId.isEmpty()) ? userDao.findByUsername(username) : userDao.findById(userId);
        if (user == null) {
            return Response
                    .status(404)
                    .entity("{\"error\": \"user not found\"}")
                    .build();
        }

        ChatbotDao chatbotDao = new ChatbotDao();
        Chatbot chatbot = (chatbotId.isBlank() || chatbotId.isEmpty()) ? chatbotDao.findByName(chatbotName) : chatbotDao.findById(chatbotId);
        if (chatbot == null) {
            return Response
                    .status(404)
                    .entity("{\"error\": \"chatbot not found\"}")
                    .build();
        }

        RatingEnum ratingEnum;
        try {
            ratingEnum = RatingEnum.valueOf(rating);
        } catch (IllegalArgumentException e) {
            return Response
                    .status(400)
                    .entity("{\"error\": \"rating must be one of the following: " + Arrays.toString(RatingEnum.values()) + "\"}")
                    .build();
        }

        RatingDao ratingDao = new RatingDao();
        Rating ratingObj = Rating.builder()
                .rating(ratingEnum)
                .chatbot(chatbot)
                .user(user)
                .favourite(favourite)
                .build();

        try {
            ratingDao.save(ratingObj);
        } catch (IllegalArgumentException e) {
            return Response
                    .status(409)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }

        try {
            return Response
                    .status(201)
                    .entity(ToJson.toJson(ratingObj, getFilterProvider()))
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
     * todo: Implement authorization
     *
     * @param id the id of the rating
     * @return 200 if successful
     * @author Zwazel
     * @since 1.1.0
     */
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
