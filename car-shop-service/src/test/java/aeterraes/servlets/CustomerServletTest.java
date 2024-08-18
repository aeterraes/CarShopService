package aeterraes.servlets;

import aeterraes.dataaccess.entities.Customer;
import aeterraes.dataaccess.models.Role;
import aeterraes.dtos.CustomerDTO;
import aeterraes.mappers.CustomerMapper;
import aeterraes.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;

import static org.mockito.Mockito.*;

public class CustomerServletTest {

    @Mock
    private CustomerService customerService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    private CustomerServlet customerServlet;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerServlet = new CustomerServlet();
        objectMapper = new ObjectMapper();
        customerServlet.setCustomerService(customerService);
        customerServlet.setObjectMapper(objectMapper);
    }

    @Test
    public void testDoPost() throws IOException, ServletException {
        String customerJson = "{\"customerId\":0,\"firstName\":\"Andrey\",\"lastName\":\"Andreev\",\"email\":\"andruxa@somemailservice.com\",\"password\":\"passcode\",\"role\":\"USER\"}";
        CustomerDTO customerDTO = objectMapper.readValue(customerJson, CustomerDTO.class);
        Customer customer = CustomerMapper.INSTANCE.customerDTOToCustomer(customerDTO);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(customerJson)));

        customerServlet.doPost(request, response);

        verify(customerService, times(1)).addCustomer(customer);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    public void testDoGet_specificCustomer() throws IOException, ServletException {
        int customerId = 2;
        Customer customer = new Customer(customerId, "Roman", "Romanov", "romromych777@somemailservice.com", "RoMKrUteee", Role.USER);
        CustomerDTO customerDTO = CustomerMapper.INSTANCE.customerToCustomerDTO(customer);
        String customerJson = objectMapper.writeValueAsString(customerDTO);

        when(request.getPathInfo()).thenReturn("/" + customerId);
        when(customerService.getCustomerById(customerId)).thenReturn(customer);

        PrintWriter printWriter = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(printWriter);

        customerServlet.doGet(request, response);

        verify(customerService, times(1)).getCustomerById(customerId);
        verify(response, times(1)).setContentType("application/json");
        verify(printWriter, times(1)).write(customerJson);
    }


}