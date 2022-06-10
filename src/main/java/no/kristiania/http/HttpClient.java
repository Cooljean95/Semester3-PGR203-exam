package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;

public class HttpClient {

    private int statusCode;
    private HttpMessage httpMessage;

    //This is the clint that sends a request to the server, and retrieves the servers information.
    public HttpClient(String host, int port, String requestTarget) throws IOException {
        Socket socket = new Socket(host, port);

        String request = "GET " + requestTarget+ " HTTP/1.1\r\n" +
                "Host:" + host + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";

        socket.getOutputStream().write(request.getBytes());

        httpMessage = new HttpMessage(socket);
        String[] statusLine = httpMessage.startLine.split(" ");
        this.statusCode = Integer.parseInt(statusLine[1]);
    }

    //Getters only
    public int getStatusCode() {
        return statusCode;
    }

    public String getMessageBody() {
        return httpMessage.messageBody;
    }

    public String getHeader(String headerTitle) {
        return httpMessage.headerFields.get(headerTitle);
    }
}
