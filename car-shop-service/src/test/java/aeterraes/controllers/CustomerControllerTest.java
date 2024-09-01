package aeterraes.controllers;

import aeterraes.dataaccess.entities.Customer;
import aeterraes.dataaccess.models.Role;
import aeterraes.dtos.CustomerDTO;
import aeterraes.services.CustomerService;
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

public class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @Mock
    private ModelMapper modelMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerController customerController = new CustomerController(customerService, modelMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        Customer customer = new Customer(1, "Andrey",
                "Andreev", "andruxa@somemailservice.com",
                "passcode", Role.USER);
        CustomerDTO customerDTO = new CustomerDTO(1,
                "Andrey", "Andreev",
                "andruxa@somemailservice.com", "passcode", "USER");

        List<Customer> customers = List.of(customer);

        when(customerService.getAllCustomers()).thenReturn(customers);
        when(modelMapper.map(any(Customer.class), any())).thenReturn(customerDTO);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Andrey"));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        Customer customer = new Customer(1, "Andrey", "Andreev",
                "andruxa@somemailservice.com",
                "passcode", Role.USER);
        CustomerDTO customerDTO = new CustomerDTO(1, "Andrey",
                "Andreev", "andruxa@somemailservice.com",
                "passcode", "USER");

        when(customerService.getCustomerById(1)).thenReturn(customer);
        when(modelMapper.map(any(Customer.class), any())).thenReturn(customerDTO);

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.firstName").value("Andrey"));
    }

    @Test
    public void testAddCustomer() throws Exception {
        Customer customer = new Customer(1, "Andrey",
                "Andreev", "andruxa@somemailservice.com",
                "passcode", Role.USER);
        CustomerDTO customerDTO = new CustomerDTO(1, "Andrey",
                "Andreev", "andruxa@somemailservice.com",
                "passcode", "USER");

        when(modelMapper.map(any(CustomerDTO.class), any())).thenReturn(customer);
        when(modelMapper.map(any(Customer.class), any())).thenReturn(customerDTO);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.firstName").value("Andrey"));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer customer = new Customer(1, "Andrey", "Andreev",
                "andruxa@somemailservice.com",
                "passcode", Role.USER);
        CustomerDTO customerDTO = new CustomerDTO(1, "Andrey",
                "Andreev", "andruxa@somemailservice.com",
                "passcode", "USER");

        when(customerService.getCustomerById(1)).thenReturn(customer);
        when(modelMapper.map(any(Customer.class), any())).thenReturn(customerDTO);

        mockMvc.perform(put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.firstName").value("Andrey"));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetCustomerByEmail() throws Exception {
        Customer customer = new Customer(1, "Andrey", "Andreev",
                "andruxa@somemailservice.com", "passcode", Role.USER);
        CustomerDTO customerDTO = new CustomerDTO(1, "Andrey",
                "Andreev", "andruxa@somemailservice.com", "passcode", "USER");
        when(customerService.getCustomerByEmail("andruxa@somemailservice.com")).thenReturn(customer);
        when(modelMapper.map(any(Customer.class), any())).thenReturn(customerDTO);

        mockMvc.perform(get("/customers/email")
                        .param("email", "andruxa@somemailservice.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.firstName").value("Andrey"));
    }
}
