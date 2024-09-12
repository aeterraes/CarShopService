package aeterraes.carshopservice.presentation.controllers;

import aeterraes.carshopservice.dataaccess.entities.Employee;
import aeterraes.carshopservice.dataaccess.models.Role;
import aeterraes.carshopservice.presentation.dtos.EmployeeDTO;
import aeterraes.carshopservice.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllEmployees() throws Exception {
        Employee employee = new Employee(1, "Andrey",
                "Andreev", "andruxa@somemailservice.com",
                "passcode", Role.ADMIN);
        EmployeeDTO employeeDTO = new EmployeeDTO(1, "Andrey",
                "Andreev", "andruxa@somemailservice.com",
                "passcode", "ADMIN");

        List<Employee> employees = List.of(employee);

        when(employeeService.getAllEmployees()).thenReturn(employees);
        when(modelMapper.map(any(Employee.class), any())).thenReturn(employeeDTO);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Andrey"));
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        Employee employee = new Employee(1, "Andrey", "Andreev",
                "andruxa@somemailservice.com",
                "passcode", Role.ADMIN);
        EmployeeDTO employeeDTO = new EmployeeDTO(1, "Andrey",
                "Andreev", "andruxa@somemailservice.com",
                "passcode", "ADMIN");

        when(employeeService.getEmployeeById(1)).thenReturn(employee);
        when(modelMapper.map(any(Employee.class), any())).thenReturn(employeeDTO);

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.firstName").value("Andrey"));
    }

    @Test
    public void testAddEmployee() throws Exception {
        Employee employee = new Employee(1, "Andrey",
                "Andreev", "andruxa@somemailservice.com",
                "passcode", Role.ADMIN);
        EmployeeDTO employeeDTO = new EmployeeDTO(1, "Andrey",
                "Andreev", "andruxa@somemailservice.com",
                "passcode", "ADMIN");

        when(modelMapper.map(any(EmployeeDTO.class), any())).thenReturn(employee);
        when(modelMapper.map(any(Employee.class), any())).thenReturn(employeeDTO);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.firstName").value("Andrey"));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        Employee employee = new Employee(1, "Andrey",
                "Andreev", "andruxa@somemailservice.com",
                "passcode", Role.ADMIN);
        EmployeeDTO employeeDTO = new EmployeeDTO(1, "Andrey",
                "Andreev", "andruxa@somemailservice.com",
                "passcode", "ADMIN");

        when(employeeService.getEmployeeById(1)).thenReturn(employee);
        when(modelMapper.map(any(Employee.class), any())).thenReturn(employeeDTO);

        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.firstName").value("Andrey"));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetEmployeeByEmail() throws Exception {
        Employee employee = new Employee(1, "Andrey", "Andreev",
                "andruxa@somemailservice.com", "passcode", Role.ADMIN);
        EmployeeDTO employeeDTO = new EmployeeDTO(1, "Andrey",
                "Andreev", "andruxa@somemailservice.com",
                "passcode", "ADMIN");

        when(employeeService.getEmployeeByEmail("andruxa@somemailservice.com")).thenReturn(employee);
        when(modelMapper.map(any(Employee.class), any())).thenReturn(employeeDTO);

        mockMvc.perform(get("/employees/email")
                        .param("email", "andruxa@somemailservice.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.firstName").value("Andrey"));
    }
}
