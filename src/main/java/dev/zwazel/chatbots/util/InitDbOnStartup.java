package dev.zwazel.chatbots.util;

import dev.zwazel.chatbots.classes.dao.ChatbotDao;
import dev.zwazel.chatbots.classes.dao.RatingDao;
import dev.zwazel.chatbots.classes.dao.TextDao;
import dev.zwazel.chatbots.classes.dao.UserDao;
import dev.zwazel.chatbots.classes.enums.RatingEnum;
import dev.zwazel.chatbots.classes.enums.UserRole;
import dev.zwazel.chatbots.classes.model.*;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
            if (chatbotFromDb == null) {
                TextDao textDao = new TextDao();

                Set<ChatbotUnknownTexts> unknownTextsChatbot = c.getChatbotUnknownTexts();
                List<Text> unknownTexts = new ArrayList<>();

                unknownTextsChatbot.forEach(ut -> unknownTexts.add(ut.getUnknownText()));

                textDao.saveCollection(unknownTexts);
                c.getQuestionAnswers().forEach(qa -> {
                    List<Text> answers = new ArrayList<>();
                    qa.getQuestionAnswerAnswers().forEach(qaAnswer -> answers.add(qaAnswer.getAnswer()));
                    textDao.saveCollection(answers);

                    List<Text> questions = new ArrayList<>();
                    qa.getQuestionAnswerQuestions().forEach(qaQuestion -> questions.add(qaQuestion.getQuestion()));
                    textDao.saveCollection(questions);
                });

                chatbotDao.save(c);
            } else {
                System.out.println(c.getChatbotName() + " already exists");
            }
        }
        Iterable<Chatbot> persistedChatbots = chatbotDao.findAll();

        List<Rating> ratings = getRatings(persistedUsers, persistedChatbots);
        RatingDao ratingDao = new RatingDao();
        ratingDao.saveCollection(ratings);

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

        Chatbot chatbot = Chatbot.builder()
                .chatbotName("MRVN")
                .user(user)
                .build();

        QuestionAnswer questionAnswer = QuestionAnswer.builder()
                .questionAnswerAnswers(Set.of(
                        QuestionAnswerAnswer.builder()
                                .answer(Text.builder()
                                        .text("Answer 1")
                                        .build())
                                .build(),
                        QuestionAnswerAnswer.builder()
                                .answer(Text.builder()
                                        .text("Answer 2")
                                        .build())
                                .build(),
                        QuestionAnswerAnswer.builder()
                                .answer(Text.builder()
                                        .text("Answer 3")
                                        .build())
                                .build()
                ))
                .questionAnswerQuestions(Set.of
                        (
                                QuestionAnswerQuestion.builder()
                                        .question(Text.builder()
                                                .text("Question 1")
                                                .build())
                                        .build(),
                                QuestionAnswerQuestion.builder()
                                        .question(Text.builder()
                                                .text("Question 2")
                                                .build())
                                        .build(),
                                QuestionAnswerQuestion.builder()
                                        .question(Text.builder()
                                                .text("Question 3")
                                                .build())
                                        .build()
                        )
                )
                .chatbot(chatbot)
                .build();

        questionAnswer.getQuestionAnswerAnswers().forEach(answer -> answer.setQuestionAnswer(questionAnswer));
        questionAnswer.getQuestionAnswerQuestions().forEach(question -> question.setQuestionAnswer(questionAnswer));

        questionAnswer.setChatbot(chatbot);
        Set<ChatbotUnknownTexts> unknownTexts = Set.of(
                ChatbotUnknownTexts.builder()
                        .chatbot(chatbot)
                        .unknownText(Text.builder()
                                .text("Unknown Text 1")
                                .build())
                        .build(),
                ChatbotUnknownTexts.builder()
                        .chatbot(chatbot)
                        .unknownText(Text.builder()
                                .text("Unknown Text 2")
                                .build())
                        .build()
        );
        chatbot.setChatbotUnknownTexts(unknownTexts);
        chatbot.setQuestionAnswers(Set.of(questionAnswer));

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
        String verySecureUserPassword = "v3Ry$ecUre";

        User user = User.builder()
                .username("user")
                .password(verySecureUserPassword)
                .userRole(UserRole.USER)
                .build();

        users.add(user);

        User admin = User.builder()
                .username("admin")
                .password(verySecureUserPassword)
                .userRole(UserRole.ADMIN)
                .build();

        users.add(admin);

        return users;
    }

    /**
     * Gets dummy ratings for the Database.
     *
     * @param users    the users to be added to the ratings
     * @param chatbots the chatbots to be added to the ratings
     * @return the ratings for the Database
     * @author Zwazel
     * @since 0.3
     */
    private List<Rating> getRatings(Iterable<User> users, Iterable<Chatbot> chatbots) {
        List<Rating> ratings = new ArrayList<>();
        User randomUser = users.iterator().next();
        Chatbot randomChatbot = chatbots.iterator().next();
        RatingEnum randomRatingEnum = RatingEnum.values()[new Random().nextInt(RatingEnum.values().length)];

        Rating rating = Rating.builder()
                .rating(randomRatingEnum)
                .user(randomUser)
                .chatbot(randomChatbot)
                .favourite((new Random().nextInt(2) == 0))
                .build();

        ratings.add(rating);

        return ratings;
    }
}
