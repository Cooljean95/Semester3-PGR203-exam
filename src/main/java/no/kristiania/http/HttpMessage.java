package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpMessage {

    public static String startLine;
    public String messageBody;
    public HashMap<String, String> headerFields = new HashMap <>();

    // This is a constructor that wants a socket.
    public HttpMessage(Socket socket) throws IOException {
        startLine = HttpMessage.readLine(socket);
        readHeaders(socket);
        if (headerFields.containsKey("Content-Length")) {
            messageBody = HttpMessage.readBytes(socket, getContentLength());
        }
    }

    //constructor that takes two String
    public HttpMessage(String startLine, String messageBody) {
        this.startLine = startLine;
        this.messageBody = messageBody;
    }

    //this splits up the different context of the webpage and puts it in a hashmap.
    private void readHeaders(Socket socket) throws IOException {
        String headerLine;
        while (!(headerLine = HttpMessage.readLine(socket)).isBlank()){
            int colonPos = headerLine.indexOf(':');
            String headerField = headerLine.substring(0, colonPos);
            String headerValue = headerLine.substring(colonPos+1).trim();
            headerFields.put(headerField, headerValue);
        }
    }

    //Translates from bytes to char, and makes whole sentences.
    static String readLine(Socket socket) throws IOException {
        StringBuilder buffer = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != '\r') {
            buffer.append((char)c);
        }
        int expectedNewline = socket.getInputStream().read();
        assert expectedNewline == '\n';
        return buffer.toString();
    }

    static String readBytes(Socket socket, int contentLength) throws IOException{
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < contentLength; i++){
            buffer.append((char)socket.getInputStream().read());
        }
        return buffer.toString();
    }

    //This writes a response back to the client from the server.
    public void write(Socket socket) throws IOException {
        String response = startLine + "\r\n" +
                "Content-Length:" + messageBody.length() + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                messageBody;
        socket.getOutputStream().write(response.getBytes());
    }

    //This splits up the url inn to usable pieces and puts it in a hashmap.
    public static Map<String, String> parseRequestParameters(String query) {
        Map<String, String> queryMap = new HashMap<>();
        for (String queryParameter : query.split("&")) {
            int equalsPos = queryParameter.indexOf('=');
            String parameterName = queryParameter.substring(0, equalsPos);
            String parameterValue = queryParameter.substring(equalsPos+1);
            queryMap.put(parameterName, parameterValue);
        }
        return queryMap;
    }

    //Getters
    public int getContentLength(){
        return Integer.parseInt(getHeader("Content-Length"));
    }

    public String getHeader(String headerName){
        return headerFields.get(headerName);
    }

}
