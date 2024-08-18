package aeterraes.services;

import aeterraes.aop.annotation.Audit;
import aeterraes.dataaccess.entities.Employee;
import aeterraes.dataaccess.repositories.EmployeeRepository;

import java.util.List;

public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Audit
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }
    @Audit
    public Employee getEmployeeById(int id) {
        return employeeRepository.getEmployeeById(id);
    }
    @Audit
    public void addEmployee(Employee employee) {
        employeeRepository.addEmployee(employee);
    }
    @Audit
    public void updateEmployee(Employee employee) {
        employeeRepository.updateEmployee(employee);
    }
    @Audit
    public void deleteEmployee(int id) {
        employeeRepository.deleteEmployee(id);
    }
    @Audit
    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.getEmployeeByEmail(email);
    }
}

