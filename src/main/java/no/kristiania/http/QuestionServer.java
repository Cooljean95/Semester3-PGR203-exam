package no.kristiania.http;

import no.kristiania.controllers.*;
import no.kristiania.daos.AnswerDao;
import no.kristiania.daos.OptionDao;
import no.kristiania.daos.QuestionDao;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class QuestionServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    //Starts the main server
    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer(85);
        httpServer.setRoot(Paths.get("./src/main/resources"));
        QuestionDao questionDao = new QuestionDao(createDataSource());
        AnswerDao answerDao = new AnswerDao(createDataSource());
        OptionDao optionDao = new OptionDao(createDataSource());
        httpServer.setQuestionDao(new QuestionDao(createDataSource()));
        httpServer.setAnswerDao(new AnswerDao(createDataSource()));
        httpServer.setOptionDao(new OptionDao(createDataSource()));
        httpServer.addQueryControllers("/api/editQuestion", new EditQuestionControllerQuery(questionDao));
        httpServer.addController("/api/questionOptions", new QuestionOptionsController(questionDao));
        httpServer.addController("/api/questions", new NewQuestionController(questionDao));
        httpServer.addQueryControllers("/api/alternativeAnswers", new AnswersControllerQuery(answerDao,optionDao));
        httpServer.addQueryControllers("/api/editOption", new EditOptionControllerQuery(optionDao));
        httpServer.addQueryControllers("/api/optionOptions", new OptionOptionControllerQuery(optionDao));
        httpServer.addQueryControllers("/api/addOption", new AddOptionControllerQuery(optionDao));
        httpServer.addController("/api/showOptions", new ShowOptionController(optionDao));
        logger.info("Starting http://localhost:85/index.html");
    }

    //Creating a properties file. Inside the properties file we wrote url, user and password
    private static DataSource createDataSource() throws IOException {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader("pgr203.properties")){
            properties.load(reader);
        }

        PGSimpleDataSource dataSource = new  PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("dataSource.url"));
        dataSource.setUser(properties.getProperty("dataSource.user"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }
}
