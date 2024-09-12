package aeterraes.carshopservice.service;

import aeterraes.carshopservice.dataaccess.entities.Customer;
import aeterraes.carshopservice.dataaccess.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void updateCustomer(Customer customer) {
        if (customerRepository.existsById(customer.getCustomerId())) {
            customerRepository.save(customer);
        }
    }

    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
