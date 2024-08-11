package aeterraes.services;

import aeterraes.dataaccess.entities.Customer;
import aeterraes.dataaccess.repositories.CustomerRepository;

import java.util.List;

public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    public Customer getCustomerById(int id) {
        return customerRepository.getCustomerById(id);
    }

    public void addCustomer(Customer customer) {
        customerRepository.addCustomer(customer);
    }

    public void updateCustomer(Customer customer) {
        customerRepository.updateCustomer(customer);
    }

    public void deleteCustomer(int id) {
        customerRepository.deleteCustomer(id);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.getCustomerByEmail(email);
    }
}
