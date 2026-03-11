/**
 * BookMyStayApp - Demonstrates basic room types and static availability
 * for the Hotel Booking Management System.
 *
 * @author YourName
 * @version 1.0
 */

/* Abstract Room class */
abstract class Room {

    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq.ft");
        System.out.println("Price     : ₹" + price + " per night");
    }
}

/* Single Room class */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 2000);
    }
}

/* Double Room class */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 3500);
    }
}

/* Suite Room class */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 600, 7000);
    }
}

/* Main Application Class */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("        BOOK MY STAY APP");
        System.out.println("      Hotel Booking System v1.0");
        System.out.println("====================================");

        // Creating room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleRoomAvailable = 5;
        int doubleRoomAvailable = 3;
        int suiteRoomAvailable = 2;

        System.out.println("\n--- Room Details & Availability ---\n");

        single.displayRoomDetails();
        System.out.println("Available : " + singleRoomAvailable);
        System.out.println("-----------------------------------");

        doubleRoom.displayRoomDetails();
        System.out.println("Available : " + doubleRoomAvailable);
        System.out.println("-----------------------------------");

        suite.displayRoomDetails();
        System.out.println("Available : " + suiteRoomAvailable);
        System.out.println("-----------------------------------");

        System.out.println("\nThank you for using Book My Stay App!");
    }
}