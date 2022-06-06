package dev.zwazel.chatbots.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.zwazel.chatbots.classes.dao.TextDao;
import dev.zwazel.chatbots.classes.model.Text;
import dev.zwazel.chatbots.config.Constants;
import dev.zwazel.chatbots.util.json.ToJson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.persistence.EntityExistsException;

/**
 * Resource class for Text objects.
 *
 * @author Zwazel
 * @since 0.3
 */
@Path("/text")
public class TextResource {
    /**
     * Creates a new Text object and returns it.
     *
     * @param text The text of the Text object.
     * @return The created text object if successful.
     * @author Zwazel
     * @since 1.2.0
     */
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createText(@FormParam("text") String text) {
        TextDao textDao = new TextDao();

        if (text.length() > Constants.MAX_TEXT_LENGTH) {
            text = text.substring(0, Constants.MAX_TEXT_LENGTH);
        }

        Text newText = new Text(text);
        try {
            textDao.save(newText);
        } catch (EntityExistsException e) {
            e.printStackTrace();
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity("{\"error\":\"Text already exists\"}")
                    .build();
        }

        try {
            return Response
                    .status(201)
                    .entity(ToJson.toJson(newText))
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
     * @param text the Text of the Text
     * @return 200 if successful
     * @author Zwazel
     * @since 1.1.0
     */
    @DELETE
    @Path("/delete/content/{content}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteTextByItsText(@PathParam("content") String text) {
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
        System.out.println("hello");
        TextDao textDao = new TextDao();
        Iterable<Text> texts = textDao.findAllByChatbotId(id);

        if (texts == null) {
            return Response.
                    status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Could not find Chatbot.\"}")
                    .build();
        }

        try {
            return Response.status(200).entity(ToJson.toJson(texts)).build();
        } catch (JsonProcessingException e) {
            return Response.status(500).build();
        }
    }
}
