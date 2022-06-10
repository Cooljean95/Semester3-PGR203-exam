package no.kristiania.daos;

import no.kristiania.object.Option;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class OptionDao {

    private static DataSource dataSource;
    private String sql;

    public OptionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //Saves the options to the database
    public void save(Option option) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            sql = "insert into option (Id_question, option) values (?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                statement.setLong(1,option.getIdQuestion());
                statement.setString(2, option.getOption());
                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                option.setIdOption(generatedKeys.getLong(1));
            }
        }
    }

    //Retrieving options in an arraylist
    public ArrayList<String> retrieveOptions() throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            String sql = "select * from option order by id_option";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet rs = statement.executeQuery();
                ArrayList<String> option = new ArrayList<>();

                while (rs.next()){
                    option.add(rs.getString("option"));
                }
                return option;
            }
        }
    }

    //Retrieving option id
    public Option retrieveId(int id) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("select * from option where id_option = ?")){
                statement.setInt(1, id);
                ResultSet rs = statement.executeQuery();

                if (rs.next()){
                    Option option = new Option();
                    option.setIdQuestion(rs.getInt("id_question"));
                    option.setIdOption(rs.getLong("id_option"));
                    option.setOption(rs.getString("option"));
                    return option;
                }
            }
        }
        return null;
    }

    //delete tables in test class
    public void deleteTable() throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE option")){
                statement.executeUpdate();
            }
        }
    }

    //Provide you to edit the option on the web
    public void edit(Option option) throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("UPDATE option SET option = ? WHERE id_option = ?")) {
                statement.setString(1, option.getOption());
                statement.setLong(2, option.getIdOption());
                statement.executeUpdate();
            }
        }
    }

    //List all options on the web
    public static ArrayList<String> listAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * FROM option")) {
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<String> listAll = new ArrayList();

                    while (rs.next()) {
                        listAll.add("[ ID_option: ");
                        listAll.add(String.valueOf(rs.getInt("id_option")));
                        listAll.add("ID_question: ");
                        listAll.add(String.valueOf(rs.getInt("id_question")));
                        listAll.add("option: ");
                        listAll.add(rs.getString("option"));
                        listAll.add(" ]");
                    }
                    return listAll;
                }
            }
        }
    }

    //Making an arraylist of questions and options
    public ArrayList<String> joinOptionAndQuestion() throws SQLException {
        try (Connection connection = dataSource.getConnection()){
           sql = "select * FROM question INNER JOIN option o on question.id_question = o.id_question";

            try (PreparedStatement statement = connection.prepareStatement(sql)){
                try (ResultSet rs = statement.executeQuery()){

                    ArrayList<String> option = new ArrayList<>();

                    while(rs.next()){
                        option.add("[ Question: ");
                        option.add(rs.getString("title"));
                        option.add(rs.getString("text"));
                        option.add("Option: ");
                        option.add(rs.getString("option"));
                        option.add(" ]");
                    }

                    return option;
                }
            }
        }
    }

}
