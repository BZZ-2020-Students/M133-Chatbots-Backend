package dev.zwazel.chatbots.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.zwazel.chatbots.classes.dao.ChatbotDao;
import dev.zwazel.chatbots.classes.model.Chatbot;
import dev.zwazel.chatbots.interfaces.ToJson;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * Resource class for the chatbot.
 *
 * @author Zwazel
 * @since 0.2
 */
@Path("/chatbot")
public class ChatbotResource {
    /**
     * Get a chatbot by its id.
     *
     * @param id the id of the chatbot
     * @return the chatbot
     * @author Zwazel
     * @since 0.2
     */
    @GET
    @Path("/{id}")
    public Response getChatbot(@PathParam("id") String id) {
        Chatbot chatbot = new ChatbotDao().find(id);

        return getResponseForChatbot(chatbot);
    }

    /**
     * Get all chatbots.
     *
     * @return the chatbots
     * @author Zwazel
     * @since 0.3
     */
    @GET
    @Produces("application/json")
    @Path("/list")
    public Response getChatbots() {
        try {
            return Response
                    .status(200)
                    .entity(ToJson.toJson(new ChatbotDao().findAll()))
                    .build();
        } catch (JsonProcessingException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    /**
     * Get the Chatbot by its name.
     *
     * @param name the name of the chatbot
     * @return the chatbot or 404 if not found
     */
    @GET
    @Path("/name/{name}")
    @Produces("application/json")
    public Response getChatbotByName(@PathParam("name") String name) {
        Chatbot chatbot = new ChatbotDao().findByName(name);

        return getResponseForChatbot(chatbot);
    }

    /**
     * Helper method to get the response for a chatbot.
     *
     * @param chatbot the chatbot to get the response for ir 500 if something went wrong while processing the chatbot to json or 404 if the chatbot could not be found
     * @return the response
     */
    private Response getResponseForChatbot(Chatbot chatbot) {
        if (chatbot == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            return Response
                    .status(200)
                    .entity(ToJson.toJson(chatbot))
                    .build();
        } catch (JsonProcessingException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
