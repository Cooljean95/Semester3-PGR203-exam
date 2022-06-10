package no.kristiania.daos;

import no.kristiania.object.Questions;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionDao {

    private final DataSource dataSource;
    private String sql;

    public QuestionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //Saving the questions to the database
    public void save(Questions questions) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            sql = "insert into question ( Title, Text, LowL, HighL) VALUES (?, ?, ?, ?)";

            try(PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
                statement.setString(1, questions.getTitle());
                statement.setString(2, questions.getText());
                statement.setString(3, questions.getLowL());
                statement.setString(4, questions.getHighL());
                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                questions.setIdQuestion(generatedKeys.getLong("Id_question"));
            }
        }
    }

    //This allows you to edit your question
    public void edit(Questions questions) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("UPDATE question SET (title, text) = (?, ?) WHERE id_question = ?")) {
                statement.setString(1, questions.getTitle());
                statement.setString(2, questions.getText());
                statement.setLong(3, questions.getIdQuestion());
                statement.executeUpdate();
            }
        }
    }

    //Retrieve question id
    public Questions retrieve(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("select * from question where id_question = ?")) {
                statement.setLong(1, id);
                Questions questions = getQuestions(statement);
                if (questions != null) return questions;
            }
        }
        return null;
    }


    //retrieve question title
    public Questions retrieveWithString(String title) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("select * from question where title = ?")) {
                statement.setString(1, title);
                Questions questions = getQuestions(statement);
                if (questions != null) return questions;
            }
        }
        return null;
    }

    // A method that is activated by two other methods. it puts the information in the Question object.
    private Questions getQuestions(PreparedStatement statement) throws SQLException {
        ResultSet rs = statement.executeQuery();

        if (rs.next()){
            Questions questions = new Questions();
            questions.setIdQuestion(rs.getLong("id_question"));
            questions.setTitle(rs.getString("Title"));
            questions.setText(rs.getString("Text"));
            questions.setLowL(rs.getString("LowL"));
            questions.setHighL(rs.getString("HighL"));
            return questions;
        }
        return null;
    }

    //Emptying table each time when you run test
    public void deleteTable() throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE question")){
                statement.executeUpdate();
            }
        }
    }

    //Listing all the questions in the database to the web
    public ArrayList<String> listAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("select * FROM question")) {
                try (ResultSet rs = statement.executeQuery()){
                    ArrayList<String> listAll = new ArrayList();

                    while(rs.next()){
                        listAll.add("[ ID: ");
                        listAll.add(String.valueOf(rs.getInt("id_question")));
                        listAll.add("Title: ");
                        listAll.add(rs.getString("title"));
                        listAll.add("Text: ");
                        listAll.add(rs.getString("text"));
                        listAll.add("Low label: ");
                        listAll.add(rs.getString("lowl"));
                        listAll.add("High label: ");
                        listAll.add(rs.getString("highl"));
                        listAll.add(" ]");
                    }
                    return listAll;
                }
            }
        }
    }

    //This list all the question's info into a Arraylist
    public ArrayList<String> listAllQuestions(long id, ArrayList<String> listAll) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("select * FROM question where id_question = ?")) {
                statement.setLong(1, id);
                try (ResultSet rs = statement.executeQuery()){
                    while(rs.next()){
                        listAll.add(String.valueOf(rs.getInt("id_question")));
                        listAll.add(rs.getString("title"));
                        listAll.add(rs.getString("text"));
                        listAll.add(rs.getString("lowl"));
                        listAll.add(rs.getString("highl"));
                    }
                    return listAll;
                }
            }
        }
    }

    //This only retrieves the question, for the dropdown menus.
    public ArrayList<String> retrieveOptions() throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("select * from question order by id_question")) {
                ResultSet rs = statement.executeQuery();
                ArrayList<String> questions = new ArrayList<>();

                while (rs.next()){
                    questions.add(rs.getString("Text"));
                }
                return questions;
            }
        }
    }
}
