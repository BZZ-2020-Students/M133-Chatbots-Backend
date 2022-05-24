package dev.zwazel.chatbots.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import dev.zwazel.chatbots.classes.dao.ChatbotDao;
import dev.zwazel.chatbots.classes.dao.RatingDao;
import dev.zwazel.chatbots.classes.model.Rating;
import dev.zwazel.chatbots.util.ToJson;
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
     * Deletes a chatbot by its id.
     * todo: Implement authorization
     *
     * @param id the id of the chatbot
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
            return Response.status(200).entity(ToJson.toJson(rating, getFilterProviderChatbot())).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
            return Response.status(200).entity(ToJson.toJson(ratingDao.findAll(), getFilterProviderChatbot())).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
            return Response.status(200).entity(ToJson.toJson(ratingDao.findByChatbotId(id), getFilterProviderChatbot())).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
            return Response.status(200).entity(ToJson.toJson(ratingDao.findByUserId(id), getFilterProviderChatbot())).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Utility method to get a filter provider for the chatbot. So that we don't get all the texts from the chatbot, and only its name and id.
     *
     * @return filter provider
     * @author Zwazel
     * @since 0.3
     */
    private FilterProvider getFilterProviderChatbot() {
        return new SimpleFilterProvider().addFilter("ChatbotFilter", SimpleBeanPropertyFilter.filterOutAllExcept("chatbotName", "id"));
    }
}
