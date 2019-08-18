package consumer;

import dbconnect.DataBase;
import java.sql.*;
import org.apache.log4j.Logger;

public class ConsumerTask implements Runnable {

    private final String sql;
    private Connection connection;
    private Statement statement;
    private final DataBase.Connect connect;
    private final int period = 10_000;

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

        int id = getIdRequest(resultSet);
        if (id == 0) return;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String updateSql2 = "UPDATE storage SET status = 'READY'," +
                " updated = '" + timestamp + "' WHERE id = " + id + ";";
        int result = connect.push(statement, updateSql2);
        if (result == 1) EmailAggregator.sendEmail(id);
    }

    protected int getIdRequest(ResultSet resultSet) throws SQLException {
        int id = 0;
        while (resultSet.next()) {
            id = resultSet.getInt("id");
            return id;
        }
        return id;
    }

    public static class EmailAggregator {

        private static final Logger log = Logger.getLogger("EmailEmulator");

        public static void sendEmail(int id) {
            log.info("Message number " + id + " sent");
            System.out.printf("Message number " + id + " sent\n");
        }

    }

}