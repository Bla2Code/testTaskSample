package dbconnect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DataBase {
    private String url;
    private String login;
    private String password;

    public DataBase() {
        File file = new File("data.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String driver = properties.getProperty("driver");
        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String database = properties.getProperty("database");

        //this.url = "jdbc:postgresql://localhost:5434/db";
        this.url = driver + "://" + host + ":"  + port + "/" + database;
        this.login = properties.getProperty("login");
        this.password = properties.getProperty("password");
    }

    protected String getUrl() {
        return this.url;
    }
    protected String getLogin() {
        return this.login;
    }
    protected String getPassword() {
        return this.password;
    }

    public class Connect implements ruleConnect {

        @Override
        public Connection connection() throws SQLException {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection connection = DriverManager.getConnection(getUrl(), getLogin(), getPassword());
            return connection;
        }

        @Override
        public void disconnect(Connection connect) throws SQLException {
            connect.close();
        }

        @Override
        public int push(Statement statement, String sql) throws SQLException {
            int result = statement.executeUpdate(sql);
            return result;
        }

        @Override
        public ResultSet pull(Statement statement, String sql) throws SQLException {
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet;
        }
    }
}