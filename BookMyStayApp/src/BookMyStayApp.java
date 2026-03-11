/**
 * BookMyStayApp - Demonstrates room search and availability check
 * using centralized inventory in the Hotel Booking System.
 */

import java.util.HashMap;
import java.util.Map;

/* ---------- Abstract Room Class ---------- */
abstract class Room {

    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : ₹" + price + " per night");
    }
}

/* ---------- Room Implementations ---------- */

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 7000);
    }
}

/* ---------- Inventory Class ---------- */

class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // Example: Suite unavailable
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Provide inventory map for search operations
    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

/* ---------- Search Service ---------- */

class SearchService {

    public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("\n----- Available Rooms -----");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Filter unavailable rooms
            if (available > 0) {

                room.displayDetails();
                System.out.println("Available : " + available);
                System.out.println("----------------------------");

            }
        }
    }
}

/* ---------- Main Application ---------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       BOOK MY STAY APP");
        System.out.println("    Hotel Booking System v1.0");
        System.out.println("=================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Room domain objects
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Search service
        SearchService searchService = new SearchService();

        // Guest searches available rooms
        searchService.searchAvailableRooms(inventory, rooms);

        System.out.println("\nSearch completed (inventory unchanged).");
    }
}