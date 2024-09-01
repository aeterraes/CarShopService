package aeterraes.carshopservice.dataaccess.entities;

import aeterraes.carshopservice.dataaccess.models.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "customers", schema = "entity")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerid")
    private int customerId;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<ServiceRequest> serviceRequests = new ArrayList<>();
}
