import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author adeptius
 */
@SuppressWarnings("Duplicates")
public class Validator {

    private static DataAcsessObj acsessObj;
    private List<HashSet<String>> massiveOfStrings;
    private HashSet<String> noCuttedHashSet;

    public static void main(String[] args) throws PropertyVetoException, SQLException {
        Validator validator = new Validator();
        acsessObj = new DataAcsessObj();
        validator.prepare();
        validator.test();
    }

    private void prepare() throws SQLException, PropertyVetoException {
        noCuttedHashSet = acsessObj.getHashSetFromDB();
        long t0 = System.nanoTime();
        massiveOfStrings = Utilites.getMassiveOfStrings(noCuttedHashSet);
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.printf("Preparing takes: %d millis%n", millis);
    }

    private void test() throws SQLException {
        List<String> listOfTickets = Utilites.createTickets(50000);
        separateByThreads(listOfTickets,4);
    }

    private void separateByThreads(List<String> tickets, int numberOfThreads){
        List<List<String>> splitedList = Utilites.splitList(tickets, numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            final int a = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    validateListOfTickets(splitedList.get(a));
                }
            }).start();
        }
    }

    private List<String> validateListOfTickets(List<String> tickets) {
        List<String> validTickets = new ArrayList<>();
        for (String ticket : tickets) {
            if (isValidTicket(ticket)) {
                validTickets.add(ticket);
                System.out.println(ticket);
            }
        }
        return validTickets;
    }

    private boolean isValidTicket(String ticket) {
        if (!isTicketMachesWithPattern(ticket)) return false;
        if (!isTickerHaveValidNumbers(ticket)) return false;
        if (isFiveMachesDigit(ticket)) return false;
        if (!isNoManyOfThreeDigits(ticket))return false;
        return true;
    }


    private boolean isTicketMachesWithPattern(String ticket) {
        return ticket.matches("\\d\\d-\\d\\d-\\d\\d-\\d\\d-\\d\\d-\\d\\d");
    }

    private boolean isTickerHaveValidNumbers(String ticket) {
        if (ticket.contains("00")) return false;
        byte one = Byte.valueOf(ticket.substring(0, 2));
        byte two = Byte.valueOf(ticket.substring(3, 5));
        byte tree = Byte.valueOf(ticket.substring(6, 8));
        byte four = Byte.valueOf(ticket.substring(9, 11));
        byte five = Byte.valueOf(ticket.substring(12, 14));
        byte six = Byte.valueOf(ticket.substring(15, 17));
        if (one < two && two < tree && tree < four && four < five && five < six) return true;
        return false;
    }


    private boolean isFiveMachesDigit(String ticket) {
        int count;
        String[] str = Utilites.getMassiveFromString(ticket);
        HashSet<String> hash = massiveOfStrings.get(Integer.valueOf(str[0]));
        count = 1;
        for (String s : hash) {
            for (int i = 1; i < 6; i++) {
                if (s.contains(String.valueOf(str[i]))) {
                    count++;
                    if (count == 5) {
                        return true;
                    }
                }
            }
            count = 1;
        }

        hash = massiveOfStrings.get(Integer.valueOf(str[5]));
        count = 1;
        for (String s : hash) {
            for (int i = 4; i >= 0; i--) {
                if (s.contains(String.valueOf(str[i]))) {
                    count++;
                    if (count == 5) {
                        return true;
                    }
                }
            }
            count = 1;
        }
        return false;
    }

    public boolean isNoManyOfThreeDigits(String ticket) {
        String[] str = Utilites.getMassiveFromString(ticket);
        int count = 0;
        for (String s : noCuttedHashSet) {
            int tempCount = 0;
            for (int i = 0; i < 6; i++) {
                if (s.contains(str[i])) {
                    tempCount++;
                    if (tempCount>3){
                        count++;
                        if (count == 6)
                            return false;
                        break;
                    }
                }
            }
        }
        return true;
    }
}
