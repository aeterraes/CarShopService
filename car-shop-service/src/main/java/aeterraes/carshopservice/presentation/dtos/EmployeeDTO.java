package aeterraes.carshopservice.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO implements Serializable {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}

