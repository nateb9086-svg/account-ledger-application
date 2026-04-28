package pluralsight.com;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
                    addTransaction(scanner, "Deposit");
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
    private static final String file = "transactions.csv";
    private static final String divider = "|";
    private static final DateTimeFormatter dateFormt = DateTimeFormatter.ofPattern("yyyy-MM-day");
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm-ss");

    Scanner scanner = new Scanner(System.in);
    boolean running = true;

    private static void appendToFile(String row) {
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write("\n" + row);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static String promptDate(Scanner scanner){
        while (true) {
            System.out.print("Date (YYYY-MM-DD) or Enter for today: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return LocalDate.now().format(dateFormt);
            try {
                LocalDate.parse(input, dateFormt);
                return input;
            }
            catch (DateTimeParseException e) {
                System.out.println(" Invalid date. Use YYYY-MM-DD.");

            }

        }

    }


    private static String promptTime(Scanner scanner){
        while (true) {
            System.out.print("Time (HH:MM:SS) or Enter for now: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return LocalTime.now().format(timeFormat);
            try {
                LocalTime.parse(input, timeFormat );
            }
            catch (DateTimeParseException e) {
                System.out.println(" Invalid time. Use HH:MM:SS.");
            }
        }

    }

    private static double promptAmount(Scanner scanner) {
        while (true) {
            System.out.print("Amount (e.g. 1500.00): ");
            String input = scanner.nextLine().trim();
            try {
                double amount = Double.parseDouble(input);
                if (amount <= 0) {
                    System.out.println(" Amount must be greater than zero.");


                } else {
                    return amount;
                }
            } catch (NumberFormatException e) {
                System.out.println(" Invalid amount. Enter a number (e.g. 1500.00).");
            }
        }



    }
    private static void addTransaction(Scanner scanner, String type) {
        System.out.println("\n---" + type + " ---");
        String date = promptDate(scanner);
        String time = promptTime(scanner);

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        String vendorLabel = type.equals("Deposit") ? "Vendor (sender/source): " : "Vendor (payee/recipient): ";
        System.out.print(vendorLabel);
        String vendor = scanner.nextLine().trim();
        double amount = promptAmount(scanner);

        if (type.equals("Payment")) {
            amount = -amount;
        }
        String row = String.join(divider,
                date,
                time,
                description,
                vendor,
                String.format("%.2f", amount));

        appendToFile(row);

        System.out.printf("%n %s recorded: %s%n", type, row);
    }

}































