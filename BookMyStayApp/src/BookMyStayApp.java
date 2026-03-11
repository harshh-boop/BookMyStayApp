/**
 * BookMyStayApp - Demonstrates validation and error handling
 * for booking operations in the Hotel Booking System.
 */

import java.util.*;

/* ---------- Custom Exception ---------- */

class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

/* ---------- Reservation Class ---------- */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
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
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) throws InvalidBookingException {

        int available = getAvailability(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for " + roomType);
        }

        inventory.put(roomType, available - 1);
    }
}

/* ---------- Invalid Booking Validator ---------- */

class BookingValidator {

    public static void validateReservation(Reservation reservation,
                                           RoomInventory inventory)
            throws InvalidBookingException {

        if (reservation.getGuestName() == null ||
                reservation.getGuestName().trim().isEmpty()) {

            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.isValidRoomType(reservation.getRoomType())) {

            throw new InvalidBookingException(
                    "Invalid room type: " + reservation.getRoomType());
        }
    }
}

/* ---------- Booking Service ---------- */

class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void confirmReservation(Reservation reservation)
            throws InvalidBookingException {

        // Validate input first
        BookingValidator.validateReservation(reservation, inventory);

        // Attempt allocation
        inventory.decrementRoom(reservation.getRoomType());

        System.out.println("Reservation Confirmed!");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + reservation.getRoomType());
        System.out.println("---------------------------");
    }
}

/* ---------- Main Application ---------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Validation Demo =====");

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        // Example reservations
        Reservation r1 = new Reservation("RES201", "Rahul", "Single Room");
        Reservation r2 = new Reservation("RES202", "", "Double Room");     // Invalid guest name
        Reservation r3 = new Reservation("RES203", "Priya", "Luxury Room"); // Invalid room type
        Reservation r4 = new Reservation("RES204", "Arjun", "Suite Room"); // No availability

        Reservation[] reservations = {r1, r2, r3, r4};

        for (Reservation r : reservations) {

            try {
                bookingService.confirmReservation(r);
            }
            catch (InvalidBookingException e) {

                System.out.println("Booking Failed for reservation: "
                        + r.getReservationId());
                System.out.println("Error: " + e.getMessage());
                System.out.println("---------------------------");
            }
        }

        System.out.println("System continues running safely.");
    }
}