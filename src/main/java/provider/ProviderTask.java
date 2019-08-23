package provider;

import dbconnect.DataBase;
import java.sql.*;
import java.util.Random;

public class ProviderTask implements Runnable {

    private Connection connection;
    private Statement statement;
    private final DataBase.Connect connect;
    private Random random = new Random();

    public ProviderTask() {
        DataBase dataBase = new DataBase();
        connect = dataBase.new Connect();
        try {
            connection = connect.connection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        final int max = 5_000;
        final int min = 2_000;
        try {
            do {
                boolean answer = sendRequest(new TaskList());
                if (!answer) break;
                int rand = random.nextInt(max) + min;
                Thread.currentThread().sleep(rand);
            } while (!Thread.currentThread().isInterrupted());
            connect.disconnect(connection);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected synchronized boolean sendRequest(TaskList taskList) throws SQLException {
        boolean result = false;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sql = "INSERT INTO storage(name, email, status, created, updated) " +
                "VALUES ('" + taskList.getName() + "', '" + taskList.getEmail() + "'," +
                " 'WAITING', '" + timestamp + "', '" + timestamp + "');";
        int answer = connect.push(statement, sql);
        if (answer == 1) result = true;
        return result;
    }

}