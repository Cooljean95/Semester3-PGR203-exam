package no.kristiania.controllers;

import no.kristiania.daos.OptionDao;
import no.kristiania.http.HttpMessage;

import java.sql.SQLException;

public class ShowOptionController implements HttpController {

    private final OptionDao optionDao;

    public ShowOptionController(OptionDao optionDao) {
        this.optionDao = optionDao;
    }

    //This is an overridden method that comes from HttpController. Its only returns a string off options and the corresponding question.
    //It's so the person on the answer webpage can see what question the option belongs to.
    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {

        String responseText = optionDao.joinOptionAndQuestion().toString();
        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}
