package no.kristiania.controllers;

import no.kristiania.http.HttpMessage;

import java.sql.SQLException;

public interface HttpController {
    HttpMessage handle(HttpMessage request) throws SQLException;

    //This is an interface, all classes implemented with this interface must implement this method handle().
    //It retrieves an HttpMessage object.
}
