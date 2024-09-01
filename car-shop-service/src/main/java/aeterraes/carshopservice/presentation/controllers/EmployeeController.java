package aeterraes.carshopservice.presentation.controllers;

import aeterraes.carshopservice.dataaccess.entities.Employee;
import aeterraes.carshopservice.presentation.dtos.EmployeeDTO;
import aeterraes.carshopservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    @Autowired
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") int id) {
        Employee employee = employeeService.getEmployeeById(id);
        EmployeeDTO employeeDTO = this.toDto(employee);
        return ResponseEntity.ok(employeeDTO);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = this.toEntity(employeeDTO);
        employeeService.addEmployee(employee);
        EmployeeDTO createdEmployeeDTO = this.toDto(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployeeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable("id") int id) {
        Employee existingEmployee = employeeService.getEmployeeById(id);
        employeeService.updateEmployee(existingEmployee);
        EmployeeDTO updatedEmployeeDTO = this.toDto(existingEmployee);
        return ResponseEntity.ok(updatedEmployeeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email")
    public ResponseEntity<EmployeeDTO> getEmployeeByEmail(@RequestParam("email") String email) {
        Employee employee = employeeService.getEmployeeByEmail(email);
        EmployeeDTO employeeDTO = this.toDto(employee);
        return ResponseEntity.ok(employeeDTO);
    }

    private Employee toEntity(EmployeeDTO employeeDTO) {
        return modelMapper.map(employeeDTO, Employee.class);
    }

    private EmployeeDTO toDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDTO.class);
    }
}
