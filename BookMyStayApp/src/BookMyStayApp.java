/**
 * BookMyStayApp - Demonstrates add-on service selection
 * for confirmed reservations in the Hotel Booking System.
 */

import java.util.*;

/* ---------- Add-On Service Class ---------- */

class AddOnService {

    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void displayService() {
        System.out.println(serviceName + " : ₹" + price);
    }
}

/* ---------- Add-On Service Manager ---------- */

class AddOnServiceManager {

    // Map: ReservationID -> List of services
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    // Attach service to reservation
    public void addService(String reservationId, AddOnService service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());

        reservationServices.get(reservationId).add(service);

        System.out.println("Service added to reservation " + reservationId +
                " -> " + service.getServiceName());
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("\nAdd-On Services for Reservation " + reservationId);

        for (AddOnService s : services) {
            s.displayService();
        }
    }

    // Calculate additional cost
    public double calculateTotalServiceCost(String reservationId) {

        double total = 0;

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }
}

/* ---------- Main Application ---------- */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Add-On Services =====");

        // Example reservation IDs (generated during booking)
        String reservation1 = "RES101";
        String reservation2 = "RES102";

        // Create services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 1200);
        AddOnService spa = new AddOnService("Spa Access", 2000);

        // Service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Guest selects services
        manager.addService(reservation1, breakfast);
        manager.addService(reservation1, spa);

        manager.addService(reservation2, airportPickup);

        // Display services
        manager.displayServices(reservation1);
        manager.displayServices(reservation2);

        // Calculate service cost
        System.out.println("\nTotal Add-On Cost for " + reservation1 +
                " : ₹" + manager.calculateTotalServiceCost(reservation1));

        System.out.println("Total Add-On Cost for " + reservation2 +
                " : ₹" + manager.calculateTotalServiceCost(reservation2));
    }
}