package dbconnect;

import java.sql.*;

public class DataBase {
    private String url;
    private String login;
    private String password;

    public DataBase() {
        this.url = "jdbc:postgresql://localhost:5432/test";//"jdbc:postgresql://localhost:5434/db";
        this.login = "www-data";//"user";
        this.password = "111111";//"pass";
    }

    DataBase(String url, String user, String pass) {
        this.url = url;
        this.login = user;
        this.password = pass;
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