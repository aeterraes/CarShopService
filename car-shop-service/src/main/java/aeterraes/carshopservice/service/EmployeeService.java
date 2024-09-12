package aeterraes.carshopservice.service;

import aeterraes.carshopservice.dataaccess.entities.Employee;
import aeterraes.carshopservice.dataaccess.repositories.EmployeeRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter
@Getter
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void updateEmployee(Employee employee) {
        if (employeeRepository.existsById(employee.getEmployeeId())) {
            employeeRepository.save(employee);
        }
    }

    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
}

