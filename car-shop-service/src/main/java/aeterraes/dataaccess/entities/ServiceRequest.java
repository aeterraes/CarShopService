package aeterraes.dataaccess.entities;

import aeterraes.dataaccess.models.ServiceRequestStatus;
import aeterraes.dataaccess.models.UserRequestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequest {
    private int requestId;
    private int customerId;
    private int carId;
    private Date requestDate;
    private UserRequestType requestType;
    private String requestDescription;
    private ServiceRequestStatus requestStatus;
}
