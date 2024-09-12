package aeterraes.carshopservice.presentation.controllers;

import aeterraes.carshopservice.dataaccess.entities.Customer;
import aeterraes.carshopservice.presentation.dtos.CustomerDTO;
import aeterraes.carshopservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    @Autowired
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") int id) {
        Customer customer = customerService.getCustomerById(id);
        CustomerDTO customerDTO = this.toDto(customer);
        return ResponseEntity.ok(customerDTO);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = this.toEntity(customerDTO);
        customerService.addCustomer(customer);
        CustomerDTO createdCustomerDTO = this.toDto(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomerDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") int id) {
        Customer existingCustomer = customerService.getCustomerById(id);
        customerService.updateCustomer(existingCustomer);
        CustomerDTO updatedCustomerDTO = this.toDto(existingCustomer);
        return ResponseEntity.ok(updatedCustomerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") int id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email")
    public ResponseEntity<CustomerDTO> getCustomerByEmail(@RequestParam("email") String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        CustomerDTO customerDTO = this.toDto(customer);
        return ResponseEntity.ok(customerDTO);
    }

    private CustomerDTO toDto(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    private Customer toEntity(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }
}

