package aeterraes.services;

import aeterraes.dataaccess.entities.Customer;
import aeterraes.dataaccess.models.Role;
import aeterraes.dataaccess.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    @DisplayName("Should return all customers")
    void testGetAllCustomers() {
        Customer customer1 = new Customer(1, "Elizaveta", "Koneva", "lizzy1992@somemailservice.com", "passwordcool", Role.USER);
        Customer customer2 = new Customer(2, "Sergey", "Danilov", "seregapowerwolf@somemailservice.com", "qwertyui", Role.USER);

        when(customerRepository.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        List<Customer> customers = customerService.getAllCustomers();

        assertThat(customers).hasSize(2).contains(customer1, customer2);
        verify(customerRepository, times(1)).getAllCustomers();
    }

    @Test
    @DisplayName("Should return customer by ID")
    void testGetCustomerById() {
        Customer customer = new Customer(1, "Elizaveta", "Koneva", "lizzy1992@somemailservice.com", "passwordcool", Role.USER);

        when(customerRepository.getCustomerById(1)).thenReturn(customer);

        Customer foundCustomer = customerService.getCustomerById(1);

        assertThat(foundCustomer).isEqualTo(customer);
        verify(customerRepository, times(1)).getCustomerById(1);
    }

    @Test
    @DisplayName("Should add a new customer")
    void testAddCustomer() {
        Customer customer = new Customer(1, "Elizaveta", "Koneva", "lizzy1992@somemailservice.com", "passwordcool", Role.USER);

        doNothing().when(customerRepository).addCustomer(customer);

        customerService.addCustomer(customer);

        verify(customerRepository, times(1)).addCustomer(customer);
    }

    @Test
    @DisplayName("Should update an existing customer")
    void testUpdateCustomer() {
        Customer customer = new Customer(1, "Elizaveta", "Koneva", "lizzy1992@somemailservice.com", "passwordcool", Role.USER);

        doNothing().when(customerRepository).updateCustomer(customer);

        customerService.updateCustomer(customer);

        verify(customerRepository, times(1)).updateCustomer(customer);
    }

    @Test
    @DisplayName("Should delete a customer by ID")
    void testDeleteCustomer() {
        doNothing().when(customerRepository).deleteCustomer(1);

        customerService.deleteCustomer(1);

        verify(customerRepository, times(1)).deleteCustomer(1);
    }

    @Test
    @DisplayName("Should return customer by email")
    void testGetCustomerByEmail() {
        Customer customer = new Customer(1, "Elizaveta", "Koneva", "lizzy1992@somemailservice.com", "passwordcool", Role.USER);

        when(customerRepository.getCustomerByEmail("lizzy1992@somemailservice.com")).thenReturn(customer);

        Customer foundCustomer = customerService.getCustomerByEmail("lizzy1992@somemailservice.com");

        assertThat(foundCustomer).isEqualTo(customer);
        verify(customerRepository, times(1)).getCustomerByEmail("lizzy1992@somemailservice.com");
    }
}
