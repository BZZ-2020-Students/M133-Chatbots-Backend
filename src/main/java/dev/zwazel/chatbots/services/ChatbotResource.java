package dev.zwazel.chatbots.services;

import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.Chatbot;
import dev.zwazel.chatbots.classes.model.QuestionAnswer;
import dev.zwazel.chatbots.classes.model.Text;
import dev.zwazel.chatbots.classes.model.User;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Set;

@Path("/chatbot")
public class ChatbotResource {
    @GET
    @Path("/{id}/{name}")
    public Response getChatbot(@PathParam("id") String id, @PathParam("name") String name) {
        Chatbot chatbot = Chatbot.builder()
                .name(name)
                .owner(User.builder()
                        .name("Test")
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

        return Response
                .status(200)
                .entity(chatbot.toJson())
                .build();
    }
}
