package aeterraes.carshopservice.presentation.controllers;

import aeterraes.carshopservice.dataaccess.entities.Customer;
import aeterraes.carshopservice.dataaccess.models.Role;
import aeterraes.carshopservice.presentation.dtos.CustomerDTO;
import aeterraes.carshopservice.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllCustomers() throws Exception {
        Customer customer = new Customer(1, "Andrey", "Andreev", "andruxa@somemailservice.com", "passcode", Role.USER,
                Collections.emptyList(), Collections.emptyList());
        CustomerDTO customerDTO = new CustomerDTO(1, "Andrey", "Andreev", "andruxa@somemailservice.com", "passcode", "USER");

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
        Customer customer = new Customer(1, "Andrey", "Andreev", "andruxa@somemailservice.com", "passcode", Role.USER,
                Collections.emptyList(), Collections.emptyList());
        CustomerDTO customerDTO = new CustomerDTO(1, "Andrey", "Andreev", "andruxa@somemailservice.com", "passcode", "USER");

        when(customerService.getCustomerById(1)).thenReturn(customer);
        when(modelMapper.map(any(Customer.class), any())).thenReturn(customerDTO);

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.firstName").value("Andrey"));
    }

    @Test
    public void testAddCustomer() throws Exception {
        Customer customer = new Customer(1, "Andrey", "Andreev", "andruxa@somemailservice.com", "passcode", Role.USER,
                Collections.emptyList(), Collections.emptyList());
        CustomerDTO customerDTO = new CustomerDTO(1, "Andrey", "Andreev", "andruxa@somemailservice.com", "passcode", "USER");

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
        Customer customer = new Customer(1, "Andrey", "Andreev", "andruxa@somemailservice.com", "passcode", Role.USER,
                Collections.emptyList(), Collections.emptyList());
        CustomerDTO customerDTO = new CustomerDTO(1, "Andrey", "Andreev", "andruxa@somemailservice.com", "passcode", "USER");

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
        Customer customer = new Customer(1, "Andrey", "Andreev", "andruxa@somemailservice.com", "passcode", Role.USER,
                Collections.emptyList(), Collections.emptyList());
        CustomerDTO customerDTO = new CustomerDTO(1, "Andrey", "Andreev", "andruxa@somemailservice.com", "passcode", "USER");

        when(customerService.getCustomerByEmail("andruxa@somemailservice.com")).thenReturn(customer);
        when(modelMapper.map(any(Customer.class), any())).thenReturn(customerDTO);

        mockMvc.perform(get("/customers/email")
                        .param("email", "andruxa@somemailservice.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.firstName").value("Andrey"));
    }
}
