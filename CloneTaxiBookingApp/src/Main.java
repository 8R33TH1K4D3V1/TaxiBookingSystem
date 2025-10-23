import com.taxibooking.controller.*;
import com.taxibooking.service.*;
import com.taxibooking.view.*;
import com.taxibooking.model.Driver;

import java.util.ArrayList;
import java.util.List;

/**
 * Entry point for the Taxi Booking System application.
 * Initializes services, controllers, menus, and starts the main menu.
 */
public final class Main {

    // Private constructor to prevent instantiation
    private Main() {}

    public static void main(final String[] args) {

        // Initialize Taxi service and pre-load demo taxis
        final TaxiService taxiService = new TaxiServiceImpl();
        taxiService.registerDemoTaxis();

        // Shared driver list
        final List<Driver> driverList = new ArrayList<>();
        final DriverServiceImpl driverService = new DriverServiceImpl(driverList);

        // Registration services using shared lists
        final DriverRegistrationService driverRegService = new DriverRegistrationServiceImpl(driverList);
        final TaxiRegistrationService taxiRegService = new TaxiRegistrationServiceImpl(taxiService.get());

        // Controllers
        final BookingController bookingController = new BookingController();
        final DriverController driverController = new DriverController(driverService);
        final TaxiController taxiController = new TaxiController(taxiService);
        final DriverRegistrationController driverRegController = new DriverRegistrationController(driverRegService);
        final TaxiRegistrationController taxiRegController = new TaxiRegistrationController(taxiRegService);

        // Menus
        final CustomerMenu customerMenu = new CustomerMenu(taxiService, bookingController);
        final AdminMenu adminMenu = new AdminMenu(
                taxiController,
                driverController,
                taxiRegController,
                driverRegController
        );
        final DriverMenu driverMenu = new DriverMenu(taxiService, bookingController, driverController);

        // Main menu
        final MainMenu mainMenu = new MainMenu(customerMenu, adminMenu, driverMenu);
        mainMenu.run();
    }
}
