package aeterraes.console;

import aeterraes.controllers.CarController;
import aeterraes.controllers.OrderRequestController;
import aeterraes.controllers.UserController;
import aeterraes.entities.*;
import aeterraes.models.*;
import aeterraes.security.AuthService;
import aeterraes.services.CarService;
import aeterraes.services.OrderRequestService;
import aeterraes.services.UserService;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        CarService carService = new CarService();
        UserService userService = new UserService();
        OrderRequestService orderRequestService = new OrderRequestService();
        AuthService authService = new AuthService();

        initialize(userService, orderRequestService, carService, authService);

        CarController carController = new CarController(carService, authService);
        UserController userController = new UserController(userService, authService);
        OrderRequestController orderRequestController =
                new OrderRequestController(orderRequestService, authService);

        System.out.println("Welcome to Car Shop Service!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Select an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Enter first name: ");
                String firstName = scanner.nextLine();
                System.out.print("Enter last name: ");
                String lastName = scanner.nextLine();
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                int id = random.nextInt(10000);
                User newUser = new Customer(id, firstName, lastName, email, password, Role.USER,
                        Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
                userController.registerUser(newUser);
                //TO DO: ADD REGISTER LOGIC
                break;

            case 2:
                boolean loginSuccessful = false;
                User currentUser = null;
                while (!loginSuccessful) {
                    System.out.print("Enter email: ");
                    String userEmail = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String userPassword = scanner.nextLine();

                    LoginStatus loginStatus = authService.login(userEmail, userPassword);

                    switch (loginStatus) {
                        case AUTHENTICATED:
                            userController.loginUser(userEmail, userPassword);
                            loginSuccessful = true;
                            currentUser = userService.getUserByEmail(userEmail);
                            break;

                        case INVALID_CREDENTIALS:
                            System.out.println("Invalid credentials. Please try again.");
                            break;

                        case USER_NOT_FOUND:
                            System.out.println("User not found. Please check the email and try again.");
                            break;

                        default:
                            System.out.println("Unexpected error occurred. Please try again.");
                            break;
                    }
                }
                while (loginSuccessful) {
                    System.out.println("Welcome, " + currentUser.getEmail() + "!");
                    System.out.println("Select an option: ");
                    System.out.println("1. Perform an action");
                    System.out.println("2. Logout");
                    int userChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (userChoice) {
                        case 1:
                            Role role = currentUser.getRole();

                            if (role == Role.USER) {
                                System.out.println("Choose an option:");
                                System.out.println("1. View orders");
                                System.out.println("2. Make an order");
                                System.out.println("3. View available cars");

                                int customerChoice = scanner.nextInt();
                                scanner.nextLine();

                                switch (customerChoice) {
                                    case 1:
                                        orderRequestController.getOrdersByCustomerId(currentUser.getId());
                                        break;

                                    case 2:
                                        scanner.nextLine();
                                        System.out.println("Please enter the ID of the selected car: ");
                                        int carId = scanner.nextInt();
                                        scanner.nextLine();
                                        Car carForOrder = carService.getCarById(carId);
                                        Order newOrder = new Order(random.nextInt(),
                                                currentUser.getId(), List.of(carForOrder),
                                                carForOrder.getPrice(), OrderStatus.CREATED, new Date());
                                        orderRequestController.addOrder(newOrder);
                                        System.out.println("Do you want to pay for the order now?");
                                        System.out.println("1. Pay");
                                        System.out.println("2. Exit");
                                        System.out.println("3. Cancel the order");
                                        int payChoice = scanner.nextInt();
                                        switch (payChoice) {
                                            case 1:
                                                newOrder.setStatus(OrderStatus.PAID);
                                                orderRequestController.updateOrderById(newOrder.getId(), newOrder);
                                                System.out.println("Payment successful! Please wait for delivery.");
                                                break;

                                            case 2:
                                                System.out.println("Exiting without payment. Your order will be pending.");
                                                break;

                                            case 3:
                                                newOrder.setStatus(OrderStatus.CANCELLED);
                                                orderRequestController.updateOrderById(newOrder.getId(), newOrder);
                                                orderRequestController.deleteOrderById(newOrder.getId());
                                                System.out.println("You cancelled the order.");
                                                break;

                                            default:
                                                System.out.println("Invalid choice, exiting without payment.");
                                                break;
                                        }
                                        break;

                                    case 3:
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
                                        break;

                                    default:
                                        System.out.println("Invalid choice, please try again.");
                                        break;
                                }
                            } else if (role == Role.ADMIN) {
                                /*
                                 * TO DO: Add Admin logic
                                 */
                            } else if (role == Role.MANAGER) {
                                /*
                                 * TO DO: Add Manager logic
                                 */
                            }
                            break;

                        case 2:
                            System.out.println("Logging out...");
                            currentUser = null;
                            loginSuccessful = false;
                            break;

                        default:
                            System.out.println("Invalid choice, please try again.");
                            break;
                    }
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

    private static void initialize(UserService userService,
                                   OrderRequestService orderRequestService,
                                   CarService carService, AuthService authService) {

        Employee annaVolkova = new Employee(1, "Anna", "Volkova",
                "annavolkova1999@somemailservice.com", "password1234", Role.ADMIN);
        Employee maksimPlotnikov = new Employee(2, "Maksim", "Plotnikov",
                "maxplotnikov2000@somemailservice.com", "qwertyQ", Role.MANAGER);
        Customer alinaAlexandrova = new Customer(4, "Alina",
                "Alexandrova", "alinochkasolnyshko1998@somemailservice.com",
                "passwordpassword", Role.USER, Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());

        Car hyundaiSolaris = new Car("Hyundai", "Solaris",
                2018, 10000, "Blue", "Gas",
                100, 12, "Independent",
                "Mechanic", "Backward", 1025000, true);
        Car mazdaCX90 = new Car("Mazda", "CX-90", 2023,
                0, "Red", "Gas", 328,
                7, "Independent", "Automatic",
                "Independent", 8700000, true);

        Order order1 = new Order(1, 3, List.of(hyundaiSolaris), 1025000,
                OrderStatus.CREATED, new Date(2024 - 1900, 4, 6));

        ServiceRequest serviceRequest1 = new ServiceRequest(1, 3, 1,
                new Date(2024, 4, 6), UserRequestType.MAINTENANCE,
                "Fix engine problems", ServiceRequestStatus.CREATED);
        Customer romanRomanov = new Customer(3, "Roman", "Romanov",
                "romromych777@somemailservice.com", "RoMKrUteee",
                Role.USER, List.of(serviceRequest1), List.of(order1), List.of(hyundaiSolaris));

        authService.setAuthenticatedUsers(List.of(romanRomanov, alinaAlexandrova,
                maksimPlotnikov, annaVolkova));

        userService.addUser(annaVolkova);
        userService.addUser(maksimPlotnikov);
        userService.addUser(romanRomanov);
        userService.addUser(alinaAlexandrova);

        carService.addCar(1, hyundaiSolaris);
        carService.addCar(2, mazdaCX90);

        orderRequestService.addOrder(order1);
        orderRequestService.addServiceRequest(serviceRequest1);
    }
}