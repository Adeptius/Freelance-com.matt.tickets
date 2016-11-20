import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author adeptius
 */
public class Utilites {

    private static final Random randomTicket = new Random();

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
}
