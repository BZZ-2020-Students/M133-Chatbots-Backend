package dev.zwazel.chatbots.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.Chatbot;
import dev.zwazel.chatbots.classes.model.QuestionAnswer;
import dev.zwazel.chatbots.classes.model.Text;
import dev.zwazel.chatbots.classes.model.User;
import dev.zwazel.chatbots.interfaces.ToJson;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.Set;

/**
 * Resource class for the chatbot.
 *
 * @author Zwazel
 * @since 0.2
 */
@Path("/chatbot")
public class ChatbotResource {
    /**
     * Get a chatbot by its id and name.
     * todo: change this to a get by id, and retrieve the name from the database.
     *
     * @param id   the id of the chatbot
     * @param name the name of the chatbot
     * @return the chatbot
     * @author Zwazel
     * @since 0.2
     */
    @GET
    @Path("/{id}/{name}")
    public Response getChatbot(@PathParam("id") String id, @PathParam("name") String name) {
        Chatbot chatbot = Chatbot.builder()
                .name(name)
                .owner(User.builder()
                        .username("Test")
                        .password("1234")
                        .userRole(UserRole.USER)
                        .build()
                )
                .questionAnswers(Set.of(
                                QuestionAnswer.builder()
                                        .answers(Set.of(
                                                Text.builder().text("Answer 1").build()
                                        ))
                                        .questions(Set.of(
                                                Text.builder().text("Question 1").build()
                                        ))
                                        .build(),
                                QuestionAnswer.builder()
                                        .answers(Set.of(
                                                Text.builder().text("Answer 2").build()
                                        ))
                                        .questions(Set.of(
                                                Text.builder().text("Question 2").build()
                                        ))
                                        .build()
                        )
                )
                .build();

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
