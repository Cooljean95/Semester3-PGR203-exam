package no.kristiania.controllers;

import no.kristiania.daos.OptionDao;
import no.kristiania.http.HttpMessage;

import java.sql.SQLException;

public class OptionOptionControllerQuery implements HttpControllerQuery{

    private final OptionDao optionDao;

    public OptionOptionControllerQuery(OptionDao optionDao) {
        this.optionDao = optionDao;
    }

    //This Overrides the implemented handle method from HttpControllerQuery.
    //This makes a dropdown bar of options to the edit options and add answer side.
    @Override
    public HttpMessage handle(String query) throws SQLException {
        String responseText = "";
        int value = 1;
        for (String option : optionDao.retrieveOptions()) {
            responseText += "<option value=" + (value++) + ">" + option + "</option>";
        }

        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}
