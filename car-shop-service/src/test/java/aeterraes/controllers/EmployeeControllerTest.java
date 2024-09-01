package aeterraes.controllers;

import aeterraes.dataaccess.entities.Employee;
import aeterraes.dataaccess.models.Role;
import aeterraes.dtos.EmployeeDTO;
import aeterraes.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ModelMapper modelMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeeController employeeController = new EmployeeController(employeeService, modelMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

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
                "Andreev", "andruxa@somemailservice.com", "passcode", "ADMIN");

        when(employeeService.getEmployeeByEmail("andruxa@somemailservice.com")).thenReturn(employee);
        when(modelMapper.map(any(Employee.class), any())).thenReturn(employeeDTO);

        mockMvc.perform(get("/employees/email")
                        .param("email", "andruxa@somemailservice.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.firstName").value("Andrey"));
    }
}
