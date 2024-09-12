package aeterraes.carshopservice.dataaccess.entities;
import aeterraes.carshopservice.dataaccess.models.Role;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "employees", schema = "entity")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeid")
    private int employeeId;
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
}
