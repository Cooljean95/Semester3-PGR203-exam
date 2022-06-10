package no.kristiania.controllers;

import no.kristiania.daos.QuestionDao;
import no.kristiania.http.HttpMessage;
import no.kristiania.object.Questions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static no.kristiania.http.HttpMessage.parseRequestParameters;

public class EditQuestionControllerQuery implements HttpControllerQuery {

    private final QuestionDao questionDao;

    public EditQuestionControllerQuery(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    //This Overrides the implemented handle method from HttpQueryController.
    //This method updates the database with editing an old question. Changing the title and text.
    @Override
    public HttpMessage handle(String query) throws SQLException {
        Map<String, String> queryMap = parseRequestParameters(query);
        Questions questions = questionDao.retrieve(Integer.parseInt(queryMap.get("questions")));
        questions.setText(queryMap.get("text"));
        questions.setTitle(queryMap.get("title"));
        questionDao.edit(questions);

        String responseText = "The database is updated!";
        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}
