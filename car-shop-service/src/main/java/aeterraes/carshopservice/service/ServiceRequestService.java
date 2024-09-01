package aeterraes.carshopservice.service;

import aeterraes.carshopservice.dataaccess.entities.ServiceRequest;
import aeterraes.carshopservice.dataaccess.models.ServiceRequestStatus;
import aeterraes.carshopservice.dataaccess.models.UserRequestType;
import aeterraes.carshopservice.dataaccess.repositories.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;

    @Autowired
    public ServiceRequestService(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    public List<ServiceRequest> getAllServiceRequests() {
        return serviceRequestRepository.findAll();
    }

    public ServiceRequest getServiceRequestById(int id) {
        return serviceRequestRepository.findById(id).orElse(null);
    }

    public void addServiceRequest(ServiceRequest request) {
        serviceRequestRepository.save(request);
    }

    public void updateServiceRequest(ServiceRequest request) {
        if (serviceRequestRepository.existsById(request.getRequestId())) {
            serviceRequestRepository.save(request);
        }
    }

    public void deleteServiceRequest(int id) {
        serviceRequestRepository.deleteById(id);
    }

    public List<ServiceRequest> getServiceRequestsByStatus(ServiceRequestStatus status) {
        return serviceRequestRepository.findServiceRequestByRequestStatus(status);
    }

    public List<ServiceRequest> getServiceRequestsByDateRange(Date startDate, Date endDate) {
        return serviceRequestRepository.findByRequestDateBetween(startDate, endDate);
    }

    public List<ServiceRequest> getServiceRequestsByType(UserRequestType type) {
        return serviceRequestRepository.findByRequestType(type);
    }

    public List<ServiceRequest> getServiceRequestsByCustomerId(int id) {
        return serviceRequestRepository.findByCustomerCustomerId(id);
    }
}

