
public class delphiToJava {
/*
    private int checkCount;
    private int tooSimilar;
    private int isValid;

    public int CheckTicketWrapper(String ticketNumber){
        isValid = 0;
        tooSimilar = checkTooSimilarTicket(ticketNumber);
        if (tooSimilar == 0){
            checkCount = checkTicket(ticketNumber);
            if (checkCount > 5){
                isValid = 0;
            }else {
                isValid = checkTicketInner(ticketNumber);
            }
        }
        return isValid;
    }


    public int checkTooSimilarTicket(String ticketNumber){
        int tooSimilarCount;
//        SELECT COUNT(*) INTO TooSimilarCount FROM lotto.ticket
//                WHERE
//        (CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 1,2)) > 0 THEN 1 ELSE 0 END +
//                CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 4,2)) > 0 THEN 1 ELSE 0 END +
//                CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 7,2)) > 0 THEN 1 ELSE 0 END +
//                CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 10,2)) > 0 THEN 1 ELSE 0 END +
//                CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 13,2)) > 0 THEN 1 ELSE 0 END +
//                CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 16,2)) > 0 THEN 1 ELSE 0 END) > 4;
        return tooSimilarCount;
    }


    public int checkTicket(String ticketNumber){
        int checkCount;
//        SELECT COUNT(*) INTO CheckCount FROM lotto.ticket
//                WHERE
//        (CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 1,2)) > 0 THEN 1 ELSE 0 END +
//                CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 4,2)) > 0 THEN 1 ELSE 0 END +
//                CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 7,2)) > 0 THEN 1 ELSE 0 END +
//                CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 10,2)) > 0 THEN 1 ELSE 0 END +
//                CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 13,2)) > 0 THEN 1 ELSE 0 END +
//                CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 16,2)) > 0 THEN 1 ELSE 0 END) > 3;
        return checkCount;
    }

    public int checkTicketInner(String ticketNumber){
        int isValid;
        String v_ticketnumber = ticketNumber;
        int v_CheckCount;
        int v_finished = 0;

//        DECLARE v_cursor CURSOR FOR
//        SELECT
//                ticket_number
//        FROM lotto.ticket
//                WHERE
//               (CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 1,2)) > 0 THEN 1 ELSE 0 END +
//                CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 4,2)) > 0 THEN 1 ELSE 0 END +
//                CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 7,2)) > 0 THEN 1 ELSE 0 END +
//                CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 10,2)) > 0 THEN 1 ELSE 0 END +
//                CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 13,2)) > 0 THEN 1 ELSE 0 END +
                  if(ticketNumber.contains("00")) return 0;




//                CASE WHEN INSTR(ticket_number, SUBSTRING(TicketNumber, 16,2)) > 0 THEN 1 ELSE 0 END) > 3;

//        DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = TRUE;
//        SET IsValid = 1;
//        OPEN v_cursor;
//
//        GET_TICKETNUMBER: LOOP
//        SET v_finished= FALSE;
//        FETCH v_cursor INTO  v_ticketnumber;
//        IF v_finished
//        THEN LEAVE GET_TICKETNUMBER;
//        END IF;
//
//        SET IsValid = 1;
//
//        CALL CheckTicket(v_ticketnumber,checkCount);
//
//        IF checkCount > 5
//        THEN
//        SET IsValid = 0;
//        LEAVE GET_TICKETNUMBER;
//        END IF;
//
//        END LOOP;
//
//        CLOSE v_cursor;




        return isValid;
    }*/
}
