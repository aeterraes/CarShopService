package aeterraes.entities;

import aeterraes.models.ServiceRequestStatus;
import aeterraes.models.UserRequestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequest {
    private int id;
    private int customerId;
    private int carId;
    private Date requestDate;
    private UserRequestType requestType;
    private String requestDescription;
    private ServiceRequestStatus requestStatus;
}
