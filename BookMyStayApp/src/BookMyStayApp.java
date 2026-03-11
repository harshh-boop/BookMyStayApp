/**
 * BookMyStayApp - Demonstrates persistence of booking and inventory data
 * using Java Serialization for system recovery.
 */

import java.io.*;
import java.util.*;

/* ---------- Reservation Class ---------- */

class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean cancelled = false;

    public Reservation(String reservationId, String guestName,
                       String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() { return reservationId; }
    public String getRoomType() { return roomType; }
    public boolean isCancelled() { return cancelled; }
    public void cancel() { cancelled = true; }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Room ID: " + roomId +
                " | Status: " + (cancelled ? "Cancelled" : "Active"));
    }
}

/* ---------- Room Inventory ---------- */

class RoomInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\nInventory Status:");
        inventory.forEach((type, count) -> System.out.println(type + ": " + count));
    }

    public Map<String, Integer> getInventoryMap() {
        return inventory;
    }
}

/* ---------- Booking History ---------- */

class BookingHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, Reservation> reservations = new HashMap<>();

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return reservations.get(id);
    }

    public void displayHistory() {
        System.out.println("\nBooking History:");
        for (Reservation r : reservations.values()) r.displayReservation();
    }

    public Map<String, Reservation> getReservationsMap() {
        return reservations;
    }
}

/* ---------- Persistence Service ---------- */

class PersistenceService {

    private static final String FILE_NAME = "booking_system_state.ser";

    public static void saveState(RoomInventory inventory, BookingHistory history) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            oos.writeObject(history);
            System.out.println("\nSystem state saved successfully!");
        } catch (IOException e) {
            System.err.println("Failed to save system state: " + e.getMessage());
        }
    }

    public static Object[] loadState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("\nNo previous state found. Starting fresh.");
            return new Object[]{new RoomInventory(), new BookingHistory()};
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            RoomInventory inventory = (RoomInventory) ois.readObject();
            BookingHistory history = (BookingHistory) ois.readObject();
            System.out.println("\nSystem state restored successfully!");
            return new Object[]{inventory, history};
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to restore system state: " + e.getMessage());
            System.out.println("Starting with fresh state.");
            return new Object[]{new RoomInventory(), new BookingHistory()};
        }
    }
}

/* ---------- Main Application ---------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Persistence & Recovery Demo =====");

        // Restore previous state or create fresh
        Object[] restored = PersistenceService.loadState();
        RoomInventory inventory = (RoomInventory) restored[0];
        BookingHistory history = (BookingHistory) restored[1];

        // Display current state
        inventory.displayInventory();
        history.displayHistory();

        // Simulate new booking
        Reservation r1 = new Reservation("RES501", "Gaurav", "Single Room", "SI101");
        if (inventory.allocateRoom(r1.getRoomType())) {
            history.addReservation(r1);
            System.out.println("\nNew booking added successfully!");
        } else {
            System.out.println("\nBooking failed: No rooms available.");
        }

        // Display updated state
        inventory.displayInventory();
        history.displayHistory();

        // Save state before shutdown
        PersistenceService.saveState(inventory, history);
    }
}