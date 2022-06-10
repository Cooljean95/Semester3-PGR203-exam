package no.kristiania.controllers;

import no.kristiania.daos.OptionDao;
import no.kristiania.http.HttpMessage;
import no.kristiania.object.Option;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static no.kristiania.http.HttpMessage.parseRequestParameters;

public class AddOptionControllerQuery implements HttpControllerQuery {

    private final OptionDao optionDao;
    private final List<Option> options = new ArrayList<>();

    public List<Option> getOptions() {
        return options;
    }

    public AddOptionControllerQuery(OptionDao optionDao) {
        this.optionDao = optionDao;
    }

    //This Overrides the implemented handle method from HttpQueryController.
    //This adds an option to the database, with the corresponding question id.
    @Override
    public HttpMessage handle(String query) throws SQLException {
        Map<String, String> queryMap = parseRequestParameters(query);
        Option option = new Option();
        int id = Integer.parseInt(queryMap.get("questions"));
        option.setIdQuestion(id);
        option.setOption(queryMap.get("option+"));
        optionDao.save(option);
        options.add(option);
        String responseText = "It is saved in the database";

        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}
