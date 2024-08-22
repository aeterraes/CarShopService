package aeterraes.services;

import aeterraes.aop.annotation.Audit;
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
    @Audit
    public List<ServiceRequest> getAllServiceRequests() {
        return serviceRequestRepository.getAllServiceRequests();
    }
    @Audit
    public ServiceRequest getServiceRequestById(int id) {
        return serviceRequestRepository.getServiceRequestById(id);
    }
    @Audit
    public void addServiceRequest(ServiceRequest request) {
        serviceRequestRepository.addServiceRequest(request);
    }
    @Audit
    public void updateServiceRequest(ServiceRequest request) {
        serviceRequestRepository.updateServiceRequest(request);
    }
    @Audit
    public void deleteServiceRequest(int id) {
        serviceRequestRepository.deleteServiceRequest(id);
    }
    @Audit
    public List<ServiceRequest> getServiceRequestsByStatus(ServiceRequestStatus status) {
        return serviceRequestRepository.getServiceRequestsByStatus(status);
    }
    @Audit
    public List<ServiceRequest> getServiceRequestsByDateRange(Date startDate, Date endDate) {
        return serviceRequestRepository.getServiceRequestsByDateRange(startDate, endDate);
    }
    @Audit
    public List<ServiceRequest> getServiceRequestsByType(UserRequestType type) {
        return serviceRequestRepository.getServiceRequestsByType(type);
    }
    @Audit
    public List<ServiceRequest> getServiceRequestsByCustomerId(int id) {
        return serviceRequestRepository.getServiceRequestsByCustomerId(id);
    }
    @Audit
    public ServiceRequest getServiceRequestByCustomerId(int id) {
        return serviceRequestRepository.getServiceRequestByCustomerId(id);
    }
}

