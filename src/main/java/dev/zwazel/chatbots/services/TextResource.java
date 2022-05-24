package dev.zwazel.chatbots.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.zwazel.chatbots.classes.dao.TextDao;
import dev.zwazel.chatbots.classes.model.Text;
import dev.zwazel.chatbots.util.ToJson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Resource class for Text objects.
 *
 * @author Zwazel
 * @since 0.3
 */
@Path("/text")
public class TextResource {
    /**
     * Deletes a text by its id.
     * todo: Implement authorization
     *
     * @param id the id of the text
     * @return 200 if successful
     * @author Zwazel
     * @since 1.1.0
     */
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteText(@PathParam("id") String id) {
        new TextDao().delete(id);

        return Response
                .status(200)
                .build();
    }

    /**
     * Deletes a text by its text.
     * todo: Implement authorization
     *
     * @param text the text of the text
     * @return 200 if successful
     * @author Zwazel
     * @since 1.1.0
     */
    @DELETE
    @Path("/delete/text/{text}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteTextByItsText(@PathParam("text") String text) {
        new TextDao().deleteByText(text);

        return Response
                .status(200)
                .build();
    }

    /**
     * Returns a Text object by its id.
     *
     * @param id The id of the Text object.
     * @return A Text object.
     */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getText(@PathParam("id") String id) {
        TextDao textDao = new TextDao();
        Text text = textDao.findById(id);

        if (text == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            return Response.status(200).entity(ToJson.toJson(text)).build();
        } catch (JsonProcessingException e) {
            return Response.status(500).build();
        }
    }

    /**
     * Returns all Text objects.
     *
     * @return A list of Text objects.
     * @author Zwazel
     * @since 0.3
     */
    @GET
    @Path("/list")
    @Produces("application/json")
    public Response getTexts() {
        TextDao textDao = new TextDao();
        try {
            return Response.status(200).entity(ToJson.toJson(textDao.findAll())).build();
        } catch (JsonProcessingException e) {
            return Response.status(500).build();
        }
    }

    /**
     * Returns all Text objects that are associated with a specific chatbot.
     *
     * @param id The id of the chatbot.
     * @return A list of Text objects.
     * @author Zwazel
     * @since 0.3
     */
    @GET
    @Path("/chatbot/{id}")
    @Produces("application/json")
    public Response getTextByChatbot(@PathParam("id") String id) {
        TextDao textDao = new TextDao();
        Iterable<Text> texts = textDao.findAllByChatbotId(id);

        if (texts == null || !texts.iterator().hasNext()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            return Response.status(200).entity(ToJson.toJson(texts)).build();
        } catch (JsonProcessingException e) {
            return Response.status(500).build();
        }
    }
}
