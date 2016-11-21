
import java.sql.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.concurrent.TimeUnit;

/**
 * @author adeptius
 */
public class DataAcsessObj {

    private static Connection connection;

    private void init() throws SQLException {
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/lotto?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPass = "357159";
        connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
    }

    public HashSet<String> getHashSetFromDB() throws SQLException {
        init();
        long t0 = System.nanoTime();
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("select ticket_number from ticket");
        HashSet<String> strings = new LinkedHashSet<>();
        while (set.next()){
            strings.add(set.getString("ticket_number"));
        }
        connection.close();

        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1-t0);
        System.out.printf("Hash have %d tickets%n", strings.size());
        System.out.printf("Loading from DB takes: %d millis%n", millis);
        return strings;
    }
}
