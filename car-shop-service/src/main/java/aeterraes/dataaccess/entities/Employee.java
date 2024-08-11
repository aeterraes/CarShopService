package aeterraes.dataaccess.entities;

import aeterraes.dataaccess.models.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
