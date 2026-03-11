/**
 * BookMyStayApp - Demonstrates centralized room inventory management
 * using HashMap for the Hotel Booking Management System.
 *
 * @author YourName
 * @version 1.0
 */

import java.util.HashMap;
import java.util.Map;

/* Inventory Management Class */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor initializes room availability
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Retrieve availability for a specific room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (for booking or cancellation)
    public void updateAvailability(String roomType, int change) {

        int current = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, current + change);
    }

    // Display entire inventory
    public void displayInventory() {

        System.out.println("\n----- Current Room Inventory -----");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }

        System.out.println("----------------------------------");
    }
}


/* Main Application Class */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("        BOOK MY STAY APP");
        System.out.println("   Hotel Booking System - v1.0");
        System.out.println("====================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Example update (simulate booking)
        System.out.println("\nBooking 1 Single Room...");
        inventory.updateAvailability("Single Room", -1);

        // Example update (simulate cancellation)
        System.out.println("Cancellation of 1 Suite Room...");
        inventory.updateAvailability("Suite Room", +1);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nThank you for using Book My Stay!");
    }
}