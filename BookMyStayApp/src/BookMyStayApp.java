/**
 * BookMyStayApp - Demonstrates booking request intake using
 * a FIFO queue (First-Come-First-Served) for the Hotel Booking System.
 */

import java.util.LinkedList;
import java.util.Queue;

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

    public void displayReservation() {
        System.out.println("Guest : " + guestName + " | Requested Room : " + roomType);
    }
}

/* ---------- Booking Request Queue ---------- */

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display all queued requests
    public void displayRequests() {

        System.out.println("\n----- Booking Request Queue -----");

        if (requestQueue.isEmpty()) {
            System.out.println("No booking requests.");
            return;
        }

        for (Reservation r : requestQueue) {
            r.displayReservation();
        }

        System.out.println("---------------------------------");
    }
}

/* ---------- Main Application ---------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       BOOK MY STAY APP");
        System.out.println("    Hotel Booking System v1.0");
        System.out.println("=================================");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        Reservation r1 = new Reservation("Rahul", "Single Room");
        Reservation r2 = new Reservation("Priya", "Double Room");
        Reservation r3 = new Reservation("Arjun", "Suite Room");

        // Add requests to queue (FIFO)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queue
        bookingQueue.displayRequests();

        System.out.println("\nRequests are waiting for allocation...");
    }
}