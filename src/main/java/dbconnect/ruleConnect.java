package dbconnect;

import java.sql.*;

public interface ruleConnect {
    /**
     * Establishes a connection to the database
     * @return connection Connection
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    Connection connection() throws SQLException, ClassNotFoundException;

    /**
     * Disconnects from database
     * @param connect Connection
     * @throws SQLException
     */
    void disconnect(Connection connect) throws SQLException;

    /**
     * Creates a task
     * @param statement Statement
     * @param sql String
     * @return result int
     * @throws SQLException
     */
    int push(Statement statement, String sql) throws SQLException;

    /**
     * Gets the task
     * @param statement Statement
     * @param sql String
     * @return resultSet ResultSet
     * @throws SQLException
     */
    ResultSet pull(Statement statement, String sql) throws SQLException;
}