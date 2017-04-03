package ru.miit.lab3events;

import ru.miit.lab3events.annotation.Added;
import ru.miit.lab3events.annotation.Pay;

import javax.inject.Inject;
import java.util.Scanner;

/*2. Разработать систему «Железнодорожная касса».
 Пассажир делает заявку на станцию назначения, время и дату поездки.
 Система регистрирует Заявку и осуществляет поиск соответствующего Поезда.
  Пассажир делает выбор Поезда и получает Счет на оплату.
  Между классами могут передаваться разные сообщения.*/

public class KassaManager {
    @Inject
    @Added
    private javax.enterprise.event.Event<String> reqGot;
    @Inject
    @Pay
    private javax.enterprise.event.Event<Integer> pay;

    public void addRequest(String req) {
        reqGot.fire(req);
    }

    public void pay(int bill){
        pay.fire(bill);
    }

    public static int inputAmount() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Please select:");
            try {
                return input.nextInt();
            }
            catch (java.util.InputMismatchException e) {
                input.nextLine();
                System.out.println("Error");
            }
        }
    }

}
