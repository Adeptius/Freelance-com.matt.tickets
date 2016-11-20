import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.concurrent.TimeUnit;

public class DataAcsessObj {

    private static String dbUrl = "jdbc:mysql://127.0.0.1:3306/lotto?autoReconnect=true&useSSL=false";  //192.168.56.101
    private static String dbUser = "root";
    private static String dbPass = "357159";
    private ComboPooledDataSource cpds;

    private void init() throws PropertyVetoException {
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl(dbUrl);
        cpds.setUser(dbUser);
        cpds.setPassword(dbPass);
        cpds.setMinPoolSize(10);
        cpds.setMaxPoolSize(10);
        cpds.setAcquireIncrement(0);
    }

    public HashSet<String> getHashSetFromDB() throws SQLException, PropertyVetoException {
        init();
        long t0 = System.nanoTime();
        Connection connection = cpds.getConnection();
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
