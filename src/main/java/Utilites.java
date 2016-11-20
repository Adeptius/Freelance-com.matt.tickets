import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author adeptius
 */
public class Utilites {

    private static final Random randomTicket = new Random();


    public static List<HashSet<String>> getMassiveOfStrings(HashSet<String> stringHashSet){
        List<HashSet<String>> massiveOfStrings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            HashSet<String> current = new LinkedHashSet<>();
            for (String s : stringHashSet) {
                if (i<10){
                    if (s.contains(String.valueOf("0"+i)))
                        current.add(s);
                }else {
                    if (s.contains(String.valueOf(i)))
                        current.add(s);
                }
            }
            massiveOfStrings.add(current);
        }
        return massiveOfStrings;
    }

    /**
     * This method cutting hash set to fife numbers.
     * So we can use method .contains to find very similiar tickets.
     * We dont use the mask "01-02-03-04-05.*" because it slow
     */
    public static HashSet<String> cutToFiveNumbers(HashSet<String> stringHashSet){
        HashSet<String> fiveNumbers = new LinkedHashSet<>();
        for (String s : stringHashSet) {
            fiveNumbers.add(s.substring(0, 14));
        }
        return fiveNumbers;
    }

    /**
     * @return HashMap with cutted three numbers as key and their quantity as value
     */
    public static HashMap<String, Integer> createMapForThreeNumbers(HashSet<String> stringHashSet){
        HashMap<String, Integer> map = new HashMap<>();
        String a;
        for (String s : stringHashSet) {
            a = s.substring(0, 8);
            if (map.containsKey(a)){
                map.put(a,map.get(a)+1);
            }else {
                map.put(a,1);
            }
        }
        return map;
    }

    /**
     * Just creating randomly numbers
     * @param count - count of numbers
     * @return numbers as list
     */
    public static List<String> createTickets(int count){
        long t0 = System.nanoTime();
        List<String> listOfTickets = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.delete(0, sb.length());
            sb.append(getRandomNumber());
            for (int j = 1; j < 6; j++) {
                sb.append("-").append(getRandomNumber());
            }
            listOfTickets.add(sb.toString());
        }
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1-t0);
        System.out.printf("Generating list of %d tickets takes: %d millis%n", count, millis);
        return listOfTickets;
    }

    public static String getRandomNumber(){
        int random = randomTicket.nextInt(53) + 1;
        if (random < 10) return String.valueOf("0"+random);
        return String.valueOf(random);
    }

    public static String[] getBytesFromString(String ticket){
        String[] str = new String[6];
        str[0] = ticket.substring(0, 2);
        str[1] = ticket.substring(3, 5);
        str[2] = ticket.substring(6, 8);
        str[3] = ticket.substring(9, 11);
        str[4] = ticket.substring(12, 14);
        str[5] = ticket.substring(15, 17);
        return str;
    }
}
