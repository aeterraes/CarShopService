package aeterraes.services;

import aeterraes.aop.annotation.Audit;
import aeterraes.dataaccess.entities.Customer;
import aeterraes.dataaccess.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Audit
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @Audit
    public Customer getCustomerById(int id) {
        return customerRepository.getCustomerById(id);
    }

    @Audit
    public void addCustomer(Customer customer) {
        customerRepository.addCustomer(customer);
    }

    @Audit
    public void updateCustomer(Customer customer) {
        customerRepository.updateCustomer(customer);
    }

    @Audit
    public void deleteCustomer(int id) {
        customerRepository.deleteCustomer(id);
    }

    @Audit
    public Customer getCustomerByEmail(String email) {
        return customerRepository.getCustomerByEmail(email);
    }
}
