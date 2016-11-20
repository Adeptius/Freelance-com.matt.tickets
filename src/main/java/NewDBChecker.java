import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class NewDBChecker {

    private static DataAcsessObj acsessObj;
    private HashSet<String>  cuttedToFiveNumbers;
    private HashMap<String, Integer> MapWithThreeNumbers;

    public static void main(String[] args) throws PropertyVetoException, SQLException {
        NewDBChecker newDBChecker = new NewDBChecker();
        newDBChecker.acsessObj = new DataAcsessObj();
        newDBChecker.prepare();
        newDBChecker.test();
    }

    private void prepare() throws SQLException, PropertyVetoException {
        HashSet<String>  noCuttedHashSet = acsessObj.getHashSetFromDB();
        long t0 = System.nanoTime();
        cuttedToFiveNumbers = Utilites.cutToFiveNumbers(noCuttedHashSet);
        MapWithThreeNumbers = Utilites.createMapForThreeNumbers(noCuttedHashSet);
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1-t0);
        System.out.printf("Preparing takes: %d millis%n", millis);
    }


    private void test() throws SQLException {
        List<String> listOfTickets = Utilites.createTickets(50000);
        //listOfTickets.forEach(System.out::println);
        List<String> validTickets = new ArrayList<>();
        long t0 = System.nanoTime();
        for (String listOfTicket : listOfTickets) {
            if (testIsValidTicket(listOfTicket))
                System.out.println(listOfTicket);
        }





        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1-t0);
        System.out.printf("Testing time: %d millis%n", millis);
    }

    private boolean testIsValidTicket(String ticket){
        if (!isTicketMachesWithPattern(ticket)) {
            //System.out.printf("%s not maches by pattern%n", ticket);
            return false;
        }
        if (!isTickerHaveValidNumbers(ticket)){
            //System.out.printf("%s has invalid numbers%n", ticket);
            return false;
        }
        if (isTicketMachesGreaterThanFourDigit(ticket)){
            //System.out.printf("%s has matches greater than 4 digits%n", ticket);
            return false;
        }
        int similiarTicketsCount = howManyMatchesByThreeDigits(ticket);
        if (similiarTicketsCount > 5){
            //System.out.printf("%s has matches 3 digits %d times%n", ticket, similiarTicketsCount);
            return false;
        }
        return true;
    }

    private boolean isTicketMachesWithPattern(String ticket){
        if (ticket.matches("\\d\\d-\\d\\d-\\d\\d-\\d\\d-\\d\\d-\\d\\d")) return true;
        return false;
    }

    private boolean isTickerHaveValidNumbers(String ticket){
        if (ticket.contains("00")) return false;
        byte one = Byte.valueOf(ticket.substring(0,2));
        byte two = Byte.valueOf(ticket.substring(3,5));
        byte tree = Byte.valueOf(ticket.substring(6,8));
        byte four = Byte.valueOf(ticket.substring(9,11));
        byte five = Byte.valueOf(ticket.substring(12,14));
        byte six = Byte.valueOf(ticket.substring(15,17));
        if (one<two && two<tree && tree<four && four<five && five<six) return true;
        return false;
    }

    private boolean isTicketMachesGreaterThanFourDigit(String ticket){
        return cuttedToFiveNumbers.contains(ticket.substring(0,14));
    }

    private int howManyMatchesByThreeDigits(String ticket){
        if (!MapWithThreeNumbers.containsKey(ticket.substring(0, 8))){
            return 0;
        }
        return MapWithThreeNumbers.get(ticket.substring(0, 8));
    }

}
