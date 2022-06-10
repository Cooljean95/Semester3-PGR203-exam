package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpPostClient {

    private final int statusCode;

    //Sends a post request to the webpage, post asks for a change in the webpage.
    public HttpPostClient(String host, int port, String requestTarget, String contentBody) throws IOException {
        Socket socket = new Socket(host, port);

        String request = "POST " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n" +
                "Content-Length: " + contentBody.getBytes().length + "\r\n" +
                "\r\n" +
                contentBody;

        socket.getOutputStream().write(request.getBytes(StandardCharsets.UTF_8));
        HttpMessage httpMessage = new HttpMessage(socket);
        String[] statusLine = HttpMessage.startLine.split(" ");
        this.statusCode = Integer.parseInt(statusLine[1]);
    }

    public int getStatusCode(){
        return statusCode;
    }
}
