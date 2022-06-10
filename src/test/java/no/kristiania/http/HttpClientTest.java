package no.kristiania.http;

import no.kristiania.dao.TestData;
import no.kristiania.daos.AnswerDao;
import no.kristiania.daos.OptionDao;
import no.kristiania.daos.QuestionDao;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpClientTest {

    HttpServer server = new HttpServer(0);
    private final TestData testData = new TestData();
    OptionDao optionDao = new OptionDao(TestData.createDataSource());
    QuestionDao questionDao = new QuestionDao(TestData.createDataSource());
    AnswerDao answerDao = new AnswerDao(TestData.createDataSource());

    public HttpClientTest() throws IOException {
    }

    //This test compares statusCode, content-type and messageBody.
    @Test
    void shouldReturn200OK() throws IOException, SQLException {

        // the table is deleted because the test can bleed in to each other.
        testData.controllers(server);
        questionDao.deleteTable();
        optionDao.deleteTable();
        answerDao.deleteTable();

        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/questions");
        assertEquals(200, client.getStatusCode());
        assertEquals("text/plain; charset=utf-8", client.getHeader("Content-Type"));
        assertEquals("Here are the questions saved in the database:<br><br>[]<br><br>Here are all the diffrent options:"+
                "<br><br> []<br><br>Here are the answers with their question that is saved in the database:<br><br>{[]=[]}", client.getMessageBody());
    }

    //Test should return 404, because requestTarget is non-existing
    @Test
    void shouldReturn404() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/non-existing");
        assertEquals(404, client.getStatusCode());
    }

    //Responding with file not found is the request target is non-existing
    @Test
    void shouldRespondWithRequestTargetIn404() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(),"/non-existing");
        assertEquals("File not found", client.getMessageBody());
    }

    //Test for handling more than one request, important because the server will crash everytime if it does not handle more than one
    @Test
    void shouldHandleMoreThanOneRequest() throws IOException {
        testData.controllers(server);

        assertEquals(200, new HttpClient("localhost", server.getPort(),"/index.html").getStatusCode());
        assertEquals(200, new HttpClient("localhost", server.getPort(),"/api/questionOptions").getStatusCode());
    }
}
