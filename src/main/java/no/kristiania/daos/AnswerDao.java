package no.kristiania.daos;

import no.kristiania.object.Answer;
import no.kristiania.object.Questions;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AnswerDao {

    private final DataSource dataSource;
    public AnswerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //Saves the answer to questions in the database
   public void save(Answer answer) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            String sql = "insert into answer (Id_question, answer) values (?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                statement.setInt(1, answer.getQuestionId());
                statement.setString(2, answer.getAnswer());
                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                answer.setIdQuestion(generatedKeys.getLong(1));
            }
        }
   }
    //Retrieving answers in an arraylist
    public ArrayList<String> retrieveAnswer() throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("select * from answer")) {
                ResultSet rs = statement.executeQuery();
                ArrayList<String> answer = new ArrayList<>();

                while (rs.next()){
                    answer.add(rs.getString("answer"));
                }
                return answer;
            }
        }
    }

//for deleting table in the testclass
    public void deleteTable() throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE answer")){
                statement.executeUpdate();
            }
        }
    }

    //This should retrieve answer
    public Answer retrieve(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("select * from answer where id_question = ?")) {
                statement.setLong(1, id);
                ResultSet rs = statement.executeQuery();

                if (rs.next()){
                    Answer answer = new Answer();
                    answer.setIdQuestion(rs.getLong(1));
                    answer.setQuestionId(rs.getInt(2));
                    answer.setAnswer(rs.getString(3));
                    return answer;
                }
            }
        }
        return null;
    }

//listing all the answers to the web
    public HashMap<ArrayList, ArrayList> listAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("select * FROM question INNER JOIN answer on question.id_question = answer.id_question")) {
               try (ResultSet rs = statement.executeQuery()){

                   HashMap<ArrayList, ArrayList> listAll = new HashMap<>();

                   ArrayList<Questions> questions1 = new ArrayList();
                   ArrayList<Answer> answers = new ArrayList<>();


                   while(rs.next()){
                       Questions questions = new Questions();
                       Answer answer = new Answer();

                       questions.setIdQuestion((long) rs.getInt("id_question"));
                       questions.setTitle(rs.getString("title"));
                       questions.setText(rs.getString("text"));
                       questions.setLowL(rs.getString("lowl"));
                       questions.setHighL(rs.getString("highl"));
                       questions1.add(questions);

                       answer.setIdQuestion((long)(rs.getInt("id_answer")));
                       answer.setQuestionId(rs.getInt("id_question"));
                       answer.setAnswer(rs.getString("answer"));
                       answers.add(answer);
                    }

                   listAll.put(questions1, answers);
                   return listAll;
                }
            }
        }
    }
}
