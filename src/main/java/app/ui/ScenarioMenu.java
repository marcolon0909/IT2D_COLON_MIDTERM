package app.ui;

import app.dao.ScenarioDao;
import app.model.ScenarioEntity;

import java.util.List;
import java.util.Scanner;

public class ScenarioMenu {
    private final ScenarioDao scenarioDao = new ScenarioDao();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        int choice = 0;
        do {
            System.out.println("\n==========================================");
            System.out.println("     AIRPORT BAGGAGE LOGGING & CLAIM      ");
            System.out.println("==========================================");
            System.out.println("[1] Add Record (Check-In Baggage)");
            System.out.println("[2] View Records (View Checked Baggage)");
            System.out.println("[3] Exit System");
            System.out.print("Enter your choice (1-3): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline buffer token[cite: 2]
            } else {
                System.out.println("Invalid input! Please enter a number between 1 and 3.");
                scanner.nextLine(); // Clear bad token
                continue;
            }

            switch (choice) {
                case 1:
                    addRecord();
                    break;
                case 2:
                    viewRecords();
                    break;
                case 3:
                    System.out.println("Exiting system. Safe travels!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select between 1 and 3.");
            }
        } while (choice != 3);
    }

    private void addRecord() {
        System.out.println("\n--- Add New Baggage Record ---");
        System.out.print("Enter Passenger Name (e.g., Juan Dela Cruz): ");
        String passengerName = scanner.nextLine();

        System.out.print("Enter Flight No (e.g., PR-1814): ");
        String flightNo = scanner.nextLine();

        double weightKg = 0.0;
        while (true) {
            System.out.print("Enter Weight (KG) [Formatted to 1 decimal place, e.g., 23.0]: ");
            if (scanner.hasNextDouble()) {
                weightKg = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                break;
            } else {
                System.out.println("Invalid weight format. Please enter a valid number.");
                scanner.nextLine();
            }
        }

        String status = "";
        while (true) {
            System.out.print("Enter Status ('CHECKED', 'LOADED', or 'CLAIMED'): ");
            status = scanner.nextLine().trim().toUpperCase();
            if (status.equals("CHECKED") || status.equals("LOADED") || status.equals("CLAIMED")) {
                break;
            }
            System.out.println("Invalid status! Must be strictly 'CHECKED', 'LOADED', or 'CLAIMED'.");
        }

        boolean success = scenarioDao.insertRecord(passengerName, flightNo, weightKg, status);
        if (success) {
            System.out.println("✅ Record successfully added to the database!");
        } else {
            System.out.println("❌ Failed to insert record.");
        }
    }

    private void viewRecords() {
        List<ScenarioEntity> records = scenarioDao.getAllRecords();

        System.out.println("\n==================================================================================");
        System.out.println("                             BAGGAGE TRACKING MANIFEST                            ");
        System.out.println("==================================================================================");

        if (records.isEmpty()) {
            System.out.println("No records found in the database.");
        } else {
            // Aligned console table format using printf[cite: 1, 2]
            System.out.printf("%-5s | %-25s | %-12s | %-12s | %-10s%n",
                    "ID", "PASSENGER NAME", "FLIGHT NO", "WEIGHT (KG)", "STATUS");
            System.out.println("----------------------------------------------------------------------------------");

            for (ScenarioEntity r : records) {
                System.out.printf("%-5d | %-25s | %-12s | %-12.1f | %-10s%n",
                        r.id(),
                        r.passengerName(),
                        r.flightNo(),
                        r.weightKg(),
                        r.status()
                );
            }
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println("Total Records: " + records.size());
        }
        System.out.println("==================================================================================");
    }
}