package no.kristiania.dao;

import no.kristiania.daos.AnswerDao;
import no.kristiania.daos.OptionDao;
import no.kristiania.daos.QuestionDao;
import no.kristiania.object.Answer;
import no.kristiania.object.Option;
import no.kristiania.object.Questions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerDaoTest {

    QuestionDao questionDao = new QuestionDao(TestData.createDataSource());
    AnswerDao answerDao = new AnswerDao(TestData.createDataSource());
    OptionDao optionDao = new OptionDao(TestData.createDataSource());

//Test for retrieving saved questions. Retrieving random questions from randomQuestion method
    @Test
    void shouldRetrieveSavedQuestion() throws SQLException {
        Questions questions1 = TestData.randomQuestion("Food");
        Questions questions2 = TestData.randomQuestion("Animals");
        Questions questions3 = TestData.randomQuestion("School");
        questionDao.save(questions1);
        questionDao.save(questions2);
        questionDao.save(questions3);

        Option option1 = TestData.randomOption(questionDao,"Food");
        Option option2 = TestData.randomOption(questionDao,"Animals");
        Option option3 = TestData.randomOption(questionDao,"School");
        optionDao.save(option1);
        optionDao.save(option2);
        optionDao.save(option3);

        Answer answer = new Answer();
        answer.setAnswer(option2.getOption());
        answer.setQuestionId(option2.getIdQuestion());

        answerDao.save(answer);

        assertThat(option2.getOption())
                .isEqualTo(answer.getAnswer())
    ;
    }

}
