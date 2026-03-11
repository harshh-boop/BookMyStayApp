/**
 * BookMyStayApp - Demonstrates booking cancellation
 * and inventory rollback using Stack for the Hotel Booking System.
 */

import java.util.*;

/* ---------- Reservation Class ---------- */

class Reservation {

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

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }

    public void displayReservation() {

        System.out.println(
                "Reservation ID: " + reservationId +
                        " | Guest: " + guestName +
                        " | Room Type: " + roomType +
                        " | Room ID: " + roomId +
                        " | Status: " + (cancelled ? "Cancelled" : "Active")
        );
    }
}

/* ---------- Room Inventory ---------- */

class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {

        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void incrementRoom(String roomType) {

        inventory.put(roomType,
                inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

/* ---------- Booking History ---------- */

class BookingHistory {

    private Map<String, Reservation> reservations = new HashMap<>();

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return reservations.get(id);
    }

    public void displayHistory() {

        System.out.println("\nBooking History:");

        for (Reservation r : reservations.values()) {
            r.displayReservation();
        }
    }
}

/* ---------- Cancellation Service ---------- */

class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelReservation(String reservationId,
                                  BookingHistory history,
                                  RoomInventory inventory) {

        Reservation reservation = history.getReservation(reservationId);

        if (reservation == null) {

            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        if (reservation.isCancelled()) {

            System.out.println("Cancellation Failed: Reservation already cancelled.");
            return;
        }

        // Track released room ID
        rollbackStack.push(reservation.getRoomId());

        // Restore inventory
        inventory.incrementRoom(reservation.getRoomType());

        // Mark reservation cancelled
        reservation.cancel();

        System.out.println("Reservation Cancelled Successfully");
        System.out.println("Released Room ID: " + rollbackStack.peek());
    }
}

/* ---------- Main Application ---------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Cancellation Demo =====");

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancellationService = new CancellationService();

        // Simulated confirmed reservations
        Reservation r1 = new Reservation("RES301", "Rahul",
                "Single Room", "SI101");

        Reservation r2 = new Reservation("RES302", "Priya",
                "Double Room", "DO201");

        history.addReservation(r1);
        history.addReservation(r2);

        history.displayHistory();

        // Guest cancels booking
        cancellationService.cancelReservation("RES301", history, inventory);

        // Invalid cancellation attempt
        cancellationService.cancelReservation("RES999", history, inventory);

        // Duplicate cancellation
        cancellationService.cancelReservation("RES301", history, inventory);

        history.displayHistory();
        inventory.displayInventory();
    }
}