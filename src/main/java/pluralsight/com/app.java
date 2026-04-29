package pluralsight.com;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;



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
                    addTransaction(scanner, "Payment");
                    break;
                case "L":
                    Boolean lRunning = true;
                    while (lRunning) {
                        System.out.println("Welcome to the Ledger screen.");
                        System.out.println("What action would you like?");
                        System.out.println(" [A] Display all Entries");
                        System.out.println(" [D] Display Deposits");
                        System.out.println(" [P] Display Payments");
                        System.out.println(" [R] Display Reports");
                        System.out.println(" [H] Home");

                        String lChoice = scanner.nextLine().trim().toUpperCase();

                        switch (lChoice) {
                            case "A":

                                displayLedger();
                                break;
                            case "D":
                                DisplayDeposits();
                                break;
                            case "P":
                                break;
                            case "R":

                                break;
                            case "H":
                                lRunning = false;
                                break;
                            default:
                                System.out.println("Invalid option. Please choose D, P, L, or X.");
                        }
                    }
                    break;
                case "X":
                    System.out.println("Goodbye!;");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose D, P, L, or X.");
            }

        }

    }
    private static final String file = "transactions.csv";
    private static final String divider = "|";
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    Scanner scanner = new Scanner(System.in);
    boolean running = true;

    private static void appendToFile(String row) {
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write("\n" + row);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static String promptDate(Scanner scanner) {
        while (true) {
            System.out.print("Date (YYYY-MM-DD) or Enter for today: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return LocalDate.now().format(dateFormat);
            try {
                LocalDate.parse(input, dateFormat);
                return input;
            } catch (DateTimeParseException e) {
                System.out.println(" Invalid date. Use YYYY-MM-DD.");

            }

        }

    }


    private static String promptTime(Scanner scanner) {
        while (true) {
            System.out.print("Time (HH:MM:SS) or Enter for now: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return LocalTime.now().format(timeFormat);
            try {
                LocalTime.parse(input, timeFormat);
                return input;
            } catch (DateTimeParseException e) {
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

    private static List<String[]> readTransactions() {
        List<String[]> entries = new ArrayList<>();
        File file2 = new File(file);

        if (!file2.exists()) {
            System.out.println("transactions.csv not found.");
            return entries;

        }

        try (BufferedReader bR = new BufferedReader(new FileReader(file2))) {
            String line;
            boolean firstLine = true;
            while ((line = bR.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (!line.trim().isEmpty()) {
                    entries.add(line.split("\\" + divider, -1));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return entries;
    }

    private static String truncate(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }


    private static void displayLedger() {
        List<String[]> entries = readTransactions();

        if (entries.isEmpty()) {
            System.out.println("\n No transaction found.");
            return;
        }

        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.printf("%-12s %-10s %30s %25s %12s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("---------------------------------------------------------------------------------------------");


        double balance = 0;

        for (String[] fields : entries) {
            if (fields.length < 5) continue;

            String date = fields[0];
            String time = fields[1];
            String description = fields[2];
            String vendor = fields[3];
            String amountStr = fields[4];
            try {
                double amount = Double.parseDouble(amountStr);
                balance += amount;
                String sign = amount >= 0 ? "+" : "";
                System.out.printf(" %-12s %10s %-30s %-25s %12s%n",
                        date, time,
                        truncate(description, 28),
                        truncate(vendor, 23),
                        sign + String.format("%.2f", amount));

            }
            catch (NumberFormatException e) {
            }


        }
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.printf(" %-79s %12s%n", "BALANCE", (balance >= 0? "+" : "" ) +String.format("%.2f", balance));
        System.out.println("----------------------------------------------------------------------------------------------");




    }
    private static void DisplayDeposits(){
        List<String[]> entries = readTransactions();

        List<String[]> deposits = new ArrayList<>();
        for (String[] fields : entries) {
            if (fields.length < 5) continue;
            try {
                double amount = Double.parseDouble(fields[4]);
                if (amount > 0) deposits.add(fields);
            }
            catch (NumberFormatException e) {}
        }
        if (deposits.isEmpty()) {
            System.out.println("\n No deposits found");
            return;
        }
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.printf("%-12s %-10s %30s %25s %12s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("---------------------------------------------------------------------------------------------");

        double total = 0;

        for (String[] fields : deposits) {
            double amount = Double.parseDouble(fields[4]);
            total += amount;
            System.out.printf(" %-12s %10s %-30s %-25s %12s%n",
                    fields[0], fields[1],
                    truncate(fields[2], 28),
                    truncate(fields[3], 23),
                    "+" + String.format("%.2f", amount));
            }

            System.out.println("----------------------------------------------------------------------------------------------");
            System.out.printf(" %-79s %12s%n", "TOTAL DEPOSITS", "+" + String.format("%.2f", total));
            System.out.println("-----------------------------------------------------------------------------------------------");

    }


}

































