package aeterraes.controllers;

import aeterraes.entities.Customer;
import aeterraes.entities.Employee;
import aeterraes.entities.User;
import aeterraes.logging.LoggerConfig;
import aeterraes.models.LoginStatus;
import aeterraes.models.OrderStatus;
import aeterraes.models.Role;
import aeterraes.models.UserRequestType;
import aeterraes.security.AuthService;
import aeterraes.services.UserService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.logging.Logger;

@Setter
@Getter
public class UserController {

    private static final Logger logger = LoggerConfig.getLogger();
    private final UserService userService;
    private final AuthService authorizationService;

    /**
     * Constructor for UserController
     *
     * @param userService          Service for managing users
     * @param authorizationService Service for authentication and authorization
     */
    public UserController(UserService userService, AuthService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    /**
     * Retrieve and print all users
     *
     * @param currentUser The current user performing the action
     */
    public void getAllUsers(User currentUser) {
        if (authorizationService.hasAccess(currentUser, Role.MANAGER)) {
            logger.info("Get all users");
            List<User> users = userService.getAllUsers();
            System.out.println("All Users:");
            users.forEach(user -> System.out.println(user.toString()));
        } else {
            logger.warning("Access denied");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Retrieve and print user information by ID
     *
     * @param id          The ID of the user
     * @param currentUser The current user performing the action
     */
    public void getUserById(int id, User currentUser) {
        if (authorizationService.hasAccess(currentUser, Role.MANAGER)) {
            logger.info("Get user by id: " + id);
            User user = userService.getUserById(id);
            if (user != null) {
                logger.info("User: " + user.toString());
                System.out.println("User Info by ID " + id + ":");
                System.out.println(user);
            } else {
                logger.warning("User with ID " + id + " not found");
                System.out.println("User with ID " + id + " not found");
            }
        } else {
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Register a new user
     *
     * @param newUser The user to register
     */
    public void registerUser(User newUser) {
        logger.info("Registering new user: " + newUser.toString());
        LoginStatus status = authorizationService.register(newUser);
        if (status == LoginStatus.AUTHENTICATED) {
            logger.info("User registered: " + newUser.toString());
            System.out.println("User registered successfully:");
            System.out.println(newUser.toString());
        } else {
            logger.warning("Registration failed: " + status);
            System.out.println("Registration failed: " + status);
        }
    }

    /**
     * Log in a user
     *
     * @param email    The email of the user
     * @param password The password of the user
     */
    public void loginUser(String email, String password) {
        logger.info("Logging in user: " + email);
        LoginStatus status = authorizationService.login(email, password);
        if (status == LoginStatus.AUTHENTICATED) {
            logger.info("User logged in: " + email);
            User user = authorizationService.getAuthenticatedUsers().stream()
                    .filter(u -> u.getEmail().equals(email))
                    .findFirst()
                    .orElse(null);
            if (user != null) {
                logger.info("User: " + user.toString());
                System.out.println("Login successful for user:");
                System.out.println(user);
            } else {
                logger.warning("User with email " + email + " not found");
                System.out.println("Login failed: User not found after authentication");
            }
        } else {
            logger.warning("Login failed: " + status);
            System.out.println("Login failed: " + status);
        }
    }

    /**
     * Add a new user
     *
     * @param newUser     The user to add
     * @param currentUser The current user performing the action
     */
    public void addUser(User newUser, User currentUser) {
        if (authorizationService.hasAccess(currentUser, Role.ADMIN)) {
            logger.info("Adding new user by admin...");
            LoginStatus status = authorizationService.register(newUser);
            if (status == LoginStatus.AUTHENTICATED) {
                logger.info("User added successfully:");
                logger.info(newUser.toString());
                userService.addUser(newUser);
                System.out.println("User added successfully:");
                System.out.println(newUser.toString());
            } else {
                logger.info("Registration failed: " + status);
                System.out.println("Registration failed: " + status);
            }
        } else {
            logger.info("Access Denied");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Update a user by ID
     *
     * @param id          The ID of the user
     * @param updatedUser The updated user information
     * @param currentUser The current user performing the action
     */
    public void updateUserById(int id, User updatedUser, User currentUser) {
        if (authorizationService.hasAccess(currentUser, Role.MANAGER)) {
            logger.info("Update user with ID " + id + ":");
            logger.info(updatedUser.toString());
            userService.updateUserById(id, updatedUser);
            System.out.println("User updated successfully with ID " + id + ":");
            System.out.println(updatedUser.toString());
        } else {
            logger.info("Access Denied");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Delete a user by ID
     *
     * @param id          The ID of the user
     * @param currentUser The current user performing the action
     */
    public void deleteUserById(int id, User currentUser) {
        if (authorizationService.hasAccess(currentUser, Role.ADMIN)) {
            logger.info("Delete user with ID " + id);
            userService.deleteUserById(id);
            System.out.println("User with ID " + id + " deleted successfully");
        } else {
            logger.info("Access Denied");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Retrieve and print the number of orders by user ID
     *
     * @param userId      The ID of the user
     * @param currentUser The current user performing the action
     */
    public void getNumberOfOrdersByUserId(int userId, User currentUser) {
        if (authorizationService.hasAccess(currentUser, Role.MANAGER)) {
            int numberOfOrders = userService.getNumberOfOrdersByUserId(userId);
            System.out.println("Number of Orders for User ID " + userId + ": " + numberOfOrders);
            logger.info("Get number of orders for User ID " + userId + ": " + numberOfOrders);
        } else {
            logger.info("Access Denied");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Retrieve and print customers by request type
     *
     * @param requestType The type of user request
     * @param currentUser The current user performing the action
     */
    public void getCustomersByRequestType(UserRequestType requestType, User currentUser) {
        if (authorizationService.hasAccess(currentUser, Role.MANAGER)) {
            List<Customer> customers = userService.getCustomersByRequestType(requestType);
            System.out.println("Customers with Request Type " + requestType + ":");
            logger.info("Get customers with Request Type " + requestType);
            customers.forEach(customer -> System.out.println(customer.toString()));
        } else {
            logger.info("Access Denied");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Retrieve and print customers by order status
     *
     * @param status      The status of the order
     * @param currentUser The current user performing the action
     */
    public void getCustomersByOrderStatus(OrderStatus status, User currentUser) {
        if (authorizationService.hasAccess(currentUser, Role.MANAGER)) {
            List<Customer> customers = userService.getCustomersByOrderStatus(status);
            System.out.println("Customers with Order Status " + status + ":");
            logger.info("Get customers with Order Status " + status);
            customers.forEach(customer -> System.out.println(customer.toString()));
        } else {
            logger.info("Access Denied");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Retrieve and print all customers
     *
     * @param currentUser The current user performing the action
     */
    public void getAllCustomers(User currentUser) {
        if (authorizationService.hasAccess(currentUser, Role.MANAGER)) {
            List<Customer> customers = userService.getAllCustomers();
            System.out.println("All Customers:");
            logger.info("Get all customers by " + currentUser.getRole());
            customers.forEach(customer -> System.out.println(customer.toString()));
        } else {
            logger.info("Access Denied");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Retrieve and print all employees
     *
     * @param currentUser The current user performing the action
     */
    public void getAllEmployees(User currentUser) {
        if (authorizationService.hasAccess(currentUser, Role.ADMIN)) {
            List<Employee> employees = userService.getAllEmployees();
            System.out.println("All Employees:");
            logger.info("Get all employees by " + currentUser.getRole());
            employees.forEach(employee -> System.out.println(employee.toString()));
        } else {
            logger.info("Access Denied");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Retrieve and print users by first name
     *
     * @param firstName   The first name of the user
     * @param currentUser The current user performing the action
     */
    public void getUsersByFirstName(String firstName, User currentUser) {
        if (authorizationService.hasAccess(currentUser, Role.MANAGER)) {
            List<User> users = userService.getUsersByFirstName(firstName);
            logger.info("Get all users with First Name " + firstName);
            System.out.println("Users with First Name " + firstName + ":");
            users.forEach(user -> System.out.println(user.toString()));
        } else {
            logger.info("Access Denied");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Retrieve and print users by last name
     *
     * @param lastName    The last name of the user
     * @param currentUser The current user performing the action
     */
    public void getUsersByLastName(String lastName, User currentUser) {
        if (authorizationService.hasAccess(currentUser, Role.MANAGER)) {
            List<User> users = userService.getUsersByLastName(lastName);
            logger.info("Get all users with Last Name " + lastName);
            System.out.println("Users with Last Name " + lastName + ":");
            users.forEach(user -> System.out.println(user.toString()));
        } else {
            logger.info("Access Denied");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Add a new employee
     *
     * @param employee    The employee to add
     * @param currentUser The current user performing the action
     */
    public void addEmployee(Employee employee, User currentUser) {
        if (authorizationService.hasAccess(currentUser, Role.ADMIN)) {
            logger.info("Add Employee " + employee.toString());
            userService.addEmployee(employee);
            System.out.println("Employee added successfully:");
            System.out.println(employee.toString());
        } else {
            logger.info("Access Denied");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Update an employee by ID
     *
     * @param id              The ID of the employee
     * @param updatedEmployee The updated employee information
     * @param currentUser     The current user performing the action
     */
    public void updateEmployeeById(int id, Employee updatedEmployee, User currentUser) {
        if (authorizationService.hasAccess(currentUser, Role.ADMIN)) {
            logger.info("Update Employee " + updatedEmployee.toString());
            userService.updateEmployeeById(id, updatedEmployee);
            System.out.println("Employee updated successfully with ID " + id + ":");
            System.out.println(updatedEmployee.toString());
        } else {
            logger.info("Access Denied");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }
}

