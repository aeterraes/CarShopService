package aeterraes.carshopservice.dataaccess.entities;

import aeterraes.carshopservice.dataaccess.models.ServiceRequestStatus;
import aeterraes.carshopservice.dataaccess.models.UserRequestType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "servicerequests", schema = "entity")
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestid")
    private int requestId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerid", referencedColumnName = "customerid")
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carid", referencedColumnName = "carid")
    private Car car;
    @Column(name = "requestdate")
    private Date requestDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "requesttype")
    private UserRequestType requestType;
    @Column(name = "requestdescription")
    private String requestDescription;
    @Enumerated(EnumType.STRING)
    @Column(name = "requeststatus")
    private ServiceRequestStatus requestStatus;
}
