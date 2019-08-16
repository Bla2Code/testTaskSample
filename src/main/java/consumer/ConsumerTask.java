package consumer;

import dbconnect.DataBase;

import java.io.IOException;
import java.sql.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ConsumerTask implements Runnable {

    private final String sql;
    private Connection connection;
    private Statement statement;
    private final DataBase.Connect connect;
    private final int period = 2_000;

    public ConsumerTask() {
        sql = "SELECT * FROM storage WHERE status = " +
                "'WAITING' ORDER BY id ASC LIMIT 1;";
        DataBase dataBase = new DataBase();
        connect = dataBase.new Connect();
        try {
            connection = connect.connection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Consumer Start ... \n");

        try {
            do {
                sendRequest();
                Thread.currentThread().sleep(period);
            } while (!Thread.currentThread().isInterrupted());
            connect.disconnect(connection);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("%s finished... \n", Thread.currentThread().getName());
    }

    protected synchronized void sendRequest() throws SQLException {
        ResultSet resultSet = connect.pull(statement, sql);

        System.out.println(sql);

        int id = getIdRequest(resultSet);
        if (id == 0) return;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String updateSql2 = "UPDATE storage SET status = 'READY'," +
                " updated = '" + timestamp + "' WHERE id = " + id + ";";
        int result = connect.push(statement, updateSql2);

        System.out.println(updateSql2);

        if (result != 0) EmailAggregator.sendEmail(id);
        System.out.printf("Added %d rows\n\n", result);
    }

    protected int getIdRequest(ResultSet resultSet) throws SQLException {
        int id = 0;
        while (resultSet.next()) {
            id = resultSet.getInt("id");
            System.out.println(id);
            return id;
        }
        return id;
    }

    public static class EmailAggregator {

        private final static Logger logger = Logger.getLogger("Result");

        public static void sendEmail(int id) {
            System.out.println("Message number " + id + " sent");
            try {
                FileHandler fileHandler = new FileHandler("src/logs/result.log");
                logger.addHandler(fileHandler);
                SimpleFormatter formatter = new SimpleFormatter();
                fileHandler.setFormatter(formatter);

                logger.info("Message number " + id + " sent");

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}