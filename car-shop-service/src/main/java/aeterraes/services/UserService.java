package aeterraes.services;

import aeterraes.entities.Customer;
import aeterraes.entities.Employee;
import aeterraes.entities.User;
import aeterraes.models.OrderStatus;
import aeterraes.models.UserRequestType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserService {

    private List<User> users = new ArrayList<>();

    /**
     * Retrieve a list of all users
     *
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    /**
     * Retrieve a user by their ID
     *
     * @param id The ID of the user
     * @return The user with the specified ID, or null if not found
     */
    public User getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieve a user by their email
     *
     * @param email The email of the user
     * @return The user with the specified email, or null if not found
     */
    public User getUserByEmail(String email) {
        return users.stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst()
                .orElse(null);
    }

    /**
     * Add a new user to the list
     *
     * @param user The user to add
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Update an existing user by their ID
     *
     * @param id          The ID of the user to update
     * @param updatedUser The updated user information
     */
    public void updateUserById(int id, User updatedUser) {
        users = users.stream()
                .map(user -> user.getId() == id ? updatedUser : user)
                .collect(Collectors.toList());
    }

    /**
     * Delete a user by their ID
     *
     * @param id The ID of the user to delete
     */
    public void deleteUserById(int id) {
        users.removeIf(user -> user.getId() == id);
    }

    /**
     * Retrieve a list of all customers
     *
     * @return List of all customers
     */
    public List<Customer> getAllCustomers() {
        return users.stream()
                .filter(user -> user instanceof Customer)
                .map(user -> (Customer) user)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of all employees
     *
     * @return List of all employees
     */
    public List<Employee> getAllEmployees() {
        return users.stream()
                .filter(user -> user instanceof Employee)
                .map(user -> (Employee) user)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of users by their first name
     *
     * @param firstName The first name to filter by
     * @return List of users with the specified first name
     */
    public List<User> getUsersByFirstName(String firstName) {
        return users.stream()
                .filter(user -> user.getFirstName().equalsIgnoreCase(firstName))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of users by their last name
     *
     * @param lastName The last name to filter by
     * @return List of users with the specified last name
     */
    public List<User> getUsersByLastName(String lastName) {
        return users.stream()
                .filter(user -> user.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    /**
     * Add a new employee to the list
     *
     * @param employee The employee to add
     */
    public void addEmployee(Employee employee) {
        users.add(employee);
    }

    /**
     * Update an existing employee by their ID
     *
     * @param id              The ID of the employee to update
     * @param updatedEmployee The updated employee information
     */
    public void updateEmployeeById(int id, Employee updatedEmployee) {
        users = users.stream()
                .map(user -> user.getId() == id ? updatedEmployee : user)
                .collect(Collectors.toList());
    }

    /**
     * Get the number of orders associated with a user by their ID
     *
     * @param userId The ID of the user
     * @return The number of orders for the user, or 0 if the user is not a customer
     */
    public int getNumberOfOrdersByUserId(int userId) {
        User user = getUserById(userId);
        if (user instanceof Customer) {
            return ((Customer) user).getOrders().size();
        }
        return 0;
    }

    /**
     * Retrieve a list of customers by the type of their service request
     *
     * @param requestType The type of service request
     * @return List of customers with the specified service request type
     */
    public List<Customer> getCustomersByRequestType(UserRequestType requestType) {
        return users.stream()
                .filter(user -> user instanceof Customer)
                .map(user -> (Customer) user)
                .filter(customer -> customer.getServiceRequests().stream()
                        .anyMatch(request -> request.getRequestType() == requestType))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of customers by the status of their orders
     *
     * @param status The status of the orders
     * @return List of customers with orders having the specified status
     */
    public List<Customer> getCustomersByOrderStatus(OrderStatus status) {
        return users.stream()
                .filter(user -> user instanceof Customer)
                .map(user -> (Customer) user)
                .filter(customer -> customer.getOrders().stream()
                        .anyMatch(order -> order.getStatus() == status))
                .collect(Collectors.toList());
    }
}
