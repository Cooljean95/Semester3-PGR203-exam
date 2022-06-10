package no.kristiania.http;

import no.kristiania.controllers.HttpController;
import no.kristiania.controllers.HttpControllerQuery;
import no.kristiania.daos.AnswerDao;
import no.kristiania.daos.OptionDao;
import no.kristiania.daos.QuestionDao;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.*;
import java.sql.SQLException;

public class HttpServer {

    private final ServerSocket serverSocket;
    private Path rootDirectory;
    private QuestionDao questionDao;
    private AnswerDao answerDao;
    private OptionDao optionDao;
    private HashMap<String, HttpController> controllers = new HashMap<>();
    private HashMap<String, HttpControllerQuery> queryControllers = new HashMap<>();

    // This is the constructor that start everything, it runs Theard, witch makes it possible for the code to run the server and client at the same time.
    public HttpServer(int serverPort) throws IOException {
        serverSocket = new ServerSocket(serverPort);

        new Thread(this::handleClients).start();
    }

// This runs handleClient in a loop so the server stays up.
    private void handleClients(){
        try {
            while (true){
                handleClient();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong, try again.");
        }
    }

// This is the method that finds its way though the webpage, it also contains the massage. And divides between post and get.
    private void handleClient() throws IOException, SQLException {
        Socket clientSocket = serverSocket.accept();
        HttpMessage httpMessage = new HttpMessage(clientSocket);
        String contentType = "text/plain; charset=utf-8";
        String[] requestLine = httpMessage.startLine.split(" ");
        String requestTarget = requestLine[1];
        String request = requestLine[0];

        if (requestTarget.endsWith(".html")) {
            contentType = "text/html; ; charset=utf-8";
        } else if (requestTarget.endsWith(".css")) {  //this will provide !DOCTYPE
            contentType = "text/css";
        }

        int questionPos = requestTarget.indexOf('?');
        String fileTarget;
        String query = null;
        if (questionPos != -1) {
            fileTarget = requestTarget.substring(0, questionPos);
            query = requestTarget.substring(questionPos + 1);
        } else {
            fileTarget = requestTarget;
        }

        InputStream fileResource = getClass().getResourceAsStream(fileTarget);

        if (request.equals("POST")) {
             if(controllers.containsKey(fileTarget)){
                HttpMessage response = controllers.get(fileTarget).handle(httpMessage);
                response.write(clientSocket);
            }else if (queryControllers.containsKey(fileTarget)){
                 HttpMessage response = queryControllers.get(fileTarget).handle(query);
                 response.write(clientSocket);}
        }else{
            if(fileTarget.equals("/api/questions")){
                String responseText = "Here are the questions saved in the database:<br><br>" + questionDao.listAll().toString() + "<br><br>" +
                        "Here are all the diffrent options:<br><br> " + optionDao.joinOptionAndQuestion().toString() + "<br><br>" +
                       "Here are the answers with their question that is saved in the database:<br><br>" + answerDao.listAll().toString();
                okResponse(clientSocket, responseText, contentType);
            }else if (queryControllers.containsKey(fileTarget)){
                HttpMessage response = queryControllers.get(fileTarget).handle(query);
                response.write(clientSocket);
            }else if (controllers.containsKey(fileTarget)) {
                HttpMessage response = controllers.get(fileTarget).handle(httpMessage);
                response.write(clientSocket);
            }else if (fileResource != null) {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                fileResource.transferTo(buffer);
                String responseTxt = buffer.toString();
                okResponse(clientSocket, responseTxt, contentType);
            }else {
                String responseTxt = "File not found";
                notFound(clientSocket, responseTxt);
            }
        }
    }

    //If thing went wrong or not found a file, it will giv ut 404 Not found
    private void notFound(Socket clientSocket, String responseTxt) throws IOException {

        String response = "HTTP/1.1 404 Not found\r\n" +
                "Content-Length:" + responseTxt.getBytes().length + "\r\n"+
                "Connection: close\r\n" +
                "\r\n" +
                responseTxt;
        clientSocket.getOutputStream().write(response.getBytes());
    }

    //If thing is ok it will give us 200 OK
    private void okResponse(Socket clientSocket, String responseTxt, String contentType) throws IOException {

        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Length:" + responseTxt.length() + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                responseTxt;
        clientSocket.getOutputStream().write(response.getBytes());
    }

    //These are all the setters and getters, and add for the list.
    public void setRoot(Path rootDirectory){
        this.rootDirectory = rootDirectory;
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void setAnswerDao(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    public void setOptionDao(OptionDao optionDao){
        this.optionDao = optionDao;
    }

    public void addController(String path, HttpController controller) {
        controllers.put(path, controller);
    }

    public void addQueryControllers(String path, HttpControllerQuery controller) {
        queryControllers.put(path, controller);
    }

}
