/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author scifct
 */
public class PureTicket {

    public static void main(String[] args) {
        Random randomTicket = new Random();
        int option = 3;

        int[] ticket = new int[6];
        Arrays.fill(ticket, 0);
        int count = 2;
        boolean status = true;
        Pure test = new Pure();
        //NewPure newPure = new NewPure();
        //newPure.getTicketNumber();
        if (option == 2) {
            test.getListOfNumber("11-26-25-27-31-50-53");
        }
        if (option == 0) {
            test.convertStringToIntArray("09-16-35-40");
        }
        if (option == 3) {
            test.getPureNumbers("04-12-24-25");
        }
        if (option == 1) {
            while (count < 250000) {
                status = true;
                ticket[0] = randomTicket.nextInt(53) + 1;
                ticket[1] = randomTicket.nextInt(53) + 1;
                ticket[2] = randomTicket.nextInt(53) + 1;
                ticket[3] = randomTicket.nextInt(53) + 1;
                ticket[4] = randomTicket.nextInt(53) + 1;
                ticket[5] = randomTicket.nextInt(53) + 1;

                for (int j = 0; j < 6; j++) {
                    for (int k = j + 1; k < 6; k++) {
                        if (ticket[j] == ticket[k]) {
                            status = false;
                        }
                    }
                }
                if (status == true) {
                    Arrays.sort(ticket);
                    Pure check = new Pure();
                    check.openFile1();
                    check.checkTicketLocally1(ticket);
                    //System.out.printf("Ticket%d %d : %d-%d-%d-%d-%d-%d \n", count, i, ticket[0], ticket[1], ticket[2], ticket[3], ticket[4], ticket[5]);
                    count++;

                } else {}
            }
        }
    }
}
