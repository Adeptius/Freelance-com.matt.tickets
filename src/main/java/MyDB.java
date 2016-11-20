/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author math305
 */
public final class MyDB {
    private static Connection myConn;
    private static String dbUrl = "jdbc:mysql://127.0.0.1:3306/lotto?autoReconnect=true&useSSL=false";  //192.168.56.101
    private static String dbUser = "root";
    private static String dbPass = "357159";
    private static MyDB instance;


    public static MyDB getInstance() {
        return instance;
    }

    private MyDB() throws PropertyVetoException {
        try {
            myConn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static {
        try {
            instance = new MyDB();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }


    public void checkTicketDB() {
        String sql1 = "select ticket_id, pick1, pick2, pick3, pick4, pick5,pick6 from ticket";
        String sql2 = "select l.ticket_id as id ,  ticket_number as total from ticket l where l.ticket_id  IN (select ticket_id from ticket where ((ticket_number like ? ESCAPE '!') + (ticket_number like ? ESCAPE '!') + (ticket_number like ? ESCAPE '!') + (ticket_number like ? ESCAPE '!') + (ticket_number like ? ESCAPE '!') + (ticket_number like ? ESCAPE '!') > 3 ))";
        String sql = "select f.ticket_id as le_ticket, count(m.ticket_id) as total_ticket from ticket f, ticket m where f.ticket_id != m.ticket_id "
                + " and m.ticket_number like ? ESCAPE '!' and m.ticket_number like ? ESCAPE '!' and m.ticket_number like ? ESCAPE '!' "
                + " and m.ticket_number like ? ESCAPE '!' group by f.ticket_id";
        try {
            Statement stmt1 = myConn.createStatement();
            ResultSet myRes2 = stmt1.executeQuery(sql1);
            int ticket_id = 0, pick1 = 0, pick2 = 0, pick3 = 0, pick4 = 0, pick5 = 0, pick6 = 0;
            while (myRes2.next()) {
                ticket_id = myRes2.getInt("ticket_id");
                pick1 = myRes2.getInt("pick1");
                pick2 = myRes2.getInt("pick2");
                pick3 = myRes2.getInt("pick3");
                pick4 = myRes2.getInt("pick4");
                pick5 = myRes2.getInt("pick5");
                pick6 = myRes2.getInt("pick6");


                //create a statement
                PreparedStatement myStatement = myConn.prepareStatement(sql2);
                myStatement.setString(1, "%" + String.format("%02d", pick1) + "%");
                myStatement.setString(2, "%" + String.format("%02d", pick2) + "%");
                myStatement.setString(3, "%" + String.format("%02d", pick3) + "%");
                myStatement.setString(4, "%" + String.format("%02d", pick4) + "%");
                myStatement.setString(5, "%" + String.format("%02d", pick5) + "%");
                myStatement.setString(6, "%" + String.format("%02d", pick6) + "%");
                //preparedStatement.setString(i, "%"+ String.format("%02d", randomTicket[i-1])+"%");

                ResultSet myRes1 = myStatement.executeQuery();
                int total = 0;
                System.out.println("-----------> " + ticket_id);
                int counter = 0;
                while (myRes1.next()) {
                    counter++;
                    if (counter > 6) {
                        System.out.println(" Ticket_id : " + myRes1.getInt("id") + " total : " + myRes1.getString("total"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int isTicketsAllowed(String str) {
        int response = -1;
        try {
            CallableStatement cStmt = myConn.prepareCall("call CheckTicketWrapper(?)");
            cStmt.setString(1, str);
            cStmt.execute();
            ResultSet rs = cStmt.getResultSet();

            if (rs.next()) {
                response = rs.getInt("v_IsValid");
            }
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public String checkPureTicketInDB(String ticket) {
        try {
            if (isTicketsAllowed(ticket) < 1) {
                ticket = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ticket = null;
        }
        return ticket;
    }

    public ArrayList<String> checkPureTicketsInDB(ArrayList<String> tickets) {
        ArrayList<String> pureTickets = new ArrayList<String>();

        try {
            CallableStatement cStmt = myConn.prepareCall("call CheckTicketWrapper(?)");
            for (int i = 0; i < tickets.size(); i++) {
                cStmt.setString(1, tickets.get(i));
                cStmt.execute();
                ResultSet rs = cStmt.getResultSet();

                if (rs.next()) {
                    if (rs.getInt("v_IsValid") == 1) {
                        pureTickets.add(tickets.get(i));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pureTickets;
    }

    public String checkTicketDB(int[] randomTicket) {

        int total4out6 = 0, total5out6 = 0;
        String ticketString = null;
        ticketString = transformIntArrayToString(randomTicket);
        try {
            //create a statement
            // prepareQuery(myConn, randomTicket).executeQuery();
            //execute SQL query
            ResultSet myRes = prepareQuery(myConn, randomTicket).executeQuery();

            //process the result set
            while (myRes.next()) {
                total4out6 = myRes.getInt("total4out6");
                total5out6 = myRes.getInt("total5out6");
            }

            if ((total4out6 > 5) || (total5out6 > 0)) {
                ticketString = null;
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticketString;
    }

    private PreparedStatement prepareQuery(Connection connection, int[] randomTicket) {
        String str = " select (select count(*) from ticket where (ticket_number like ? ESCAPE '!')\n" +
                "+ (ticket_number like ? ESCAPE '!')\n" +
                "+ (ticket_number like ? ESCAPE '!')\n" +
                "+ (ticket_number like ? ESCAPE '!')\n" +
                "+ (ticket_number like ? ESCAPE '!')\n" +
                "+ (ticket_number like ? ESCAPE '!') > 3) as total4out6,\n" +
                "(select count(*) from ticket where (ticket_number like ? ESCAPE '!')\n" +
                "+ (ticket_number like ? ESCAPE '!')\n" +
                "+ (ticket_number like ? ESCAPE '!')\n" +
                "+ (ticket_number like ? ESCAPE '!')\n" +
                "+ (ticket_number like ? ESCAPE '!')\n" +
                "+ (ticket_number like ? ESCAPE '!') > 4) as total5out6, count(*) as c from ticket";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = myConn.prepareStatement(str);
            for (int i = 1; i <= randomTicket.length; i++) {
                preparedStatement.setString(i, "%" + String.format("%02d", randomTicket[i - 1]) + "%");
                preparedStatement.setString(i + 6, "%" + String.format("%02d", randomTicket[i - 1]) + "%");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    private String transformIntArrayToString(int[] ticketas) {
        return String.format("%02d-%02d-%02d-%02d-%02d-%02d", ticketas[0], ticketas[1], ticketas[2], ticketas[3], ticketas[4], ticketas[5]);
    }
}
