package no.kristiania.controllers;

import no.kristiania.daos.QuestionDao;
import no.kristiania.http.HttpMessage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionOptionsController implements HttpController {
    private final QuestionDao questionDao;

    public QuestionOptionsController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    //This Overrides the implemented handle method from HttpController.
    //This method makes a dropdown bar of questions to the add option side.
    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        String responseText = "";

        int value = 1;
        for (String option : questionDao.retrieveOptions()) {
            responseText += "<option value=" + (value++) + ">" + option + "</option>";
        }
        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}
