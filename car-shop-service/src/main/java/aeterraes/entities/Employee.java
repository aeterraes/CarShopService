package aeterraes.entities;

import aeterraes.models.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee extends User {
    public Employee(int id, String firstName, String lastName, String email, String password, Role role) {
        super(id, firstName, lastName, email, password, role);
    }
}
