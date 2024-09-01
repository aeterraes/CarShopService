package aeterraes.carshopservice.dataaccess.repositories;

import aeterraes.carshopservice.dataaccess.entities.ServiceRequest;
import aeterraes.carshopservice.dataaccess.models.ServiceRequestStatus;
import aeterraes.carshopservice.dataaccess.models.UserRequestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Integer> {
    List<ServiceRequest> findServiceRequestByRequestStatus(ServiceRequestStatus status);

    List<ServiceRequest> findByRequestDateBetween(Date startDate, Date endDate);

    List<ServiceRequest> findByRequestType(UserRequestType type);

    List<ServiceRequest> findByCustomerCustomerId(int customerId);
}
