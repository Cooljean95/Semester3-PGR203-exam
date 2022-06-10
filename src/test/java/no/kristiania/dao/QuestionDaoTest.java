package no.kristiania.dao;

import no.kristiania.daos.AnswerDao;
import no.kristiania.daos.QuestionDao;
import no.kristiania.object.Questions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionDaoTest {
    private final QuestionDao questionDao = new QuestionDao(TestData.createDataSource());
    private final AnswerDao answerDao = new AnswerDao(TestData.createDataSource());

    //Test for retrieving a question from a title, using randomQuestion method
    @Test
    void shouldRetrieveOneQuestionsFromTitle() throws SQLException {

        Questions questions1 = TestData.randomQuestion("Food");
        Questions questions2 = TestData.randomQuestion("Animals");
        Questions questions3 = TestData.randomQuestion("School");

        questionDao.save(questions1);
        questionDao.save(questions2);
        questionDao.save(questions3);

        ArrayList<String> list = new ArrayList<>();
        assertThat(questionDao.listAllQuestions(1L,list)).isEqualTo(list);
    }

    //Test for return a random question from randomQuestion method
    @Test
    void shouldReturnRandomQuestion() throws SQLException {
        questionDao.retrieveWithString("Food");

        String questions = TestData.pickOneString("Food", "Animals", "School");
        Questions question = TestData.randomQuestion(questions);
        questionDao.save(question);
        assertThat(questionDao.retrieve(question.getIdQuestion()))
                .usingRecursiveComparison()
                .isEqualTo(question);
    }

    //This test should list all the questions
    @Test
    void shouldListAllQuestions() throws SQLException {

        Questions questions1 = TestData.randomQuestion("Food");
        Questions questions2 = TestData.randomQuestion("Animals");
        Questions questions3 = TestData.randomQuestion("School");

        questionDao.save(questions1);
        questionDao.save(questions2);
        questionDao.save(questions3);

        ArrayList<String> questions = questionDao.listAll();

        assertThat(questionDao.listAll())
                .isEqualTo(questions);
    }

}


