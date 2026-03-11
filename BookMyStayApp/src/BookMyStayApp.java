/**
 * BookMyStayApp - Demonstrates booking history tracking
 * and reporting for confirmed reservations.
 */

import java.util.*;

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

    public void displayReservation() {
        System.out.println(
                "Reservation ID: " + reservationId +
                        " | Guest: " + guestName +
                        " | Room: " + roomType
        );
    }
}

/* ---------- Booking History ---------- */

class BookingHistory {

    // Stores confirmed reservations in order
    private List<Reservation> reservations = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation stored in history: " + reservation.getReservationId());
    }

    // Retrieve all reservations
    public List<Reservation> getReservations() {
        return reservations;
    }
}

/* ---------- Booking Report Service ---------- */

class BookingReportService {

    // Display complete booking history
    public void showAllBookings(List<Reservation> reservations) {

        System.out.println("\n===== Booking History =====");

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            r.displayReservation();
        }
    }

    // Generate summary report
    public void generateSummaryReport(List<Reservation> reservations) {

        System.out.println("\n===== Booking Summary Report =====");

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : reservations) {

            String roomType = r.getRoomType();

            roomCount.put(roomType,
                    roomCount.getOrDefault(roomType, 0) + 1);
        }

        for (String room : roomCount.keySet()) {
            System.out.println(room + " bookings : " + roomCount.get(room));
        }

        System.out.println("Total bookings : " + reservations.size());
    }
}

/* ---------- Main Application ---------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Booking History =====");

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings
        Reservation r1 = new Reservation("RES101", "Rahul", "Single Room");
        Reservation r2 = new Reservation("RES102", "Priya", "Double Room");
        Reservation r3 = new Reservation("RES103", "Arjun", "Suite Room");
        Reservation r4 = new Reservation("RES104", "Meera", "Single Room");

        // Store bookings
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);
        history.addReservation(r4);

        // Admin views booking history
        reportService.showAllBookings(history.getReservations());

        // Admin generates summary report
        reportService.generateSummaryReport(history.getReservations());
    }
}