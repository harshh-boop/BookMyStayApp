/**
 * BookMyStayApp - Demonstrates reservation confirmation and
 * safe room allocation using Queue, HashMap, and Set.
 */

import java.util.*;

/* ---------- Reservation Class ---------- */

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/* ---------- Room Inventory ---------- */

class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }
    }
}

/* ---------- Booking Request Queue ---------- */

class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

/* ---------- Booking Service ---------- */

class BookingService {

    private RoomInventory inventory;

    // Tracks allocated room IDs
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Maps room type -> assigned room IDs
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    private int roomCounter = 100;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBooking(Reservation reservation) {

        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) > 0) {

            // Generate unique room ID
            String roomId = roomType.substring(0,2).toUpperCase() + roomCounter++;

            // Ensure uniqueness
            allocatedRoomIds.add(roomId);

            roomAllocations.putIfAbsent(roomType, new HashSet<>());
            roomAllocations.get(roomType).add(roomId);

            // Update inventory
            inventory.decrementRoom(roomType);

            System.out.println("Reservation Confirmed");
            System.out.println("Guest : " + reservation.getGuestName());
            System.out.println("Room Type : " + roomType);
            System.out.println("Assigned Room ID : " + roomId);
            System.out.println("-----------------------------");

        } else {

            System.out.println("Reservation Failed for " + reservation.getGuestName());
            System.out.println("No " + roomType + " available.");
            System.out.println("-----------------------------");
        }
    }
}

/* ---------- Main Application ---------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Booking Allocation =====");

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        BookingService bookingService = new BookingService(inventory);

        // Add booking requests
        queue.addRequest(new Reservation("Rahul", "Single Room"));
        queue.addRequest(new Reservation("Priya", "Single Room"));
        queue.addRequest(new Reservation("Arjun", "Suite Room"));
        queue.addRequest(new Reservation("Meera", "Suite Room"));

        // Process queue
        while (queue.hasRequests()) {

            Reservation request = queue.getNextRequest();
            bookingService.processBooking(request);
        }

        // Show remaining inventory
        inventory.displayInventory();
    }
}