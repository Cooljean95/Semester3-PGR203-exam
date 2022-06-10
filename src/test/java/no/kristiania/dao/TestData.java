package no.kristiania.dao;

import no.kristiania.controllers.*;
import no.kristiania.daos.AnswerDao;
import no.kristiania.daos.OptionDao;
import no.kristiania.daos.QuestionDao;
import no.kristiania.http.HttpServer;
import no.kristiania.object.Option;
import no.kristiania.object.Questions;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;

public class TestData {

    //Static method that gives out a datasource to who ever calls on it.
    public static DataSource createDataSource(){
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:exam_db;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    //contains all the controllers and connection to the database
    public HttpServer  controllers(HttpServer serverOne){
        OptionDao optionDao = new OptionDao(createDataSource());
        AnswerDao answerDao = new AnswerDao(createDataSource());
        QuestionDao questionDao = new QuestionDao(createDataSource());
        serverOne.setQuestionDao(new QuestionDao(createDataSource()));
        serverOne.setAnswerDao(new AnswerDao(createDataSource()));
        serverOne.setOptionDao(new OptionDao(createDataSource()));
        serverOne.addQueryControllers("/api/editQuestion", new EditQuestionControllerQuery(questionDao));
        serverOne.addController("/api/questionOptions", new QuestionOptionsController(questionDao));
        serverOne.addController("/api/questions", new NewQuestionController(questionDao));
        serverOne.addQueryControllers("/api/alternativeAnswers", new AnswersControllerQuery(answerDao,optionDao));
        serverOne.addQueryControllers("/api/editOption", new EditOptionControllerQuery(optionDao));
        serverOne.addQueryControllers("/api/optionOptions", new OptionOptionControllerQuery(optionDao));
        serverOne.addQueryControllers("/api/addOption", new AddOptionControllerQuery(optionDao));
        serverOne.addController("/api/showOptions", new ShowOptionController(optionDao));

        return serverOne;
    }

    public static String pickOneString(String ... alternatives){
        return alternatives[new Random().nextInt(alternatives.length)];
    }

    //Method for picking one random title
    public static Questions randomQuestion(String title){
        Questions question = new Questions();
        if (Objects.equals(title, "Food")){
            question.setTitle("Food");
            question.setText(TestData.pickOneString("Everyone loves food", "What do you love to eat?", "Who in the world is best to cook?"));
            question.setLowL(TestData.pickOneString("Disagree", "Pizza", "Spain"));
            question.setHighL(TestData.pickOneString("Agree", "Pasta", "Italia"));

        } else if(Objects.equals(title, "Animals")){
            question.setTitle("Animals");
            question.setText(TestData.pickOneString("I love animals", "What is your favorite animal?", "Which animal are cutest?"));
            question.setLowL(TestData.pickOneString("Agree", "Dog", "Panda"));
            question.setHighL(TestData.pickOneString("Disagree", "Cat", "Puppy"));

        } else if(Objects.equals(title, "School")){
            question.setTitle("School");
            question.setText(TestData.pickOneString("What is your favorite subject?", "What subject is worst?", "Favorite teacher"));
            question.setLowL(TestData.pickOneString("Advanced Java", "Digital Technology", "Johannes"));
            question.setHighL(TestData.pickOneString("Introduction to programming", "Databases", "Rolando"));
        }
        return question;
    }

    //This method pick one random option
    public static Option randomOption(QuestionDao questionDao, String title) throws SQLException {
        Option options = new Option();
        long idQuestion = questionDao.retrieveWithString(title).getIdQuestion();
        if (title.equals( "Food")){
            options.setOption(TestData.pickOneString("Cake", "Beef", "Carrot"));
            options.setIdQuestion((int) idQuestion);
        } else if(title.equals("Animals")){
            options.setOption(TestData.pickOneString("Dog", "Cat", "Panda"));
            options.setIdQuestion((int) idQuestion);
        } else if(title.equals("School")) {
            options.setOption(TestData.pickOneString("Gym", "Teacher", "Student"));
            options.setIdQuestion((int) idQuestion);
        }
        return options;
    }
}
