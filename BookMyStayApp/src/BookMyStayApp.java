/**
 * BookMyApp - Entry point for the Hotel Booking Management System.
 * This class demonstrates how a Java application starts execution
 * and displays a welcome message to the user.
 *
 * @author YourName
 * @version 1.0
 */
public class BookMyStayApp {

    /**
     * Main method - starting point of the application.
     * The JVM invokes this method to begin program execution.
     *
     * @param args Command line arguments (not used in this program)
     */
    public static void main(String[] args) {

        // Application name and version
        String appName = "Book My Stay App";
        String version = "v1.0";

        // Welcome message
        System.out.println("=================================");
        System.out.println("Welcome to " + appName + " Application");
        System.out.println("Hotel Booking Management System");
        System.out.println("Version: " + version);
        System.out.println("=================================");

        // Application end message
        System.out.println("Application started successfully.");
    }
}
