package aeterraes.controllers;

import aeterraes.dataaccess.entities.Customer;
import aeterraes.utils.logging.LoggerConfig;
import aeterraes.services.CustomerService;

import java.util.List;
import java.util.logging.Logger;

public class CustomerController {

    private final CustomerService customerService;
    private static final Logger logger = LoggerConfig.getLogger();

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void getAllCustomers() {
        logger.info("Fetching all customers");
        List<Customer> customers = customerService.getAllCustomers();
        customers.forEach(System.out::println);
        logger.info("Fetched " + customers.size() + " customers");
    }

    public void getCustomerById(int id) {
        logger.info("Fetching customer with ID: " + id);
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            System.out.println(customer);
            logger.info("Fetched customer: " + customer);
        } else {
            logger.warning("Customer with ID " + id + " not found");
        }
    }

    public void addCustomer(Customer customer) {
        logger.info("Adding new customer: " + customer);
        customerService.addCustomer(customer);
        logger.info("Customer added: " + customer);
    }

    public void updateCustomer(Customer customer) {
        logger.info("Updating customer: " + customer);
        customerService.updateCustomer(customer);
        logger.info("Customer updated: " + customer);
    }

    public void deleteCustomer(int id) {
        logger.info("Deleting customer with ID: " + id);
        customerService.deleteCustomer(id);
        logger.info("Customer with ID " + id + " deleted");
    }

    public void getCustomerByEmail(String email) {
        logger.info("Fetching customer with email: " + email);
        Customer customer = customerService.getCustomerByEmail(email);
        if (customer != null) {
            System.out.println(customer);
            logger.info("Fetched customer: " + customer);
        } else {
            logger.warning("Customer with email " + email + " not found");
        }
    }
}
