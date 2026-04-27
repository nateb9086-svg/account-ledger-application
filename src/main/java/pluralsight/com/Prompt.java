package pluralsight.com;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class Prompt {
    private static final String file = "transactions.csv";
    private static final String Divider = "|";
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-day");
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm-ss");

    private static void appendToFile(String row) {
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write("\n" + row);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static String promtDate(Scanner scanner){
        while (true) {
            System.out.print("Date (YYYY-MM-DD) or Enter for today: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return LocalDate.now().format(dateFormat);
            try {
                LocalDate.parse(input, dateFormat);
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
    private static void addTransaction(Scanner scanner, String type) {

        String date = promtDate(scanner);
        String time = promptTime(scanner);
    }







}
