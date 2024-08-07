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
        OrderRequestController orderRequestController = new OrderRequestController(orderRequestService, authService);

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
                handleUserActions(scanner, random, newUser, carController, orderRequestController, userController, carService);
                break;

            case 2:
                User currentUser = handleLogin(scanner, authService, userController, userService);
                if (currentUser != null) {
                    handleUserActions(scanner, random, currentUser, carController, orderRequestController, userController, carService);
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

    private static User handleLogin(Scanner scanner, AuthService authService, UserController userController, UserService userService) {
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
        return currentUser;
    }

    private static void handleUserActions(Scanner scanner, Random random, User currentUser, CarController carController,
                                          OrderRequestController orderRequestController, UserController userController, CarService carService) {
        boolean loggedIn = true;
        while (loggedIn) {
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
                                System.out.println("Please enter the ID of the selected car: ");
                                int carId = scanner.nextInt();
                                scanner.nextLine();
                                Car carForOrder = carService.getCarById(carId);
                                Order newOrder = new Order(random.nextInt(10000), currentUser.getId(),
                                        List.of(carForOrder), carForOrder.getPrice(), OrderStatus.CREATED, new Date());
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
                    } else if (role == Role.MANAGER) {
                        System.out.println("Choose an option:");
                        System.out.println("1. Users actions");
                        System.out.println("2. Orders actions");
                        System.out.println("3. Cars actions");

                        int managerChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (managerChoice) {
                            case 1:
                                System.out.println("Choose user action:");
                                System.out.println("1. Get number of orders by user ID");
                                System.out.println("2. Get customers by request type");
                                System.out.println("3. Get customers by order status");
                                System.out.println("4. Get all customers");
                                System.out.println("5. Get users by first name");
                                System.out.println("6. Get users by last name");

                                int userAction = scanner.nextInt();
                                scanner.nextLine();

                                switch (userAction) {
                                    case 1:
                                        System.out.print("Enter user ID: ");
                                        int userId = scanner.nextInt();
                                        userController.getNumberOfOrdersByUserId(userId, currentUser);
                                        break;

                                    case 2:
                                        System.out.print("Enter request type: ");
                                        UserRequestType requestType = UserRequestType.valueOf(scanner.next().toUpperCase());
                                        userController.getCustomersByRequestType(requestType, currentUser);
                                        break;

                                    case 3:
                                        System.out.print("Enter order status: ");
                                        OrderStatus status = OrderStatus.valueOf(scanner.next().toUpperCase());
                                        userController.getCustomersByOrderStatus(status, currentUser);
                                        break;

                                    case 4:
                                        userController.getAllCustomers(currentUser);
                                        break;

                                    case 5:
                                        System.out.print("Enter first name: ");
                                        String firstName = scanner.nextLine();
                                        userController.getUsersByFirstName(firstName, currentUser);
                                        break;

                                    case 6:
                                        System.out.print("Enter last name: ");
                                        String lastName = scanner.nextLine();
                                        userController.getUsersByLastName(lastName, currentUser);
                                        break;

                                    default:
                                        System.out.println("Invalid choice, please try again.");
                                        break;
                                }
                                break;

                            case 2:
                                System.out.println("Choose order action:");
                                System.out.println("1. Get service requests by customer ID");
                                System.out.println("2. Get service requests by car ID");
                                System.out.println("3. Get service requests by status");
                                System.out.println("4. Get orders by customer ID");
                                System.out.println("5. Get orders by status");

                                int orderAction = scanner.nextInt();
                                scanner.nextLine();

                                switch (orderAction) {
                                    case 1:
                                        System.out.print("Enter customer ID: ");
                                        int customerId = scanner.nextInt();
                                        orderRequestController.getServiceRequestsByCustomerId(customerId);
                                        break;

                                    case 2:
                                        System.out.print("Enter car ID: ");
                                        int carId = scanner.nextInt();
                                        orderRequestController.getServiceRequestsByCarId(carId);
                                        break;

                                    case 3:
                                        System.out.print("Enter service request status: ");
                                        ServiceRequestStatus requestStatus = ServiceRequestStatus.valueOf(scanner.next().toUpperCase());
                                        orderRequestController.getServiceRequestsByStatus(requestStatus);
                                        break;

                                    case 4:
                                        System.out.print("Enter customer ID: ");
                                        int orderCustomerId = scanner.nextInt();
                                        orderRequestController.getOrdersByCustomerId(orderCustomerId);
                                        break;

                                    case 5:
                                        System.out.print("Enter order status: ");
                                        OrderStatus orderStatus = OrderStatus.valueOf(scanner.next().toUpperCase());
                                        orderRequestController.getOrdersByStatus(orderStatus);
                                        break;

                                    default:
                                        System.out.println("Invalid choice, please try again.");
                                        break;
                                }
                                break;

                            case 3:
                                System.out.println("Choose car action:");
                                System.out.println("1. Get cars by make");
                                System.out.println("2. Get cars by model");
                                System.out.println("3. Get cars by year");
                                System.out.println("4. Get cars by mileage");
                                System.out.println("5. Get cars by color");
                                System.out.println("6. Get cars by engine");
                                System.out.println("7. Get cars by horsepower");
                                System.out.println("8. Get cars by acceleration");
                                System.out.println("9. Get cars by drive train");
                                System.out.println("10. Get cars by price range");

                                int carAction = scanner.nextInt();
                                scanner.nextLine();

                                switch (carAction) {
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
                                        carController.getCarsByYear(year);
                                        break;

                                    case 4:
                                        System.out.print("Enter mileage: ");
                                        double mileage = scanner.nextDouble();
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
                                        carController.getCarsByHorsepower(horsepower);
                                        break;

                                    case 8:
                                        System.out.print("Enter acceleration: ");
                                        int acceleration = scanner.nextInt();
                                        carController.getCarsByAcceleration(acceleration);
                                        break;

                                    case 9:
                                        System.out.print("Enter drive train type: ");
                                        String driveTrain = scanner.nextLine();
                                        carController.getCarsByDriveTrain(driveTrain);
                                        break;

                                    case 10:
                                        System.out.print("Enter minimum price: ");
                                        double minPrice = scanner.nextDouble();
                                        System.out.print("Enter maximum price: ");
                                        double maxPrice = scanner.nextDouble();
                                        carController.getCarsByPriceRange(minPrice, maxPrice);
                                        break;

                                    default:
                                        System.out.println("Invalid choice, please try again.");
                                        break;
                                }
                                break;

                            default:
                                System.out.println("Invalid option. Please choose a valid option.");
                                break;
                        }
                    } else if (role == Role.ADMIN) {
                        System.out.println("Choose an option:");
                        System.out.println("1. Users actions");
                        System.out.println("2. Cars action");
                        System.out.println("3. Orders actions");

                        int adminChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (adminChoice) {
                            case 1:
                                System.out.println("Choose user action:");
                                System.out.println("1. Add user");
                                System.out.println("2. Update user");
                                System.out.println("3. Delete user");
                                System.out.println("4. View users by first name");
                                System.out.println("5. View users by last name");

                                int userAction = scanner.nextInt();
                                scanner.nextLine();

                                switch (userAction) {
                                    case 1:
                                        System.out.print("Enter user details:");
                                        int userId = scanner.nextInt();
                                        String userFirstName = scanner.next();
                                        String userLastName = scanner.next();
                                        String userMail = scanner.next();
                                        String userPass = scanner.next();
                                        Role userRole = Role.valueOf(scanner.next());
                                        User newUser = new Customer(userId, userFirstName, userLastName, userMail, userPass, userRole,
                                                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
                                        userController.addUser(newUser, currentUser);
                                        break;

                                    case 2:
                                        System.out.print("Enter user ID to update: ");
                                        int updateUserId = scanner.nextInt();
                                        System.out.print("Enter updated user details:");
                                        userFirstName = scanner.next();
                                        userLastName = scanner.next();
                                        userMail = scanner.next();
                                        userPass = scanner.next();
                                        userRole = Role.valueOf(scanner.next());
                                        if (userRole == Role.ADMIN || userRole == Role.MANAGER) {
                                            Employee newEmployee = new Employee(updateUserId, userFirstName, userLastName, userMail, userPass, userRole);
                                            userController.updateEmployeeById(updateUserId, newEmployee, currentUser);
                                        } else {
                                            newUser = new Customer(updateUserId, userFirstName, userLastName, userMail, userPass, userRole,
                                                    Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
                                            userController.updateUserById(updateUserId, newUser, currentUser);

                                        }
                                        break;

                                    case 3:
                                        System.out.print("Enter user ID to delete: ");
                                        int deleteUserId = scanner.nextInt();
                                        userController.deleteUserById(deleteUserId, currentUser);
                                        break;

                                    case 4:
                                        System.out.print("Enter first name: ");
                                        String firstName = scanner.nextLine();
                                        userController.getUsersByFirstName(firstName, currentUser);
                                        break;

                                    case 5:
                                        System.out.print("Enter last name: ");
                                        String lastName = scanner.nextLine();
                                        userController.getUsersByLastName(lastName, currentUser);
                                        break;

                                    default:
                                        System.out.println("Invalid choice, please try again.");
                                        break;
                                }
                                break;

                            case 2:
                                System.out.println("Choose car action:");
                                System.out.println("1. Add car");
                                System.out.println("2. Update car");
                                System.out.println("3. Delete car");
                                System.out.println("4. View car by ID");
                                System.out.println("5. View all cars");
                                System.out.println("6. View cars by make");
                                System.out.println("7. View cars by model");
                                System.out.println("8. View cars by year");
                                System.out.println("9. View cars by mileage");
                                System.out.println("10. View cars by color");
                                System.out.println("11. View cars by engine");
                                System.out.println("12. View cars by horsepower");
                                System.out.println("13. View cars by acceleration");
                                System.out.println("14. View cars by drive train");

                                int carAction = scanner.nextInt();
                                scanner.nextLine();

                                switch (carAction) {
                                    case 1:
                                        System.out.print("Enter car details:");
                                        String make = scanner.next();
                                        String model = scanner.next();
                                        int year = scanner.nextInt();
                                        double mileage = scanner.nextDouble();
                                        String color = scanner.next();
                                        String engine = scanner.next();
                                        int horsepower = scanner.nextInt();
                                        int acceleration = scanner.nextInt();
                                        String suspension = scanner.next();
                                        String gear = scanner.next();
                                        String driveTrain = scanner.next();
                                        double price = scanner.nextDouble();
                                        boolean availableForSale = scanner.nextBoolean();
                                        Car newCar = new Car(make, model, year, mileage, color, engine, horsepower, acceleration, suspension, gear, driveTrain, price, availableForSale);
                                        carController.addCar(random.nextInt(), newCar, currentUser);
                                        break;

                                    case 2:
                                        System.out.print("Enter car ID to update: ");
                                        int updateCarId = scanner.nextInt();
                                        System.out.print("Enter updated car details: ");
                                        make = scanner.next();
                                        model = scanner.next();
                                        year = scanner.nextInt();
                                        mileage = scanner.nextDouble();
                                        color = scanner.next();
                                        engine = scanner.next();
                                        horsepower = scanner.nextInt();
                                        acceleration = scanner.nextInt();
                                        suspension = scanner.next();
                                        gear = scanner.next();
                                        driveTrain = scanner.next();
                                        price = scanner.nextDouble();
                                        availableForSale = scanner.nextBoolean();
                                        Car updatedCar = new Car(make, model, year, mileage, color, engine, horsepower, acceleration, suspension, gear, driveTrain, price, availableForSale);
                                        carController.updateCar(updateCarId, updatedCar, currentUser);
                                        break;

                                    case 3:
                                        System.out.print("Enter car ID to delete: ");
                                        int deleteCarId = scanner.nextInt();
                                        carController.deleteCar(deleteCarId, currentUser);
                                        break;

                                    case 4:
                                        System.out.print("Enter car ID to view: ");
                                        int viewCarId = scanner.nextInt();
                                        carController.getCarById(viewCarId);
                                        break;

                                    case 5:
                                        carController.getAllCars();
                                        break;

                                    case 6:
                                        System.out.print("Enter make: ");
                                        String carMake = scanner.nextLine();
                                        carController.getCarsByMake(carMake);
                                        break;

                                    case 7:
                                        System.out.print("Enter model: ");
                                        String carModel = scanner.nextLine();
                                        carController.getCarsByModel(carModel);
                                        break;

                                    case 8:
                                        System.out.print("Enter year: ");
                                        int carYear = scanner.nextInt();
                                        scanner.nextLine();
                                        carController.getCarsByYear(carYear);
                                        break;

                                    case 9:
                                        System.out.print("Enter mileage: ");
                                        double carMileage = scanner.nextDouble();
                                        scanner.nextLine();
                                        carController.getCarsByMileage(carMileage);
                                        break;

                                    case 10:
                                        System.out.print("Enter color: ");
                                        String carColor = scanner.nextLine();
                                        carController.getCarsByColor(carColor);
                                        break;

                                    case 11:
                                        System.out.print("Enter engine type: ");
                                        String carEngine = scanner.nextLine();
                                        carController.getCarsByEngine(carEngine);
                                        break;

                                    case 12:
                                        System.out.print("Enter horsepower: ");
                                        int carHorsepower = scanner.nextInt();
                                        scanner.nextLine();
                                        carController.getCarsByHorsepower(carHorsepower);
                                        break;

                                    case 13:
                                        System.out.print("Enter acceleration: ");
                                        int carAcceleration = scanner.nextInt();
                                        scanner.nextLine();
                                        carController.getCarsByAcceleration(carAcceleration);
                                        break;

                                    case 14:
                                        System.out.print("Enter drive train type: ");
                                        String carDriveTrain = scanner.nextLine();
                                        carController.getCarsByDriveTrain(carDriveTrain);
                                        break;

                                    default:
                                        System.out.println("Invalid choice, please try again.");
                                        break;
                                }
                                break;

                            case 3:
                                System.out.println("Choose order action:");
                                System.out.println("1. View all orders");
                                System.out.println("2. View orders by customer ID");
                                System.out.println("3. View orders by status");

                                int orderAction = scanner.nextInt();
                                scanner.nextLine();

                                switch (orderAction) {
                                    case 1:
                                        orderRequestController.getAllOrders(currentUser);
                                        break;

                                    case 2:
                                        System.out.print("Enter customer ID: ");
                                        int customerId = scanner.nextInt();
                                        orderRequestController.getOrdersByCustomerId(customerId);
                                        break;

                                    case 3:
                                        System.out.print("Enter order status: ");
                                        OrderStatus status = OrderStatus.valueOf(scanner.next());
                                        orderRequestController.getOrdersByStatus(status);
                                        break;

                                    default:
                                        System.out.println("Invalid choice, please try again.");
                                        break;
                                }
                                break;

                            default:
                                System.out.println("Invalid option. Please choose a valid option.");
                                break;
                        }
                    }
                    break;

                case 2:
                    System.out.println("Logging out...");
                    loggedIn = false;
                    break;

                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
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

        //authService.setAuthenticatedUsers(List.of(romanRomanov, alinaAlexandrova,maksimPlotnikov, annaVolkova));
        authService.getAuthenticatedUsers().addAll(List.of(
                romanRomanov, alinaAlexandrova,maksimPlotnikov, annaVolkova));

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
