package aeterraes.carshopservice.presentation.controllers;

import aeterraes.carshopservice.dataaccess.entities.Customer;
import aeterraes.carshopservice.dataaccess.models.Role;
import aeterraes.carshopservice.presentation.dtos.CustomerDTO;
import aeterraes.carshopservice.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private final String token = "testToken";

    @Test
    public void testLogin() throws Exception {
        Customer customer = new Customer(1, "Andrey", "Andreev", "andruxa@somemailservice.com", "passcode", Role.USER,
                Collections.emptyList(), Collections.emptyList());

        when(customerService.getCustomerByEmail(anyString())).thenReturn(customer);

        mockMvc.perform(post("/auth/login")
                        .param("email", "andruxa@somemailservice.com")
                        .param("password", "passcode"))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().equals(token));
    }

    @Test
    public void testRegister() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO(1, "Andrey", "Andreev", "andruxa@somemailservice.com", "passcode", "USER");
        Customer customer = new Customer(1, "Andrey", "Andreev", "andruxa@somemailservice.com", "passcode", Role.USER,
                Collections.emptyList(), Collections.emptyList())
                ;

        when(customerService.getCustomerByEmail(anyString())).thenReturn(null);
        when(modelMapper.map(any(CustomerDTO.class), any())).thenReturn(customer);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(result -> result.getResponse().getContentAsString().equals(token));
    }
}
