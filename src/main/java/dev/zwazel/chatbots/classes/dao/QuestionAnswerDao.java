package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.QuestionAnswer;
import dev.zwazel.chatbots.classes.model.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Dao Class for QuestionAnswer objects
 *
 * @author Zwazel
 * @since 0.3
 */
public class QuestionAnswerDao extends Dao<QuestionAnswer, String> {
    /**
     * Default constructor.
     *
     * @author Zwazel
     * @since 0.3
     */
    public QuestionAnswerDao() {
        super(QuestionAnswer.class);
    }

    @Override
    public void save(QuestionAnswer questionAnswer) {
        List<Text> questions = new ArrayList<>();
        questionAnswer.getQuestionAnswerQuestions().forEach(question -> {
            questions.add(question.getQuestion());
        });

        List<Text> answers = new ArrayList<>();
        questionAnswer.getQuestionAnswerAnswers().forEach(answer -> {
            answers.add(answer.getAnswer());
        });

        TextDao textDao = new TextDao();
        textDao.saveCollection(questions);
        textDao.saveCollection(answers);

        super.save(questionAnswer);
    }

    @Override
    public void saveCollection(Iterable<QuestionAnswer> t) {
        super.saveCollection(t);
    }
}
