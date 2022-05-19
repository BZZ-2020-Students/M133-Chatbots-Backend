package dev.zwazel.chatbots.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.zwazel.chatbots.classes.dao.RatingDao;
import dev.zwazel.chatbots.classes.model.Rating;
import dev.zwazel.chatbots.util.ToJson;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/rating")
public class RatingResource {
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getRating(@jakarta.ws.rs.PathParam("id") String id) {
        RatingDao ratingDao = new RatingDao();

        Rating rating = ratingDao.find(id);

        if (rating == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            return Response.status(200).entity(ToJson.toJson(rating)).build();
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
            return Response.status(200).entity(ToJson.toJson(ratingDao.findAll())).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
