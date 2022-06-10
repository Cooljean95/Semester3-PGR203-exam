package no.kristiania.controllers;

import no.kristiania.daos.AnswerDao;
import no.kristiania.daos.OptionDao;
import no.kristiania.http.HttpMessage;
import no.kristiania.object.Answer;
import no.kristiania.object.Option;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static no.kristiania.http.HttpMessage.parseRequestParameters;

public class EditOptionControllerQuery implements HttpControllerQuery{

    private final OptionDao optionDao;
    private List<Option> options = new ArrayList<>();


    public List<Option> getOptions() {
        return options;
    }

    //This controller edits the option and saves it to the database.
    public EditOptionControllerQuery(OptionDao optionDao){
        this.optionDao = optionDao;
    }

    @Override
    public HttpMessage handle(String query) throws SQLException {
        Map<String, String> queryMap = parseRequestParameters(query);
        Option option = new Option();
        int id = Integer.parseInt(queryMap.get("option"));
        option.setIdOption(id);
        option.setOption(queryMap.get("newOption+"));
        optionDao.edit(option);
        options.add(option);

        String responseText = "The database has been updated!";
        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}
