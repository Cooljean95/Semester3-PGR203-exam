package no.kristiania.http;

import no.kristiania.controllers.*;
import no.kristiania.dao.TestData;
import no.kristiania.daos.OptionDao;
import no.kristiania.daos.QuestionDao;
import no.kristiania.object.Option;
import no.kristiania.object.Questions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {

    private final QuestionDao questionDao = new QuestionDao(TestData.createDataSource());
    private final OptionDao optionDao = new OptionDao(TestData.createDataSource());
    private final TestData testData = new TestData();


    HttpServer server = new HttpServer(0);

    public HttpServerTest() throws IOException {
    }

    //This test creates a new question
    @Test
    void shouldCreateNewQuestion() throws IOException, SQLException {

        NewQuestionController newQuestionController = new NewQuestionController(questionDao);
        server.addController("/api/questions", newQuestionController);

        HttpPostClient postClient = new HttpPostClient(
                "localhost",
                server.getPort(),
                "/api/questions",
                "title=Food&text=Do you like waffles?&low_label=Yeah we like waffles!&high_label=No"
        );

        Questions questions = newQuestionController.getQuestion().get(0);

        assertEquals(200, postClient.getStatusCode());
        assertEquals("Food", questions.getTitle());

        questionDao.deleteTable();
    }

    //This test edits an option
    @Test
    void shouldEditOption() throws IOException, SQLException {
        Questions questions1 = TestData.randomQuestion("Food");
        Questions questions2 = TestData.randomQuestion("Animals");
        Questions questions3 = TestData.randomQuestion("School");

        questionDao.save(questions1);
        questionDao.save(questions2);
        questionDao.save(questions3);

        Option option1 = TestData.randomOption(questionDao, "Food");
        Option option2 = TestData.randomOption(questionDao, "Animals");
        Option option3 = TestData.randomOption(questionDao, "School");

        optionDao.save(option1);
        optionDao.save(option2);
        optionDao.save(option3);


        EditOptionControllerQuery editOptionControllerQuery = new EditOptionControllerQuery(optionDao);
        server.addQueryControllers("/api/editOption", editOptionControllerQuery);

        HttpClient postClient = new HttpClient(
                "localhost",
                server.getPort(),
                "/api/editOption?option=1&newOption+=Balder"
        );

        Option option = editOptionControllerQuery.getOptions().get(0);
        assertEquals(200, postClient.getStatusCode());
        assertEquals("Balder", option.getOption());

        optionDao.deleteTable();
    }

    //This test returns options from the server and compares to messageBody
    @Test
    void shouldReturnOptionsFromServer() throws IOException, SQLException{
        Questions questions1 = TestData.randomQuestion("Food");
        Questions questions2 = TestData.randomQuestion("Animals");
        Questions questions3 = TestData.randomQuestion("School");

        questionDao.save(questions1);
        questionDao.save(questions2);
        questionDao.save(questions3);

        Option option1 = TestData.randomOption(questionDao, "Food");
        Option option2 = TestData.randomOption(questionDao, "Animals");
        Option option3 = TestData.randomOption(questionDao, "School");

        ArrayList<String> list = new ArrayList<>();

        optionDao.save(option1);
        optionDao.save(option2);
        optionDao.save(option3);

        list.add(option1.getOption());
        list.add(option2.getOption());
        list.add(option3.getOption());

        testData.controllers(server);

        HttpClient clientOne = new HttpClient("localhost", server.getPort(), "/api/optionOptions");
        assertThat("<option value=1>" + list.get(0) + "</option><option value=2>" + list.get(1) + "</option><option value=3>" + list.get(2) + "</option>")
                .isEqualTo(clientOne.getMessageBody());

        questionDao.deleteTable();
        optionDao.deleteTable();

    }

    //This test creates an option in the database
    @Test
    void shouldCreateOption() throws IOException, SQLException {
        OptionDao optionDao1 = new OptionDao(TestData.createDataSource());

        AddOptionControllerQuery addOptionControllerQuery = new AddOptionControllerQuery(optionDao1);
        server.addQueryControllers("/api/addOption", addOptionControllerQuery);

        HttpClient postClient = new HttpClient(
                "localhost", server.getPort(), "/api/addOption?questions=1&option+=Socks"
        );

        Option option = addOptionControllerQuery.getOptions().get(0);
        assertEquals(200, postClient.getStatusCode());
        assertEquals("Socks", option.getOption());
        assertEquals("It is saved in the database", postClient.getMessageBody());

        questionDao.deleteTable();
        optionDao.deleteTable();
    }
}

