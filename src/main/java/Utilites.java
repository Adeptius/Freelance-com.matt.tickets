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
            int[] ints = getRandomNumber();
            sb.append(ints[0]);
            for (int j = 1; j < 6; j++) {
                sb.append("-").append(ints[j]);
            }
            listOfTickets.add(sb.toString());
        }
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1-t0);
        System.out.printf("Generating list of %d tickets takes: %d millis%n", count, millis);
        return listOfTickets;
    }

    public static int[] getRandomNumber(){
        int[] ints = new int[6];
        for (int i = 0; i < 6; i++) {
            ints[i] = randomTicket.nextInt(53) + 1;
        }
        Arrays.sort(ints);
        return ints;
    }

    public static String[] getMassiveFromString(String ticket){
        String[] str = new String[6];
        str[0] = ticket.substring(0, 2);
        str[1] = ticket.substring(3, 5);
        str[2] = ticket.substring(6, 8);
        str[3] = ticket.substring(9, 11);
        str[4] = ticket.substring(12, 14);
        str[5] = ticket.substring(15, 17);
        return str;
    }

    public static List<List<String>> splitList(List<String> originalList, int numberOfLists) {
        int partitionSize = 1+ (originalList.size() / numberOfLists);
        List<List<String>> partitions = new LinkedList<>();
        for (int i = 0; i < originalList.size(); i += partitionSize) {
            partitions.add(originalList.subList(i, Math.min(i + partitionSize, originalList.size())));
        }
        return partitions;
    }
}