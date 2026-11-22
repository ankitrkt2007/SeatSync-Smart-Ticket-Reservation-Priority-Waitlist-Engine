import model.Ticket;
import service.SeatSyncEngine;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SeatSyncEngine engine = new SeatSyncEngine(3); // 3 seats for easy waitlist & promotion demo
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== SeatSync: Smart Ticket Reservation & Priority Waitlist Engine ===");
            System.out.println("1. Book Ticket");
            System.out.println("2. Lookup Ticket by PNR");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Undo Last Action");
            System.out.println("5. Inspect Internal Data Structures");
            System.out.println("6. Load Sample Demo Scenario");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> {
                    System.out.print("Passenger Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Age: ");
                    int age = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Priority (1=Emergency, 2=VIP/Tatkal, 3=General): ");
                    int priority = Integer.parseInt(scanner.nextLine().trim());
                    Ticket ticket = engine.requestBooking(name, age, priority);
                    System.out.println("Result -> " + ticket);
                }
                case "2" -> {
                    System.out.print("Enter PNR (e.g., PNR1001): ");
                    String pnr = scanner.nextLine().trim();
                    Ticket found = engine.lookupPnr(pnr);
                    System.out.println(found != null ? found : "No ticket found for " + pnr);
                }
                case "3" -> {
                    System.out.print("Enter PNR to cancel: ");
                    String pnr = scanner.nextLine().trim();
                    System.out.println(engine.cancelTicket(pnr));
                }
                case "4" -> System.out.println(engine.undoLastAction());
                case "5" -> engine.inspectInternalStructures();
                case "6" -> {
                    System.out.println("Loading demo bookings (3 seats total)...");
                    System.out.println("  " + engine.requestBooking("Aarav", 28, 3));
                    System.out.println("  " + engine.requestBooking("Meera", 34, 3));
                    System.out.println("  " + engine.requestBooking("Rohan", 22, 3));
                    System.out.println("  " + engine.requestBooking("Kabir", 45, 3));
                    System.out.println("  " + engine.requestBooking("Ananya", 62, 1));
                    System.out.println("  " + engine.requestBooking("Vikram", 39, 2));
                    engine.inspectInternalStructures();
                }
                case "0" -> {
                    System.out.println("Exiting SeatSync. Good luck with your CSM228 evaluation!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}
