package ru.miit.lab3events;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringJoiner;


/**
 * Created by Андрей on 18.02.2017.
 */
public class Lab3Start {
    protected static Weld weld;
    protected static WeldContainer container;

    public static void main(String args[]) throws Exception {
        weld = new Weld();
        container = weld.initialize();
        try {
            KassaManager kassaManager = container.instance().select(KassaManager.class).get();
            Scanner sc = new Scanner(System.in);
            String city;
            String time;
            String date;
            System.out.println("Please enter the City");
            city = sc.next();
            System.out.println("Please enter the Time");
            time = sc.next();
            System.out.println("Please enter the Date");
            date = sc.next();

            //kassaManager.addRequest("Kaluga|10:30|01:04:2017");
            kassaManager.addRequest(city + "|" + time + "|" + date);
            kassaManager.pay(240);
        } finally {
            weld.shutdown();
        }
    }
}
