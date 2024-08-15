package aeterraes.services;

import aeterraes.dataaccess.entities.Employee;
import aeterraes.dataaccess.models.Role;
import aeterraes.dataaccess.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    @DisplayName("Should return all employees")
    void testGetAllEmployees() {
        Employee employee1 = new Employee(1, "Ivan", "Alexandrov", "vanek777@somemailservice.com", "darovabro", Role.ADMIN);
        Employee employee2 = new Employee(2, "Yana", "Zorina", "yan_ochka_sunny@somemailservice.com", "genuinepasscode", Role.MANAGER);

        when(employeeRepository.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2));

        List<Employee> employees = employeeService.getAllEmployees();

        assertThat(employees).hasSize(2).contains(employee1, employee2);
        verify(employeeRepository, times(1)).getAllEmployees();
    }

    @Test
    @DisplayName("Should return employee by ID")
    void testGetEmployeeById() {
        Employee employee = new Employee(1, "Ivan", "Alexandrov", "vanek777@somemailservice.com", "darovabro", Role.ADMIN);

        when(employeeRepository.getEmployeeById(1)).thenReturn(employee);

        Employee foundEmployee = employeeService.getEmployeeById(1);

        assertThat(foundEmployee).isEqualTo(employee);
        verify(employeeRepository, times(1)).getEmployeeById(1);
    }

    @Test
    @DisplayName("Should add a new employee")
    void testAddEmployee() {
        Employee employee = new Employee(1, "Ivan", "Alexandrov", "vanek777@somemailservice.com", "darovabro", Role.ADMIN);

        doNothing().when(employeeRepository).addEmployee(employee);

        employeeService.addEmployee(employee);

        verify(employeeRepository, times(1)).addEmployee(employee);
    }

    @Test
    @DisplayName("Should update an existing employee")
    void testUpdateEmployee() {
        Employee employee = new Employee(1, "Ivan", "Alexandrov", "vanek777@somemailservice.com", "darovabro", Role.ADMIN);

        doNothing().when(employeeRepository).updateEmployee(employee);

        employeeService.updateEmployee(employee);

        verify(employeeRepository, times(1)).updateEmployee(employee);
    }

    @Test
    @DisplayName("Should delete an employee by ID")
    void testDeleteEmployee() {
        doNothing().when(employeeRepository).deleteEmployee(1);

        employeeService.deleteEmployee(1);

        verify(employeeRepository, times(1)).deleteEmployee(1);
    }

    @Test
    @DisplayName("Should return employee by email")
    void testGetEmployeeByEmail() {
        Employee employee = new Employee(1, "Ivan", "Alexandrov", "vanek777@somemailservice.com", "darovabro", Role.ADMIN);

        when(employeeRepository.getEmployeeByEmail("vanek777@somemailservice.com")).thenReturn(employee);

        Employee foundEmployee = employeeService.getEmployeeByEmail("vanek777@somemailservice.com");

        assertThat(foundEmployee).isEqualTo(employee);
        verify(employeeRepository, times(1)).getEmployeeByEmail("vanek777@somemailservice.com");
    }
}
