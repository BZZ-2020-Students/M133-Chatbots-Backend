package dev.zwazel.chatbots.util;

import dev.zwazel.chatbots.classes.dao.ChatbotDao;
import dev.zwazel.chatbots.classes.dao.UserDao;
import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.Chatbot;
import dev.zwazel.chatbots.classes.model.QuestionAnswer;
import dev.zwazel.chatbots.classes.model.Text;
import dev.zwazel.chatbots.classes.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class is used to initialize the database with Data on startup.
 *
 * @author Zwazel
 * @since 0.3
 */
@Singleton
@Startup
public class InitDbOnStartup {
    /**
     * This method is used to initialize the database with Data on startup.
     *
     * @author Zwazel
     * @since 0.3
     */
    @PostConstruct
    public void init() {
        System.out.println("Initiation of DB");

        List<User> users = getUsers();
        UserDao userDao = new UserDao();
        for (User u : users) {
            User userFromDb = userDao.findByUsername(u.getUsername());
            if (userFromDb == null) {
                userDao.save(u);
            } else {
                System.out.println(u.getUsername() + " already exists");
            }
        }

        Iterable<User> persistedUsers = userDao.findAll();
        List<Chatbot> chatbots = getChatbots(persistedUsers);
        ChatbotDao chatbotDao = new ChatbotDao();
        for (Chatbot c : chatbots) {
            Chatbot chatbotFromDb = chatbotDao.findByName(c.getChatbotName());
            System.out.println("searching for " + c.getChatbotName());
            System.out.println("chatbotFromDb = " + chatbotFromDb);
            if (chatbotFromDb == null) {
                chatbotDao.save(c);
            } else {
                System.out.println(c.getChatbotName() + " already exists");
            }
        }

        System.out.println("DB initiation finished");
    }

    /**
     * This method is used to get the Chatbots for the Database.
     *
     * @param users the users to be added to the Chatbots
     * @return the Chatbots for the Database
     * @author Zwazel
     * @since 0.3
     */
    private List<Chatbot> getChatbots(Iterable<User> users) {
        List<Chatbot> chatbots = new ArrayList<>();

        User user = users.iterator().next();

        QuestionAnswer questionAnswer = QuestionAnswer.builder()
                .answers(Set.of(
                        Text.builder()
                                .text("Answer 1")
                                .build(),
                        Text.builder()
                                .text("Answer 2")
                                .build(),
                        Text.builder()
                                .text("Answer 3")
                                .build()
                ))
                .questions(Set.of(
                        Text.builder()
                                .text("Question 1")
                                .build(),
                        Text.builder()
                                .text("Question 2")
                                .build(),
                        Text.builder()
                                .text("Question 3")
                                .build()
                ))
                .build();

        Chatbot chatbot = Chatbot.builder()
                .chatbotName("MRVN")
                .owner(user)
                .questionAnswers(Set.of(
                        questionAnswer
                ))
                .unknownTexts(Set.of(
                        Text.builder()
                                .text("Unknown Text 1")
                                .build(),
                        Text.builder()
                                .text("Unknown Text 2")
                                .build()
                ))
                .build();

        questionAnswer.setChatbot(chatbot);

        chatbots.add(chatbot);

        return chatbots;
    }

    /**
     * This method is used to get the Users for the Database.
     *
     * @return the Users for the Database
     * @author Zwazel
     * @since 0.3
     */
    private List<User> getUsers() {
        List<User> users = new ArrayList<>();

        User user = User.builder()
                .username("user")
                .password("user")
                .userRole(UserRole.USER)
                .build();

        users.add(user);

        User admin = User.builder()
                .username("admin")
                .password("admin")
                .userRole(UserRole.ADMIN)
                .build();

        users.add(admin);

        return users;
    }
}
