package provider;

import dbconnect.DataBase;
import java.sql.*;
import java.util.Random;
import java.util.concurrent.*;

public class ProviderTask implements Runnable {

    private Connection connection;
    private Statement statement;
    private final DataBase.Connect connect;
    private final int max = 5_000;
    private final int min = 2_000;
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
        System.out.println("Provider Start ... \n");

        try {
            do {
                sendRequest();
                int rand = random.nextInt(max) + min;
                Thread.currentThread().sleep(rand);
            } while (!Thread.currentThread().isInterrupted());
            connect.disconnect(connection);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("%s finished... \n", Thread.currentThread().getName());
    }

    protected synchronized void sendRequest() throws SQLException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sql = "INSERT INTO storage(name, status, created, updated) " +
                "VALUES ('task', 'WAITING', '" + timestamp + "', '" + timestamp + "');";
        int result = connect.push(statement, sql);

        System.out.println(sql);
        System.out.printf("Added %d rows\n\n", result);
    }

}