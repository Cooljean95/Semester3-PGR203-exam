package no.kristiania.controllers;

import no.kristiania.http.HttpMessage;

import java.sql.SQLException;

public interface HttpControllerQuery {
    HttpMessage handle(String query) throws SQLException;

    //This is an interface, all classes implemented with this interface must implement this method handle().
    //It retrieves a String.
}
