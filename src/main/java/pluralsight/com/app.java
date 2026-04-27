package pluralsight.com;
import java.util.Scanner;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class app {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;





        while (running) {

            System.out.println("What action would you like?");
            System.out.println(" [D] Add Deposit");
            System.out.println(" [P] Make Payment");
            System.out.println(" [L] Display Ledger");
            System.out.println(" [X] Exit");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "D":

                    break;
                case "P":
                    break;
                case "L":
                    break;
                case "X":
                    System.out.println("Goodbye!;");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please c hoose D, P, L, or X.");
            }





            }

        }
    }









