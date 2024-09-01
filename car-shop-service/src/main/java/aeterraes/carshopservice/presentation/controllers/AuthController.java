package aeterraes.carshopservice.presentation.controllers;

import aeterraes.carshopservice.dataaccess.entities.Customer;
import aeterraes.carshopservice.presentation.dtos.CustomerDTO;
import aeterraes.carshopservice.security.JwtService;
import aeterraes.carshopservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final CustomerService customerService;

    @Autowired
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        Customer customer = customerService.getCustomerByEmail(email);
        if (customer != null && customer.getPassword().equals(password)) {
            String token = JwtService.generateToken(customer.getEmail());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CustomerDTO customerDTO) {
        if (customerService.getCustomerByEmail(customerDTO.getEmail()) != null) {
            return ResponseEntity.status(400).body("Email already in use");
        }
        Customer newCustomer = modelMapper.map(customerDTO, Customer.class);
        customerService.addCustomer(newCustomer);
        String token = JwtService.generateToken(newCustomer.getEmail());
        return ResponseEntity.status(201).body(token);
    }
}

