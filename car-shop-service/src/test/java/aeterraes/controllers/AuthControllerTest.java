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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @Mock
    private ModelMapper modelMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AuthController authController = new AuthController(customerService, modelMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testLogin() throws Exception {
        Customer customer = new Customer(1, "Andrey", "Andreev", "andruxa@somemailservice.com", "passcode", Role.USER);
        String token = "testToken";

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
        Customer customer = new Customer(1, "Andrey", "Andreev", "andruxa@somemailservice.com", "passcode", Role.USER);
        String token = "testToken";

        when(customerService.getCustomerByEmail(anyString())).thenReturn(null);
        when(modelMapper.map(any(CustomerDTO.class), any())).thenReturn(customer);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(result -> result.getResponse().getContentAsString().equals(token));
    }

}
