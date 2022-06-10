package no.kristiania.dao;

import no.kristiania.daos.OptionDao;
import no.kristiania.daos.QuestionDao;
import no.kristiania.object.Option;
import no.kristiania.object.Questions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionDaoTest {
    private QuestionDao questionDao = new QuestionDao(TestData.createDataSource());
    private OptionDao optionDao = new OptionDao(TestData.createDataSource());

    //This test returns random options from a randomOption method
    @Test
    void shouldReturnRandomOption() throws SQLException {
        Questions questions1 = TestData.randomQuestion("Food");
        Questions questions2 = TestData.randomQuestion("Animals");
        Questions questions3 = TestData.randomQuestion("School");
        questionDao.save(questions1);
        questionDao.save(questions2);
        questionDao.save(questions3);

        String options = TestData.pickOneString("Food", "Animals", "School");
        Option option = TestData.randomOption(questionDao, options);
        optionDao.save(option);
        ArrayList<String> options1 = optionDao.retrieveOptions();
        assertThat(optionDao.retrieveOptions())
                .usingRecursiveComparison()
                .isEqualTo(options1);
    }

    //Test for listing all the options, using the randomOption method
    @Test
    void shouldListAll() throws SQLException {
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

        ArrayList<String> options = OptionDao.listAll();

        assertThat(OptionDao.listAll())
                .isEqualTo(options);
    }
}
