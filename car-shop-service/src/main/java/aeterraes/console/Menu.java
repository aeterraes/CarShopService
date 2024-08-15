package aeterraes.console;

import aeterraes.controllers.*;
import aeterraes.dataaccess.LiquibaseConfig;
import aeterraes.dataaccess.entities.Car;
import aeterraes.dataaccess.entities.Customer;
import aeterraes.dataaccess.entities.Employee;
import aeterraes.dataaccess.entities.Order;
import aeterraes.dataaccess.models.OrderStatus;
import aeterraes.dataaccess.models.Role;
import aeterraes.dataaccess.repositories.*;
import aeterraes.security.AuthService;
import aeterraes.security.LoginStatus;
import aeterraes.services.*;

import java.sql.Date;
import java.util.Scanner;

public class Menu {

    private final AuthService authService;
    private final CustomerController customerController;
    private final EmployeeController employeeController;
    private final CarController carController;
    private final OrderController orderController;
    private final ServiceRequestController serviceRequestController;

    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final CarService carService;
    private final OrderService orderService;
    private final ServiceRequestService serviceRequestService;

    private Scanner scanner = new Scanner(System.in);
    private Customer currentCustomer;
    private Employee currentEmployee;

    public Menu(AuthService authService, CustomerController customerController,
                EmployeeController employeeController, CarController carController,
                OrderController orderController, ServiceRequestController serviceRequestController,
                CustomerService customerService, EmployeeService employeeService, CarService carService,
                OrderService orderService, ServiceRequestService serviceRequestService) {
        this.authService = authService;
        this.customerController = customerController;
        this.employeeController = employeeController;
        this.carController = carController;
        this.orderController = orderController;
        this.serviceRequestController = serviceRequestController;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.carService = carService;
        this.orderService = orderService;
        this.serviceRequestService = serviceRequestService;
    }

    public static Menu createMenu() {
        LiquibaseConfig liquibaseConfig = new LiquibaseConfig();
        CarService carService = new CarService(new CarRepository(liquibaseConfig));
        CustomerService customerService = new CustomerService(new CustomerRepository(liquibaseConfig));
        EmployeeService employeeService = new EmployeeService(new EmployeeRepository(liquibaseConfig));
        OrderService orderService = new OrderService(new OrderRepository(liquibaseConfig));
        ServiceRequestService serviceRequestService = new ServiceRequestService(new ServiceRequestRepository(liquibaseConfig));

        CarController carController = new CarController(carService);
        CustomerController customerController = new CustomerController(customerService);
        EmployeeController employeeController = new EmployeeController(employeeService);
        OrderController orderController = new OrderController(orderService);
        ServiceRequestController serviceRequestController = new ServiceRequestController(serviceRequestService);

        AuthService authService = new AuthService(customerController, employeeController, customerService, employeeService);

        return new Menu(authService, customerController, employeeController, carController, orderController, serviceRequestController,
                customerService, employeeService, carService, orderService, serviceRequestService);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("Welcome to the Car Shop Service!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleRegistration();
                    break;

                case 2:
                    handleLogin();
                    if (currentCustomer != null) {
                        handleCustomerActions();
                    } else if (currentEmployee != null) {
                        handleEmployeeActions();
                    }
                    break;

                case 3:
                    System.out.println("Exiting program...");
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }

    private void handleRegistration() {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (USER/ADMIN/MANAGER): ");
        String role = scanner.nextLine();

        LoginStatus status = authService.register(firstName, lastName, email, password, role);
        if (status == LoginStatus.AUTHENTICATED) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed: " + status);
        }
    }

    private void handleLogin() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (USER/ADMIN/MANAGER): ");
        String role = scanner.nextLine();

        LoginStatus status = authService.login(email, password, role);
        if (status == LoginStatus.AUTHENTICATED) {
            System.out.println("Login successful!");
            if (role.equals("USER")) {
                currentCustomer = customerService.getCustomerByEmail(email);
            } else {
                currentEmployee = employeeService.getEmployeeByEmail(email);
            }
        } else {
            System.out.println("Login failed: " + status);
        }
    }

    private void handleCustomerActions() {
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. View orders");
            System.out.println("2. Make an order");
            System.out.println("3. View available cars");
            System.out.println("4. Exit");

            int customerChoice = scanner.nextInt();
            scanner.nextLine();

            switch (customerChoice) {
                case 1:
                    orderController.getOrderByCustomerId(currentCustomer.getCustomerId());
                    break;

                case 2:
                    makeOrder();
                    break;

                case 3:
                    viewAvailableCars();
                    break;

                case 4:
                    System.out.println("Exiting program...");
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }

    private void makeOrder() {
        System.out.println("Please enter the ID of the selected car: ");
        int carId = scanner.nextInt();
        scanner.nextLine();
        Car carForOrder = carService.getCarById(carId);
        if (carForOrder == null) {
            System.out.println("Car not found.");
            return;
        }
        Order newOrder = new Order(0, currentCustomer.getCustomerId(), carId,
                carForOrder.getPrice(), OrderStatus.CREATED, new Date(System.currentTimeMillis()));
        orderController.addOrder(newOrder);

        System.out.println("Do you want to pay for the order now?");
        System.out.println("1. Pay");
        System.out.println("2. Exit");
        System.out.println("3. Cancel the order");
        int payChoice = scanner.nextInt();
        scanner.nextLine();
        switch (payChoice) {
            case 1:
                newOrder = orderService.getOrderByCustomerId(currentCustomer.getCustomerId());
                orderController.updateOrderStatus(newOrder.getOrderId(), OrderStatus.PAID);
                carController.changeCarAvailability(carId, false);
                System.out.println("Payment successful! Please wait for delivery.");
                break;

            case 2:
                System.out.println("Exiting without payment. Your order will be pending.");
                break;

            case 3:
                newOrder = orderService.getOrderByCustomerId(currentCustomer.getCustomerId());
                orderController.updateOrderStatus(newOrder.getOrderId(), OrderStatus.PAID);
                orderController.deleteOrder(newOrder.getOrderId());
                System.out.println("You cancelled the order.");
                break;

            default:
                System.out.println("Invalid choice, exiting without payment.");
                break;
        }
    }

    private void viewAvailableCars() {
        System.out.println("Please select the parameter by which you would like to view cars: ");
        System.out.println("1. By Make");
        System.out.println("2. By Model");
        System.out.println("3. By Year");
        System.out.println("4. By Mileage");
        System.out.println("5. By Color");
        System.out.println("6. By Engine");
        System.out.println("7. By Horsepower");
        System.out.println("8. By Acceleration");
        System.out.println("9. By Drive Train");

        int queryType = scanner.nextInt();
        scanner.nextLine();

        switch (queryType) {
            case 1:
                System.out.print("Enter make: ");
                String make = scanner.nextLine();
                carController.getCarsByMake(make);
                break;

            case 2:
                System.out.print("Enter model: ");
                String model = scanner.nextLine();
                carController.getCarsByModel(model);
                break;

            case 3:
                System.out.print("Enter year: ");
                int year = scanner.nextInt();
                scanner.nextLine();
                carController.getCarsByYear(year);
                break;

            case 4:
                System.out.print("Enter mileage: ");
                double mileage = scanner.nextDouble();
                scanner.nextLine();
                carController.getCarsByMileage(mileage);
                break;

            case 5:
                System.out.print("Enter color: ");
                String color = scanner.nextLine();
                carController.getCarsByColor(color);
                break;

            case 6:
                System.out.print("Enter engine type: ");
                String engine = scanner.nextLine();
                carController.getCarsByEngine(engine);
                break;

            case 7:
                System.out.print("Enter horsepower: ");
                int horsepower = scanner.nextInt();
                scanner.nextLine();
                carController.getCarsByHorsepower(horsepower);
                break;

            case 8:
                System.out.print("Enter acceleration: ");
                int acceleration = scanner.nextInt();
                scanner.nextLine();
                carController.getCarsByAcceleration(acceleration);
                break;

            case 9:
                System.out.print("Enter drive train type: ");
                String driveTrain = scanner.nextLine();
                carController.getCarsByDriveTrain(driveTrain);
                break;

            default:
                System.out.println("Invalid choice, please try again.");
                break;
        }
    }

    private void handleEmployeeActions() {
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Users actions");
            System.out.println("2. Orders actions");
            System.out.println("3. Cars actions");
            System.out.println("4. Exit");

            int employeeChoice = scanner.nextInt();
            scanner.nextLine();

            switch (employeeChoice) {
                case 1:
                    handleUserActions();
                    break;

                case 2:
                    handleOrderActions();
                    break;

                case 3:
                    handleCarActions();
                    break;

                case 4:
                    System.out.println("Exiting program...");
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }

    private void handleUserActions() {

        System.out.println("Choose an option:");
        System.out.println("1. Get all customers");
        System.out.println("2. Get customer by ID");
        System.out.println("3. Get customer by email");
        System.out.println("4. Exit");

        int userActionChoice = scanner.nextInt();
        scanner.nextLine();

        switch (userActionChoice) {
            case 1:
                customerController.getAllCustomers();
                break;

            case 2:
                System.out.print("Enter customer ID: ");
                int customerId = scanner.nextInt();
                scanner.nextLine();
                customerController.getCustomerById(customerId);
                break;

            case 3:
                System.out.print("Enter customer email: ");
                String email = scanner.nextLine();
                customerController.getCustomerByEmail(email);
                break;

            case 4:
                System.out.println("Exiting...");
                return;

            default:
                System.out.println("Invalid choice, please try again.");
                break;
        }
    }

    private void handleOrderActions() {
        System.out.println("Choose an option:");
        System.out.println("1. View all orders");
        System.out.println("2. View order by ID");
        System.out.println("3. View orders by status");
        System.out.println("4. View orders by date");
        System.out.println("5. View orders by price");
        System.out.println("6. Exit");

        int orderActionChoice = scanner.nextInt();
        scanner.nextLine();

        switch (orderActionChoice) {
            case 1:
                orderController.getAllOrders();
                break;

            case 2:
                System.out.print("Enter order ID: ");
                int orderId = scanner.nextInt();
                scanner.nextLine();
                 orderController.getOrderById(orderId);
                break;

            case 3:
                System.out.print("Enter order status: ");
                String status = scanner.nextLine();
                orderController.getOrdersByStatus(OrderStatus.valueOf(status));
                break;

            case 4:
                System.out.print("Enter start date (YYYY-MM-DD): ");
                Date startDate = Date.valueOf(scanner.nextLine());
                System.out.print("Enter end date (YYYY-MM-DD): ");
                Date endDate = Date.valueOf(scanner.nextLine());
                orderController.getOrdersByDateRange(startDate, endDate);
                break;

            case 5:
                System.out.print("Enter start price: ");
                double startPrice = scanner.nextDouble();
                scanner.nextLine();
                double endPrice = scanner.nextDouble();
                scanner.nextLine();
                orderController.getOrdersByPriceRange(startPrice, endPrice);
                break;

            case 6:
                System.out.println("Exiting...");
                return;

            default:
                System.out.println("Invalid choice, please try again.");
                break;
        }
    }

    private void handleCarActions() {
        System.out.println("Choose an option:");
        System.out.println("1. View all cars");
        System.out.println("2. Add a new car");
        System.out.println("3. Update car details");
        System.out.println("4. Delete a car");
        System.out.println("5. View available cars");
        System.out.println("6. Exit");

        int carActionChoice = scanner.nextInt();
        scanner.nextLine();


        switch (carActionChoice) {
            case 1:
                carController.getAllCars();
                break;

            case 2:
                if (currentEmployee.getRole().equals(Role.ADMIN)) {
                    System.out.print("Enter make: ");
                    String make = scanner.nextLine();
                    System.out.print("Enter model: ");
                    String model = scanner.nextLine();
                    System.out.print("Enter year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter mileage: ");
                    double mileage = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter color: ");
                    String color = scanner.nextLine();
                    System.out.print("Enter engine type: ");
                    String engine = scanner.nextLine();
                    System.out.print("Enter horsepower: ");
                    int horsepower = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter acceleration: ");
                    int acceleration = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter suspension type: ");
                    String suspension = scanner.nextLine();
                    System.out.print("Enter gear type: ");
                    String gear = scanner.nextLine();
                    System.out.print("Enter drive train type: ");
                    String driveTrain = scanner.nextLine();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();

                    Car newCar = new Car(0, make, model, year, mileage,
                            color, engine, horsepower, acceleration, suspension,
                            gear, driveTrain, price, true);
                    carController.addCar(newCar);
                } else {
                    System.out.println("You do not have permission to add a car.");
                }
                break;

            case 3:
                if (currentEmployee.getRole().equals(Role.ADMIN)){
                    System.out.print("Enter car ID to update: ");
                    int carId = scanner.nextInt();
                    scanner.nextLine();
                    Car carToUpdate = carService.getCarById(carId);
                    System.out.print("Enter new data: ");
                    System.out.print("Enter make: ");
                    String make = scanner.nextLine();
                    System.out.print("Enter model: ");
                    String model = scanner.nextLine();
                    System.out.print("Enter year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter mileage: ");
                    double mileage = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter color: ");
                    String color = scanner.nextLine();
                    System.out.print("Enter engine type: ");
                    String engine = scanner.nextLine();
                    System.out.print("Enter horsepower: ");
                    int horsepower = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter acceleration: ");
                    int acceleration = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter suspension type: ");
                    String suspension = scanner.nextLine();
                    System.out.print("Enter gear type: ");
                    String gear = scanner.nextLine();
                    System.out.print("Enter drive train type: ");
                    String driveTrain = scanner.nextLine();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    carToUpdate.setMake(make);
                    carToUpdate.setModel(model);
                    carToUpdate.setYear(year);
                    carToUpdate.setMileage(mileage);
                    carToUpdate.setColor(color);
                    carToUpdate.setEngine(engine);
                    carToUpdate.setHorsepower(horsepower);
                    carToUpdate.setAcceleration(acceleration);
                    carToUpdate.setSuspension(suspension);
                    carToUpdate.setGear(gear);
                    carToUpdate.setDriveTrain(driveTrain);
                    carToUpdate.setPrice(price);
                    carController.updateCar(carToUpdate);
                } else {
                    System.out.println("You do not have permission to update a car.");
                }
                break;

            case 4:
                if (currentEmployee.getRole().equals(Role.ADMIN)) {
                    System.out.print("Enter car ID to delete: ");
                    int deleteCarId = scanner.nextInt();
                    scanner.nextLine();
                    carController.deleteCar(deleteCarId);
                } else {
                    System.out.println("You do not have permission to delete a car.");
                }
                break;

            case 5:
                viewAvailableCars();

            case 6:
                System.out.println("Exiting...");
                return;

            default:
                System.out.println("Invalid choice, please try again.");
                break;
        }
    }
}

