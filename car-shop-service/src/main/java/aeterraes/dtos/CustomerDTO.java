package aeterraes.dtos;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}

