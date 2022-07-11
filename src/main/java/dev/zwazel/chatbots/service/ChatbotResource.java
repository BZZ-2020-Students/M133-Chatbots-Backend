package dev.zwazel.chatbots.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import dev.zwazel.chatbots.authentication.TokenHandler;
import dev.zwazel.chatbots.classes.dao.ChatbotDao;
import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.Chatbot;
import dev.zwazel.chatbots.classes.model.User;
import dev.zwazel.chatbots.exception.NotLoggedInException;
import dev.zwazel.chatbots.util.json.ToJson;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
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
     * Updates a chatbot in the database.
     *
     * @param chatbot The chatbot to update.
     * @param id      The id of the chatbot to update.
     * @return The updated chatbot.
     * @author Zwazel
     * @since 1.3.0
     */
    @RolesAllowed({"admin", "user"})
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update/{id}")
    public Response updateText(@PathParam("id") String id, @Valid @BeanParam Chatbot chatbot, ContainerRequestContext requestContext) {
        User user;
        try {
            user = TokenHandler.getUserFromCookie(requestContext);
        } catch (NotLoggedInException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(e.getMessage())
                    .build();
        }

        ChatbotDao chatbotDao = new ChatbotDao();
        Chatbot chatbotFromDb = chatbotDao.findById(id);
        if (chatbotFromDb == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // admins can change everything, users can only change their own chatbots
        if (UserRole.ADMIN == user.getUserRole() || chatbotFromDb.getUser().getId().equals(user.getId())) {
            if (!chatbot.getChatbotName().equals(chatbotFromDb.getChatbotName())) {
                chatbotFromDb.setChatbotName(chatbot.getChatbotName());
                chatbotDao.update(chatbotFromDb);
            }

            try {
                return Response
                        .status(201)
                        .entity(ToJson.toJson(chatbotFromDb, getFilterProvider()))
                        .build();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return Response
                        .status(500)
                        .entity("{\"error\": \"Could not return JSON.\"}")
                        .build();
            }
        } else {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"You are not allowed to update this chatbot.\"}")
                    .build();
        }
    }

    /**
     * Creates a new Chatbot
     *
     * @param chatbot the chatbot to create
     * @return the newly created chatbot.
     * @author Zwazel
     * @since 1.2.0
     */
    @RolesAllowed({"admin", "user"})
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createChatbot(
            @Valid @BeanParam Chatbot chatbot,
            ContainerRequestContext requestContext
    ) {
        User user;
        try {
            user = TokenHandler.getUserFromCookie(requestContext);
        } catch (NotLoggedInException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(e.getMessage())
                    .build();
        }

        chatbot.setUser(user);

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
     *
     * @param id the id of the chatbot
     * @return 200 if successful
     * @author Zwazel
     * @since 1.1.0
     */
    @RolesAllowed({"admin", "user"})
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteChatbot(@PathParam("id") String id, ContainerRequestContext requestContext) {
        User user;

        try {
            user = TokenHandler.getUserFromCookie(requestContext);
        } catch (NotLoggedInException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(e.getMessage())
                    .build();
        }

        ChatbotDao chatbotDao = new ChatbotDao();
        Chatbot chatbotFromDb = chatbotDao.findById(id);
        if (chatbotFromDb == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (UserRole.ADMIN == user.getUserRole() || chatbotFromDb.getUser().getId().equals(user.getId())) {
            new ChatbotDao().delete(id);
            return Response
                    .status(200)
                    .build();
        } else {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"You are not allowed to delete this chatbot.\"}")
                    .build();
        }
    }

    /**
     * Deletes a chatbot by its name.
     *
     * @param name the name of the chatbot
     * @return 200 if successful
     * @author Zwazel
     * @since 1.1.0
     */
    @RolesAllowed({"admin", "user"})
    @DELETE
    @Path("/delete/name/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteChatbotByName(@PathParam("name") String name, ContainerRequestContext requestContext) {
        User user;

        try {
            user = TokenHandler.getUserFromCookie(requestContext);
        } catch (NotLoggedInException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(e.getMessage())
                    .build();
        }

        ChatbotDao chatbotDao = new ChatbotDao();
        Chatbot chatbotFromDb = chatbotDao.findByName(name);
        if (chatbotFromDb == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (UserRole.ADMIN == user.getUserRole() || chatbotFromDb.getUser().getId().equals(user.getId())) {
            new ChatbotDao().delete(chatbotFromDb.getId());
            return Response
                    .status(200)
                    .build();
        } else {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"You are not allowed to delete this chatbot.\"}")
                    .build();
        }
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
                .addFilter("ChatbotFilter",
                        SimpleBeanPropertyFilter.serializeAll())
                .addFilter("RatingFilter",
                        SimpleBeanPropertyFilter.filterOutAllExcept("rating", "id"))
                .addFilter("UserFilter",
                        SimpleBeanPropertyFilter.filterOutAllExcept("id", "username"))
                .addFilter("QuestionAnswerFilter",
                        SimpleBeanPropertyFilter.filterOutAllExcept("id", "questionAnswerQuestions", "questionAnswerAnswers"))
                .addFilter("QuestionAnswerQuestionFilter",
                        SimpleBeanPropertyFilter.serializeAllExcept("questionAnswer"))
                .addFilter("QuestionAnswerAnswerFilter",
                        SimpleBeanPropertyFilter.serializeAllExcept("questionAnswer"))
                .addFilter("ChatbotUnknownTextsFilter",
                        SimpleBeanPropertyFilter.serializeAllExcept("chatbot"));
    }
}
