/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author scifct
 */
public class Pure {

    private Scanner input;
    int[] pickedTicket = new int[6];
    int SENTINEL = -999;
    boolean isAllLess3 = true;

    public void openFile1() {
        try {

            //file from android device by user is "newUser.txt"
            //file from All the ticket is "globalticket.txt
            input = new Scanner(new File("globalticket.txt"));//will change to local ticket after I finish 
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Error opening the file");
            System.exit(1);
        }
    }

    private ArrayList<String> getNewTicket(List<Integer> myIntList) {
        int counter = 0;
        ArrayList<String> arr = new ArrayList<>();
        //check all the numbers
        for (int numb = 1; numb <= 53; numb++) {
            //for each number check If it existed 
            for (int i = 0; i < myIntList.size(); i++) {
                if (numb != myIntList.get(i)) {
                    counter++;
                    if (counter == myIntList.size()) {
                        myIntList.add(numb);
                        arr.add(transformIntArryToString(myIntList));
                        myIntList.remove(myIntList.size() - 1);
                        counter = 0;
                        System.out.println("" + transformIntArryToString(myIntList));
                    }
                }
            }
        }
        return arr;
    }

    private ArrayList<String> getNewTicket2(List<Integer> myIntList) {
        int counter = 0;
        ArrayList<String> arr = new ArrayList<>();
        //check all the numbers
        for (int numb = 1; numb <= 53; numb++) {
            for (int numb2 = 1; numb2 <= 53; numb2++) {
                System.out.println("hello");
                counter = 0;
                //for each number check If it existed 
                for (int i = 0; i < myIntList.size(); i++) {
                    if (numb != myIntList.get(i) && numb2 != myIntList.get(i)) {
                        counter++;
                        if (counter == myIntList.size() && numb != numb2) {
                            myIntList.add(numb);
                            myIntList.add(numb2);
                            arr.add(transformIntArryToString(myIntList));
                            myIntList.remove(myIntList.size() - 1);
                            myIntList.remove(myIntList.size() - 1);
                            counter = 0;

                            System.out.println("" + transformIntArryToString(myIntList));
                        }//end of if
                    }
                }
            }
        }
        return arr;
    }

    public void getPureNumbers(String str) {
        List<Integer> ints = convertStringToIntArr(str);
        ArrayList<String> listStr = new ArrayList<>();
        if (ints.size() == 5) {
            listStr = getNewTicket(ints);
        } else if (ints.size() == 4) {
            listStr = getNewTicket2(ints);
        }
        MyDB db = MyDB.getInstance();
        System.out.println(listStr.size() + " : " + System.currentTimeMillis());
        String oneTicket = null;
        
        /* Option 2 - Do it in group takes 116 seconds 
        ArrayList<String> tickets = db.checkPureTicketsInDB(listStr);
        for(int i =0; i < tickets.size(); i++){
            List<Integer> integers =convertStringToIntArr(tickets.get(i));
            Collections.sort(integers);
            System.out.println(" "+checkTicket(transformIntArryToString(integers)));
        }*/
        System.out.println(listStr.size() + " : " + System.currentTimeMillis());
        for (int i = 0; i < listStr.size(); i++) {
            oneTicket = db.checkPureTicketInDB(listStr.get(i));
            if (oneTicket != null) {
                List<Integer> integers = convertStringToIntArr(oneTicket);
                Collections.sort(integers);
                System.out.println(" " + checkTicket(transformIntArryToString(integers)));
            }
        }


        System.out.println(listStr.size() + " : " + System.currentTimeMillis());
    }

    public void getUniqueNumberFromArray1(int[] myintArray) {
        boolean status = true;
        int[] myFullArray = new int[6];
        int remainingIndex = 6 - myintArray.length;
        String mTicket = "";
        if (remainingIndex == 1) {
            for (int number1 = 1; number1 <= 53; number1++) {
                status = true;
                for (int i = 0; i < myintArray.length; i++) {
                    if (number1 == myintArray[i]) {
                        status = false;
                    }
                    myFullArray[i] = myintArray[i];
                }
                if (status == true) {
                    myFullArray[5] = number1;
                    Arrays.sort(myFullArray);
                    if (myFullArray[0] < 20) {
                        mTicket = transformIntArrayToString(myFullArray);
                        //System.out.printf(" %s \n", String.format("%05d", numb));
                        status = canSaveAgainsttest1(mTicket);

                        if (status == true) {
                            System.out.printf("%s \n", mTicket);
                        }
                    }


                }
            }
        }
    }

    public void getUniqueNumberFromArray(int[] myintArray) {
        boolean status = true;
        int[] myFullArray = new int[6];
        int remainingIndex = 6 - myintArray.length;
        String mTicket = "";
        if (remainingIndex == 1) {
            for (int number1 = 1; number1 <= 53; number1++) {
                status = true;
                for (int i = 0; i < myintArray.length; i++) {
                    if (number1 == myintArray[i]) {
                        status = false;
                    }
                    myFullArray[i] = myintArray[i];
                }
                if (status == true) {
                    myFullArray[5] = number1;
                    Arrays.sort(myFullArray);
                    if (myFullArray[0] < 20) {
                        mTicket = transformIntArrayToString(myFullArray);
                        //System.out.printf(" %s \n", String.format("%05d", numb));
                        status = canSaveAgainsttest1(mTicket);

                        if (status == true) {
                            System.out.printf("%s \n", mTicket);
                        }
                    }


                }
            }
        }
    }

    private boolean canSaveAgainsttest2(String likelyTicket) {
        //System.out.println(" ;"+likelyTicket);
        int ticket_id = 0;
        String localTicket = "";
        int[] localTicket_intArr = new int[6];
        int[] likelyTicket_intArr = new int[6];
        String localfTicket;
        boolean status = true;
        int counter = 0;
        Scanner newTirageInput = null;

        try {
            //mynewtirage.txt
            newTirageInput = new Scanner(new File("globalticket.txt"));
        } catch (FileNotFoundException fnfe) {
            System.err.println("error opening the file " + fnfe.getMessage());
            System.exit(1);
        } catch (SecurityException se) {
            System.err.println("error not permission " + se.getMessage());
            System.exit(1);
        }

        while (newTirageInput.hasNext()) {
            //get Ticket from the internal file that already has the ticket
            ticket_id = newTirageInput.nextInt();
            localTicket = newTirageInput.next();


            //convert files to int array
            localTicket_intArr = transformStringToInt(localTicket.trim());
            likelyTicket_intArr = transformStringToInt(likelyTicket.trim());
            int similarNumbers = 0;
            for (int i = 0; i < likelyTicket_intArr.length; i++) {
                for (int j = 0; j < localTicket_intArr.length; j++) {
                    if (localTicket_intArr[i] == likelyTicket_intArr[j]) {
                        similarNumbers++;
                    }
                }
            }

            if (similarNumbers > 4) {
                status = false;

            } else if (similarNumbers == 4) {
                counter++;
            }
            
            /*if(similarNumbers > 6)
            {
                System.err.printf(" ************* %s %s", localTicket, likelyTicket);
            } */
            //System.err.printf(" ************* %s %s", localTicket, likelyTicket);
        }
        //System.err.printf(" ************* %s %s", localTicket, likelyTicket);
        if (counter > 5) {
            status = false;
        }
        newTirageInput.close();
        return status;
    }

    private boolean canSaveAgainsttest1(String likelyTicket) {
        //System.out.println(" ;"+likelyTicket);
        int ticket_id = 0;
        String localTicket = "";
        int[] localTicket_intArr = new int[6];
        int[] likelyTicket_intArr = new int[6];
        String localfTicket;
        boolean status = true;
        Scanner newTirageInput = null;

        try {
            //mynewtirage.txt
            newTirageInput = new Scanner(new File("globalticket.txt"));
        } catch (FileNotFoundException fnfe) {
            System.err.println("error opening the file " + fnfe.getMessage());
            System.exit(1);
        } catch (SecurityException se) {
            System.err.println("error not permission " + se.getMessage());
            System.exit(1);
        }

        int totalSimilar4 = 0;
        while (newTirageInput.hasNext()) {
            //get Ticket from the internal file that already has the ticket
            ticket_id = newTirageInput.nextInt();
            localTicket = newTirageInput.next();


            //convert files to int array
            localTicket_intArr = transformStringToInt(localTicket);
            likelyTicket_intArr = transformStringToInt(likelyTicket);
            int similarNumbers = 0;
            for (int i = 0; i < likelyTicket_intArr.length; i++) {
                for (int j = 0; j < localTicket_intArr.length; j++) {
                    if (localTicket_intArr[i] == likelyTicket_intArr[j]) {
                        similarNumbers++;
                    }
                }
            }
            if (similarNumbers > 4) {
                status = false;
            }
            if (similarNumbers > 3) {
                totalSimilar4++;

            }
            /*if(similarNumbers > 6)
            {
                System.err.printf(" ************* %s %s", localTicket, likelyTicket);
            } */
            //System.err.printf(" ************* %s %s", localTicket, likelyTicket);
        }
        //System.err.printf(" ************* %s %s", localTicket, likelyTicket);

        if (totalSimilar4 > 5) {
            status = false;
        }


        newTirageInput.close();
        return status;
    }

    private String transformIntArryToString(List<Integer> ints) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ints.size(); i++) {
            stringBuilder.append(String.format("%02d-", ints.get(i)));
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private String transformIntArrayToString(int[] ticketas) {
        return String.format("%02d-%02d-%02d-%02d-%02d-%02d", ticketas[0], ticketas[1], ticketas[2], ticketas[3], ticketas[4], ticketas[5]);
    }

    public void getListOfNumber(String myString) {
        String numberStr = "";
        int counter = 0;
        boolean response = false;
        int[] numbersPicked = null;
        do {
            do {
                int[] numbersArr = convertStringToIntArray1(myString);
                Arrays.sort(numbersArr);
                //System.out.printf("is this number less than 20 "+numbersArr[0]);
                if (numbersArr[0] < 20) {
                    int[] indexArr = randomPickIndex(numbersArr.length);
                    Arrays.sort(indexArr);
                    numbersPicked = getNumbersBasedOnIndex(numbersArr, indexArr);
                    String ticket = transformIntArrayToString(numbersPicked);
                    numberStr = checkTicket(ticket);
                }
                counter++;
            } while (numberStr.length() < 5 && counter < 100);
            if (numberStr.length() > 2) {
                response = canSaveAgainsttest2(numberStr);
            }
            if (response) {
                System.out.printf("\n Good number :  " + numberStr);
                formatSomethingUseful(numbersPicked);
            }
            if (counter > 1000) {
                System.out.printf("No Number was found  ");
                response = true;
            }
        } while ((!response || counter < 100));
       /*if(numberStr.length() > 5)
       {
          if(canSaveAgainsttest2(numberStr))
          {
              System.out.printf("Best number is "+ numberStr);
          }
       }else
       {
            System.out.printf("No numbers found "+ numberStr);
       }*/

    }

    public void formatSomethingUseful(int[] arr) {
        int zeros = 0;
        int tens = 0;
        int twenties = 0;
        int thirties = 0;
        int forties = 0;
        int fifties = 0;
        int[] arr1 = new int[6];

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 10) {
                zeros++;
                arr1[0] = zeros * 100;
            } else if (arr[i] < 20) {
                tens++;
                arr1[1] = (tens * 100) + 10;
            } else if (arr[i] < 30) {
                twenties++;
                arr1[2] = (twenties * 100) + 20;
            } else if (arr[i] < 40) {
                thirties++;
                arr1[3] = (thirties * 100) + 30;
            } else if (arr[i] < 50) {
                forties++;
                arr1[4] = (forties * 100) + 40;
            } else {
                fifties++;
                arr1[5] = (fifties * 100) + 50;
            }
        }
        Arrays.sort(arr1);
        //String total="";
        String total = "";
        for (int i : arr1) {
            if (i > 99) {
                total = i + "-" + total;

            }
        }
        System.out.printf(" version : " + total.toString());
    }

    public int[] getNumbersBasedOnIndex(int[] numbArr, int[] iArr) {
        int[] pickedArr = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            pickedArr[i] = numbArr[iArr[i]];
        }
        return pickedArr;
    }

    public int[] randomPickIndex(int i) {
        ArrayList<Integer> randompicks = new ArrayList<>();
        int[] indexesPicked = new int[6];
        int index = 0;
        Random generator = new Random();
        int number;
        do {
            number = generator.nextInt(i);

            if (randompicks.contains(number)) {

            } else {
                randompicks.add(number);
                indexesPicked[index] = number;
                index++;
            }
            //System.out.printf("the numbers are : "+ number);

        } while (randompicks.size() < 6);

        return indexesPicked;
    }

    public int[] convertStringToIntArray1(String myString) {
        int charLength = myString.length();
        char[] myCharArray = new char[charLength];
        myCharArray = myString.toCharArray();
        ArrayList<String> newString = new ArrayList<String>();
        String oneItem = "";
        //System.out.printf("%s \n", myString);
        int index = 0;
        for (int i = 0; i < charLength; i++) {

            if (myCharArray[i] == '-') {
                index++;
                //oneItem= oneItem.trim();
                newString.add(oneItem.trim());
                oneItem = "";
            } else {
                oneItem += myCharArray[i];
            }
        }
        newString.add(oneItem.trim());
        int[] intArray = new int[newString.size()];
        //System.out.printf("%d \n", newString.size());
        index = 0;
        for (int i = 0; i < newString.size(); i++) {
            intArray[index] = Integer.parseInt(newString.get(i));
            index++;
        }
        return intArray;
    }

    private List<Integer> convertStringToIntArr(String str) {
        int charLength = str.length();
        int counter = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i * 3 < charLength; i++) {
            list.add(Integer.parseInt(str.substring((i * 3), ((i * 3) + 2))));
        }
        return list;
    }

    public void convertStringToIntArray(String myString) {

        int charLength = myString.length();
        char[] myCharArray = new char[charLength];
        myCharArray = myString.toCharArray();
        ArrayList<String> newString = new ArrayList<String>();
        String oneItem = "";
        System.out.printf("%s \n", myString.length());
        int index = 0;
        for (int i = 0; i < charLength; i++) {

            if (myCharArray[i] == '-') {
                index++;
                //oneItem= oneItem.trim();
                newString.add(oneItem.trim());
                oneItem = "";
            } else {
                oneItem += myCharArray[i];
            }
        }
        newString.add(oneItem.trim());
        int[] intArray = new int[newString.size()];
        System.out.printf("%d \n", newString.size());
        index = 0;
        for (int i = 0; i < newString.size(); i++) {
            intArray[index] = Integer.parseInt(newString.get(i));
            index++;
        }
        getUniqueNumberFromArray(intArray);

    }

    public String checkTicketLocally1(int[] randomTicket) {
        String ticketString = "";
        ticketString = transformIntArrayToString(randomTicket);

        try {
            while (input.hasNext() && SENTINEL == -999) {
                int ticketNumber = input.nextInt();
                pickedTicket = transformStringToInt(input.next().toString());

                int numberRepeat = 0;
                for (int i = 0; i < pickedTicket.length; i++) {
                    for (int j = 0; j < randomTicket.length; j++) {
                        if (pickedTicket[i] == randomTicket[j]) {
                            numberRepeat++;

                        }
                    }
                }
                if (numberRepeat > 3) {

                    //remove from the loop
                    SENTINEL = 0;
                    isAllLess3 = false;
                } else {
                    if (ticketNumber == 15522) {
                        if (checkTicket(ticketString).length() > 1) {
                            System.out.printf(" %s \n", checkTicket(ticketString));
                            return checkTicket(ticketString);
                        }

                    }
                }
            }

        } catch (NoSuchElementException noSuchElementException) {
            System.err.println("File improperly formed");
            input.close();
            System.exit(1);
        } catch (IllegalStateException illegalStateException) {
            System.err.println("error reading from file");
            System.exit(1);
        }
        return ticketString;
    }

    private String checkTicket(String myTicket) {
        char CHARACTER_NUMBER_0 = '0';
        int number0 = 0;
        char CHARACTER_NUMBER_1 = '1';
        int number1 = 0;
        char CHARACTER_NUMBER_2 = '2';
        int number2 = 0;
        char CHARACTER_NUMBER_3 = '3';
        int number3 = 0;
        char CHARACTER_NUMBER_4 = '4';
        int number4 = 0;
        char CHARACTER_NUMBER_5 = '5';
        int number5 = 0;
        char CHARACTER_NUMBER_6 = '6';
        int number6 = 0;
        char CHARACTER_NUMBER_7 = '7';
        int number7 = 0;
        char CHARACTER_NUMBER_8 = '8';
        int number8 = 0;
        char CHARACTER_NUMBER_9 = '9';
        int number9 = 0;
        int myDouble = 0;
        int fiveplusRepeat = 0;
        int oddRepeat = 0;
        int evenRepeat = 0;
        int fourminusRepeat = 0;
        int[] myIntArrayTicket = new int[6];

        myIntArrayTicket = transformStringToInt(myTicket);
        for (int i = 0; i < myIntArrayTicket.length; i++) {
            if ((myIntArrayTicket[i] % 2) == 1) {
                oddRepeat++;
            }
            if ((myIntArrayTicket[i] % 2) == 0) {
                evenRepeat++;
            }
            if ((myIntArrayTicket[i] % 10 >= 5) || myIntArrayTicket[i] >= 50) {
                fiveplusRepeat++;
            }
            if ((myIntArrayTicket[i] % 10 < 5) && myIntArrayTicket[i] < 50) {
                fourminusRepeat++;
            }
        }
        if (oddRepeat > 5) {
            return "";
        }
        if (evenRepeat > 5) {
            return "";
        }
        if (fiveplusRepeat > 4) {
            return "";
        }
        if (fourminusRepeat > 4) {
            return "";
        }


        for (int i = 0; i < myTicket.length(); i++) {
            char c = myTicket.charAt(i);
            if (CHARACTER_NUMBER_0 == c) {
                number0++;
                if (number0 > 4) {
                    return "";
                }
            }
            if (CHARACTER_NUMBER_1 == c) {
                number1++;
                if (number1 > 4) {
                    return "";
                }
            }
            if (CHARACTER_NUMBER_2 == c) {
                number2++;
                if (number2 > 4) {
                    return "";
                }
            }
            if (CHARACTER_NUMBER_3 == c) {
                number3++;
                if (number3 > 4) {
                    return "";
                }
            }
            if (CHARACTER_NUMBER_4 == c) {
                number4++;
                if (number4 > 4) {
                    return "";
                }
            }
            if (CHARACTER_NUMBER_5 == c) {
                number5++;
                if (number5 == 2) {
                    myDouble++;
                }
                if (number5 > 2) {
                    return "";
                }
            }
            if (CHARACTER_NUMBER_6 == c) {
                number6++;
                if (number6 == 2) {
                    myDouble++;
                }
                if (number6 > 2) {
                    return "";
                }

            }
            if (CHARACTER_NUMBER_7 == c) {
                number7++;
                if (number7 == 2) {
                    myDouble++;
                }
                if (number7 > 2) {
                    return "";
                }
            }
            if (CHARACTER_NUMBER_8 == c) {
                number8++;
                if (number8 == 2) {
                    myDouble++;
                }
                if (number8 > 2) {
                    return "";
                }
            }
            if (CHARACTER_NUMBER_9 == c) {
                number9++;
                if (number9 == 2) {
                    myDouble++;
                }
                if (number9 > 2) {
                    return "";
                }
            }

        }
        if (myDouble >= 2) {
            return "";
        }

        number0 = 0;
        number1 = 0;
        number2 = 0;
        number3 = 0;
        number4 = 0;
        number5 = 0;
        number6 = 0;
        number7 = 0;
        number8 = 0;
        number9 = 0;
        myDouble = 0;
        return myTicket;
    }

    public int[] transformStringToInt(String theString) {
        String numb1 = theString.substring(0, 2);
        int numbInt1 = Integer.parseInt(numb1);
        String numb2 = theString.substring(3, 5);
        int numbInt2 = Integer.parseInt(numb2);
        String numb3 = theString.substring(6, 8);
        int numbInt3 = Integer.parseInt(numb3);
        String numb4 = theString.substring(9, 11);
        int numbInt4 = Integer.parseInt(numb4);
        String numb5 = theString.substring(12, 14);
        int numbInt5 = Integer.parseInt(numb5);
        String numb6 = theString.substring(15, 17);
        int numbInt6 = Integer.parseInt(numb6);

        int[] stringToIntArray = {numbInt1, numbInt2, numbInt3, numbInt4, numbInt5, numbInt6};

        return stringToIntArray;

    }


}
