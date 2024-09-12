package aeterraes.carshopservice.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO implements Serializable {
    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}

