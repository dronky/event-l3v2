package ru.miit.lab3events;

import ru.miit.lab3events.annotation.Added;
import ru.miit.lab3events.annotation.Pay;
import ru.miit.lab3events.annotation.Process;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Sinky on 03.04.2017.
 */

/*Выводит пассажиру список удобных поездов, чтобы он мог выбрать. */
public class KassaRequest {
    @Inject
    @Process
    private javax.enterprise.event.Event<List<String>> reqProcess;
    private static List<String> validTrains;
    private static int sel = 0;
    private static int bill = 0;

    public void interact(@Observes @Process List<String> found) {
        validTrains = found;
        int count = 0;
        System.out.println("Found good trains for you:");
        for (String string : found) {
            count++;
            System.out.println(count + ") " + string);
        }

        if (sel != 0)
            System.out.println(count + 1 + ") refill bill by 100 rub");
        System.out.println("0) Exit");
        sel = KassaManager.inputAmount();
        if (sel < count + 1) {
            if (bill != 0) pay(bill);
        } else if (sel == count + 1) {
            bill += 100;
            System.out.println("Your bill: " + bill + " rub");
            reqProcess.fire(validTrains);
        } else if (sel == 0) {
            return;
        }
    }

    public boolean pay(@Observes @Pay int c_bill) {
        bill = c_bill;
        boolean status = false;
        if (validTrains != null)
            if (sel <= validTrains.size() && sel > 0 && validTrains.size() > 0) {
                if (bill > Integer.parseInt(validTrains.get(sel - 1).split("\\|")[4])) {
                    System.out.println("Payment successfull");
                    status = true;
                } else {
                    System.out.println("Not enough money. Please select another train or refill your bill");
                    System.out.println("Your bill: " + bill + " rub");
                    reqProcess.fire(validTrains);
                }
            }
        return status;
    }

    private static void refill(int value) {
        bill += value;
    }

}
