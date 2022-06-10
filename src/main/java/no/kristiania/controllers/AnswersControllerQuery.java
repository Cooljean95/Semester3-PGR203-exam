package no.kristiania.controllers;

import no.kristiania.daos.AnswerDao;
import no.kristiania.daos.OptionDao;
import no.kristiania.http.HttpMessage;
import no.kristiania.object.Answer;
import no.kristiania.object.Option;

import java.sql.SQLException;
import java.util.Map;

import static no.kristiania.http.HttpMessage.parseRequestParameters;

public class AnswersControllerQuery implements HttpControllerQuery {

    private final AnswerDao answerDao;
    private final OptionDao optionDao;

    public AnswersControllerQuery(AnswerDao answerDao, OptionDao optionDao){
        this.answerDao = answerDao;
        this.optionDao = optionDao;
    }

    //This Overrides the implemented handle method from HttpQueryController.
    //This method adds a answer to the database, if not done correctly the answer will not be added.
    @Override
    public HttpMessage handle(String query) throws SQLException {
        Map<String, String> queryMap = parseRequestParameters(query);
        Answer answer = new Answer();
        Option option = optionDao.retrieveId(Integer.parseInt(queryMap.get("option")));
        String responseText;
        if (option.getOption().equalsIgnoreCase(queryMap.get("Answer"))){
            answer.setQuestionId(option.getIdQuestion());
            answer.setAnswer(queryMap.get("Answer"));
            answerDao.save(answer);
            responseText = "It is saved in the database";
        }else{
            responseText = "Dette ble ikke lagret i databasen. Det du valgte på drop down menyen må tilsvare det du skriver i feltet";
        }
        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}
