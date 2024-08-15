package aeterraes.security;

import aeterraes.controllers.CustomerController;
import aeterraes.controllers.EmployeeController;
import aeterraes.dataaccess.entities.Customer;
import aeterraes.dataaccess.entities.Employee;
import aeterraes.dataaccess.models.Role;
import aeterraes.services.CustomerService;
import aeterraes.services.EmployeeService;
import aeterraes.utils.logging.LoggerConfig;

import java.util.logging.Logger;

public class AuthService {
    private static final Logger logger = LoggerConfig.getLogger();

    private final CustomerController customerController;
    private final EmployeeController employeeController;
    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public AuthService(CustomerController customerController, EmployeeController employeeController,
                       CustomerService customerService, EmployeeService employeeService) {
        this.customerController = customerController;
        this.employeeController = employeeController;
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    public LoginStatus register(String firstName, String lastName, String email, String password, String role) {
        if (role.equalsIgnoreCase("USER")) {
            Customer newCustomer = new Customer();
            newCustomer.setFirstName(firstName);
            newCustomer.setLastName(lastName);
            newCustomer.setEmail(email);
            newCustomer.setPassword(password);
            customerController.addCustomer(newCustomer);
            logger.info("Registered new customer: " + email);
            return LoginStatus.AUTHENTICATED;
        } else if (role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("MANAGER")) {
            Employee newEmployee = new Employee();
            newEmployee.setFirstName(firstName);
            newEmployee.setLastName(lastName);
            newEmployee.setEmail(email);
            newEmployee.setPassword(password);
            newEmployee.setRole(Role.valueOf(role));
            employeeController.addEmployee(newEmployee);
            logger.info("Registered new employee: " + email + " with role: " + role);
            return LoginStatus.AUTHENTICATED;
        } else {
            logger.warning("Invalid role provided during registration: " + role);
            return LoginStatus.INVALID_CREDENTIALS;
        }
    }

    public LoginStatus login(String email, String password, String role) {
        if (role.equalsIgnoreCase("USER")) {
            Customer customer = customerService.getCustomerByEmail(email);
            if (customer != null && customer.getPassword().equals(password)) {
                logger.info("Customer authenticated: " + email);
                return LoginStatus.AUTHENTICATED;
            }
        } else if (role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("MANAGER")) {
            Employee employee = employeeService.getEmployeeByEmail(email);
            if (employee != null && employee.getPassword().equals(password)) {
                logger.info("Employee authenticated: " + email);
                return LoginStatus.AUTHENTICATED;
            }
        }
        logger.warning("Invalid login attempt for email: " + email);
        return LoginStatus.INVALID_CREDENTIALS;
    }
}
