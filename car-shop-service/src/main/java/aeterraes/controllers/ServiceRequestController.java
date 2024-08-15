package aeterraes.controllers;

import aeterraes.dataaccess.entities.ServiceRequest;
import aeterraes.utils.logging.LoggerConfig;
import aeterraes.dataaccess.models.ServiceRequestStatus;
import aeterraes.dataaccess.models.UserRequestType;
import aeterraes.services.ServiceRequestService;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;
    private static final Logger logger = LoggerConfig.getLogger();

    public ServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    public void getAllServiceRequests() {
        logger.info("Fetching all service requests");
        List<ServiceRequest> requests = serviceRequestService.getAllServiceRequests();
        requests.forEach(System.out::println);
        logger.info("Fetched " + requests.size() + " service requests");
    }

    public void getServiceRequestById(int id) {
        logger.info("Fetching service request with ID: " + id);
        ServiceRequest request = serviceRequestService.getServiceRequestById(id);
        if (request != null) {
            System.out.println(request);
            logger.info("Fetched service request: " + request);
        } else {
            logger.warning("Service request with ID " + id + " not found");
        }
    }

    public void addServiceRequest(ServiceRequest request) {
        logger.info("Adding new service request: " + request);
        serviceRequestService.addServiceRequest(request);
        logger.info("Service request added: " + request);
    }

    public void updateServiceRequest(ServiceRequest request) {
        logger.info("Updating service request: " + request);
        serviceRequestService.updateServiceRequest(request);
        logger.info("Service request updated: " + request);
    }

    public void deleteServiceRequest(int id) {
        logger.info("Deleting service request with ID: " + id);
        serviceRequestService.deleteServiceRequest(id);
        logger.info("Service request with ID " + id + " deleted");
    }

    public void getServiceRequestsByStatus(ServiceRequestStatus status) {
        logger.info("Fetching service requests with status: " + status);
        List<ServiceRequest> requests = serviceRequestService.getServiceRequestsByStatus(status);
        requests.forEach(System.out::println);
        logger.info("Fetched " + requests.size() + " service requests with status " + status);
    }

    public void getServiceRequestsByDateRange(Date startDate, Date endDate) {
        logger.info("Fetching service requests from " + startDate + " to " + endDate);
        List<ServiceRequest> requests = serviceRequestService.getServiceRequestsByDateRange(startDate, endDate);
        requests.forEach(System.out::println);
        logger.info("Fetched " + requests.size() + " service requests from " + startDate + " to " + endDate);
    }

    public void getServiceRequestsByType(UserRequestType type) {
        logger.info("Fetching service requests with type: " + type);
        List<ServiceRequest> requests = serviceRequestService.getServiceRequestsByType(type);
        requests.forEach(System.out::println);
        logger.info("Fetched " + requests.size() + " service requests with type " + type);
    }

    public void getServiceRequestsByCustomerId(int id) {
        logger.info("Fetching service requests with customer ID: " + id);
        List<ServiceRequest> requests = serviceRequestService.getServiceRequestsByCustomerId(id);
        requests.forEach(System.out::println);
        logger.info("Fetched " + requests.size() + " service requests with customer ID " + id);
    }

    public void getServiceRequestByCustomerId(int id) {
        logger.info("Fetching service request with customer ID: " + id);
        ServiceRequest request = serviceRequestService.getServiceRequestByCustomerId(id);
        if (request != null) {
            System.out.println(request);
            logger.info("Fetched service request: " + request);
        } else {
            logger.warning("Service request with customer ID " + id + " not found");
        }
    }
}

