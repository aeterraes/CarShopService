package aeterraes.controllers;

import aeterraes.dataaccess.entities.Employee;
import aeterraes.utils.logging.LoggerConfig;
import aeterraes.services.EmployeeService;

import java.util.List;
import java.util.logging.Logger;

public class EmployeeController {

    private final EmployeeService employeeService;
    private static final Logger logger = LoggerConfig.getLogger();

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void getAllEmployees() {
        logger.info("Fetching all employees");
        List<Employee> employees = employeeService.getAllEmployees();
        employees.forEach(System.out::println);
        logger.info("Fetched " + employees.size() + " employees");
    }

    public void getEmployeeById(int id) {
        logger.info("Fetching employee with ID: " + id);
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            System.out.println(employee);
            logger.info("Fetched employee: " + employee);
        } else {
            logger.warning("Employee with ID " + id + " not found");
        }
    }

    public void addEmployee(Employee employee) {
        logger.info("Adding new employee: " + employee);
        employeeService.addEmployee(employee);
        logger.info("Employee added: " + employee);
    }

    public void updateEmployee(Employee employee) {
        logger.info("Updating employee: " + employee);
        employeeService.updateEmployee(employee);
        logger.info("Employee updated: " + employee);
    }

    public void deleteEmployee(int id) {
        logger.info("Deleting employee with ID: " + id);
        employeeService.deleteEmployee(id);
        logger.info("Employee with ID " + id + " deleted");
    }

    public void getEmployeeByEmail(String email) {
        logger.info("Fetching employee with email: " + email);
        Employee employee = employeeService.getEmployeeByEmail(email);
        if (employee != null) {
            System.out.println(employee);
            logger.info("Fetched employee: " + employee);
        } else {
            logger.warning("Employee with email " + email + " not found");
        }
    }
}

