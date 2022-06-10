package no.kristiania.controllers;

import no.kristiania.daos.QuestionDao;
import no.kristiania.http.HttpMessage;
import no.kristiania.object.Questions;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewQuestionController implements HttpController {

    public List<Questions> getQuestion() {
        return question;
    }

    private List<Questions> question = new ArrayList<>();
    private QuestionDao questionDao;

    public NewQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    //This Overrides the implemented handle method from HttpController.
    //This method adds a new question to the Question database. And sends a ok response to the webpage.
    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        String responseText = "";
        Questions questions = new Questions();
        questions.setTitle(queryMap.get("title"));
        questions.setText(queryMap.get("text"));
        questions.setHighL(queryMap.get("low_label"));
        questions.setLowL(queryMap.get("high_label"));
        question.add(questions);
        questionDao.save(questions);

        responseText = "Question Id:" + questions.getIdQuestion()+ " Question title:" +questions.getTitle();
        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}
