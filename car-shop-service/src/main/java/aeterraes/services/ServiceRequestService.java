package aeterraes.services;

import aeterraes.dataaccess.entities.ServiceRequest;
import aeterraes.dataaccess.models.ServiceRequestStatus;
import aeterraes.dataaccess.models.UserRequestType;
import aeterraes.dataaccess.repositories.ServiceRequestRepository;

import java.sql.Date;
import java.util.List;

public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;

    public ServiceRequestService(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    public List<ServiceRequest> getAllServiceRequests() {
        return serviceRequestRepository.getAllServiceRequests();
    }

    public ServiceRequest getServiceRequestById(int id) {
        return serviceRequestRepository.getServiceRequestById(id);
    }

    public void addServiceRequest(ServiceRequest request) {
        serviceRequestRepository.addServiceRequest(request);
    }

    public void updateServiceRequest(ServiceRequest request) {
        serviceRequestRepository.updateServiceRequest(request);
    }

    public void deleteServiceRequest(int id) {
        serviceRequestRepository.deleteServiceRequest(id);
    }

    public List<ServiceRequest> getServiceRequestsByStatus(ServiceRequestStatus status) {
        return serviceRequestRepository.getServiceRequestsByStatus(status);
    }

    public List<ServiceRequest> getServiceRequestsByDateRange(Date startDate, Date endDate) {
        return serviceRequestRepository.getServiceRequestsByDateRange(startDate, endDate);
    }

    public List<ServiceRequest> getServiceRequestsByType(UserRequestType type) {
        return serviceRequestRepository.getServiceRequestsByType(type);
    }

    public List<ServiceRequest> getServiceRequestsByCustomerId(int id) {
        return serviceRequestRepository.getServiceRequestsByCustomerId(id);
    }

    public ServiceRequest getServiceRequestByCustomerId(int id) {
        return serviceRequestRepository.getServiceRequestByCustomerId(id);
    }
}

