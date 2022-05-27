package dev.zwazel.chatbots.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import dev.zwazel.chatbots.classes.dao.ChatbotDao;
import dev.zwazel.chatbots.classes.dao.UserDao;
import dev.zwazel.chatbots.classes.model.Chatbot;
import dev.zwazel.chatbots.classes.model.User;
import dev.zwazel.chatbots.util.ToJson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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
     * Creates a new Chatbot
     *
     * @param name     the name of the chatbot
     * @param userId   the id of the user. If username is defined, this one can be left empty. If both are defined, the username will be used.
     * @param username the username of the user. If UserID is defined, this one can be left empty.
     * @return the newly created chatbot.
     * @author Zwazel
     * @since 1.2.0
     */
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createChatbot(
            @FormParam("name") String name,
            @DefaultValue("") @FormParam("username") String username,
            @DefaultValue("") @FormParam("userId") String userId
    ) {
        if ((userId.isBlank() || userId.isEmpty()) && (username.isBlank() || username.isEmpty())) {
            return Response
                    .status(400)
                    .entity("{\"error\": \"userId or username must be provided\"}")
                    .build();
        }

        UserDao userDao = new UserDao();
        User user = (userId.isBlank() || userId.isEmpty()) ? userDao.findByUsername(username) : userDao.findById(userId);

        if (user == null) {
            // return response with error code for user not found
            return Response
                    .status(404)
                    .entity("{\"error\": \"user not found\"}")
                    .build();
        }

        Chatbot chatbot = Chatbot.builder()
                .chatbotName(name)
                .user(user)
                .build();

        try {
            new ChatbotDao().save(chatbot);
        } catch (IllegalArgumentException e) {
            return Response
                    .status(400)
                    .entity("{\"error\": \"chatbot name already exists\"}")
                    .build();
        }

        try {
            return Response
                    .status(200)
                    .entity(ToJson.toJson(chatbot, getFilterProvider()))
                    .build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response
                    .status(500)
                    .entity("{\"error\": \"internal server while processing JSON response\"}")
                    .build();
        }
    }

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
    public Response deleteChatbot(@PathParam("id") String id) {
        new ChatbotDao().delete(id);

        return Response
                .status(200)
                .build();
    }

    /**
     * Deletes a chatbot by its name.
     * todo: Implement authorization
     *
     * @param name the name of the chatbot
     * @return 200 if successful
     * @author Zwazel
     * @since 1.1.0
     */
    @DELETE
    @Path("/delete/name/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteChatbotByName(@PathParam("name") String name) {
        new ChatbotDao().deleteByName(name);

        return Response
                .status(200)
                .build();
    }

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
    @Produces("application/json")
    public Response getChatbot(@PathParam("id") String id) {
        Chatbot chatbot = new ChatbotDao().findById(id);

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
                    .entity(ToJson.toJson(new ChatbotDao().findAll(), getFilterProvider()))
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
                    .entity(ToJson.toJson(chatbot, getFilterProvider()))
                    .build();
        } catch (JsonProcessingException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    /**
     * Utility method to get a filter provider. So that we don't get recursion and only return what we need.
     *
     * @return filter provider with configured filters.
     * @author Zwazel
     * @since 0.3
     */
    private FilterProvider getFilterProvider() {
        return new SimpleFilterProvider()
                .addFilter("ChatbotFilter", SimpleBeanPropertyFilter.serializeAll())
                .addFilter("RatingFilter", SimpleBeanPropertyFilter.filterOutAllExcept("rating", "id"))
                .addFilter("UserFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id", "username"))
                .addFilter("QuestionAnswerFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id", "questionAnswerQuestions", "questionAnswerAnswers"))
                .addFilter("QuestionAnswerQuestionFilter", SimpleBeanPropertyFilter.serializeAllExcept("questionAnswer"))
                .addFilter("QuestionAnswerAnswerFilter", SimpleBeanPropertyFilter.serializeAllExcept("questionAnswer"))
                .addFilter("ChatbotUnknownTextsFilter", SimpleBeanPropertyFilter.serializeAllExcept("chatbot"));
    }
}
