/**
 * BookMyStayApp - Demonstrates concurrent booking simulation
 * with thread-safe inventory updates.
 */

import java.util.*;
import java.util.concurrent.*;

/* ---------- Reservation Class ---------- */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }

    public void displayReservation() {
        System.out.println("Reservation: " + reservationId + " | Guest: " + guestName +
                " | Room Type: " + roomType + " | Room ID: " + roomId);
    }
}

/* ---------- Thread-Safe Room Inventory ---------- */

class RoomInventory {

    private final Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    // Thread-safe method to allocate a room
    public synchronized boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    // Thread-safe method to increment room (for rollback)
    public synchronized void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public synchronized void displayInventory() {
        System.out.println("\nInventory Status:");
        inventory.forEach((type, count) -> System.out.println(type + ": " + count));
    }
}

/* ---------- Booking Queue ---------- */

class BookingQueue {
    private final Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addReservation(Reservation r) {
        queue.offer(r);
    }

    public synchronized Reservation pollReservation() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

/* ---------- Booking Processor Runnable ---------- */

class BookingProcessor implements Runnable {

    private BookingQueue bookingQueue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue bookingQueue, RoomInventory inventory) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            Reservation r;

            // Fetch reservation from queue
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) break;
                r = bookingQueue.pollReservation();
            }

            // Attempt allocation
            boolean success = inventory.allocateRoom(r.getRoomType());

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " allocated " + r.getRoomType() +
                        " to " + r.getGuestName());
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " failed to allocate " + r.getRoomType() +
                        " for " + r.getGuestName() + " (No availability)");
            }

            try {
                Thread.sleep(50); // Simulate processing time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

/* ---------- Main Application ---------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Concurrent Booking Demo =====");

        RoomInventory inventory = new RoomInventory();
        BookingQueue bookingQueue = new BookingQueue();

        // Simulate multiple booking requests
        Reservation[] requests = {
                new Reservation("RES401", "Alice", "Single Room", "SI101"),
                new Reservation("RES402", "Bob", "Double Room", "DO201"),
                new Reservation("RES403", "Charlie", "Suite Room", "SU301"),
                new Reservation("RES404", "Diana", "Single Room", "SI102"),
                new Reservation("RES405", "Ethan", "Double Room", "DO202"),
                new Reservation("RES406", "Fiona", "Single Room", "SI103") // May fail due to availability
        };

        // Add reservations to queue
        for (Reservation r : requests) bookingQueue.addReservation(r);

        // Create multiple threads to process bookings concurrently
        Thread t1 = new Thread(new BookingProcessor(bookingQueue, inventory), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(bookingQueue, inventory), "Thread-2");
        Thread t3 = new Thread(new BookingProcessor(bookingQueue, inventory), "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Show final inventory state
        inventory.displayInventory();
        System.out.println("All bookings processed safely.");
    }
}