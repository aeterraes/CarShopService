package aeterraes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestDTO implements Serializable {
    private int requestId;
    private int customerId;
    private int carId;
    private Date requestDate;
    private String requestType;
    private String requestDescription;
    private String requestStatus;
}
