package aeterraes.services;

import aeterraes.dataaccess.entities.ServiceRequest;
import aeterraes.dataaccess.models.ServiceRequestStatus;
import aeterraes.dataaccess.models.UserRequestType;
import aeterraes.dataaccess.repositories.ServiceRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ServiceRequestServiceTest {

    private ServiceRequestRepository serviceRequestRepository;
    private ServiceRequestService serviceRequestService;

    @BeforeEach
    void setUp() {
        serviceRequestRepository = Mockito.mock(ServiceRequestRepository.class);
        serviceRequestService = new ServiceRequestService(serviceRequestRepository);
    }

    @Test
    @DisplayName("Should return all service requests")
    void testGetAllServiceRequests() {
        ServiceRequest request1 = new ServiceRequest(1, 1, 1, new Date(System.currentTimeMillis()),
                UserRequestType.PURCHASE, "Description 1", ServiceRequestStatus.CREATED);
        ServiceRequest request2 = new ServiceRequest(2, 2, 2, new Date(System.currentTimeMillis()),
                UserRequestType.MAINTENANCE, "Description 2", ServiceRequestStatus.IN_PROGRESS);

        when(serviceRequestRepository.getAllServiceRequests()).thenReturn(Arrays.asList(request1, request2));

        List<ServiceRequest> requests = serviceRequestService.getAllServiceRequests();

        assertThat(requests).hasSize(2).contains(request1, request2);
        verify(serviceRequestRepository, times(1)).getAllServiceRequests();
    }

    @Test
    @DisplayName("Should return service request by ID")
    void testGetServiceRequestById() {
        ServiceRequest request = new ServiceRequest(1, 1, 1, new Date(System.currentTimeMillis()),
                UserRequestType.PURCHASE, "Description", ServiceRequestStatus.CREATED);

        when(serviceRequestRepository.getServiceRequestById(1)).thenReturn(request);

        ServiceRequest foundRequest = serviceRequestService.getServiceRequestById(1);

        assertThat(foundRequest).isEqualTo(request);
        verify(serviceRequestRepository, times(1)).getServiceRequestById(1);
    }

    @Test
    @DisplayName("Should add a new service request")
    void testAddServiceRequest() {
        ServiceRequest request = new ServiceRequest(1, 1, 1, new Date(System.currentTimeMillis()),
                UserRequestType.PURCHASE, "Description", ServiceRequestStatus.CREATED);

        doNothing().when(serviceRequestRepository).addServiceRequest(request);

        serviceRequestService.addServiceRequest(request);

        verify(serviceRequestRepository, times(1)).addServiceRequest(request);
    }

    @Test
    @DisplayName("Should update an existing service request")
    void testUpdateServiceRequest() {
        ServiceRequest request = new ServiceRequest(1, 1, 1, new Date(System.currentTimeMillis()),
                UserRequestType.PURCHASE, "Description", ServiceRequestStatus.CREATED);

        doNothing().when(serviceRequestRepository).updateServiceRequest(request);

        serviceRequestService.updateServiceRequest(request);

        verify(serviceRequestRepository, times(1)).updateServiceRequest(request);
    }

    @Test
    @DisplayName("Should delete a service request by ID")
    void testDeleteServiceRequest() {
        doNothing().when(serviceRequestRepository).deleteServiceRequest(1);

        serviceRequestService.deleteServiceRequest(1);

        verify(serviceRequestRepository, times(1)).deleteServiceRequest(1);
    }

    @Test
    @DisplayName("Should return service requests by status")
    void testGetServiceRequestsByStatus() {
        ServiceRequest request1 = new ServiceRequest(1, 1, 1, new Date(System.currentTimeMillis()),
                UserRequestType.PURCHASE, "Description 1", ServiceRequestStatus.CREATED);
        ServiceRequest request2 = new ServiceRequest(2, 2, 2, new Date(System.currentTimeMillis()),
                UserRequestType.MAINTENANCE, "Description 2", ServiceRequestStatus.CREATED);

        when(serviceRequestRepository.getServiceRequestsByStatus(ServiceRequestStatus.CREATED)).thenReturn(Arrays.asList(request1, request2));

        List<ServiceRequest> requests = serviceRequestService.getServiceRequestsByStatus(ServiceRequestStatus.CREATED);

        assertThat(requests).hasSize(2).contains(request1, request2);
        verify(serviceRequestRepository, times(1)).getServiceRequestsByStatus(ServiceRequestStatus.CREATED);
    }

    @Test
    @DisplayName("Should return service requests by date range")
    void testGetServiceRequestsByDateRange() {
        Date startDate = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        Date endDate = new Date(System.currentTimeMillis());
        ServiceRequest request1 = new ServiceRequest(1, 1, 1, startDate,
                UserRequestType.PURCHASE, "Description 1", ServiceRequestStatus.CREATED);
        ServiceRequest request2 = new ServiceRequest(2, 2, 2, endDate,
                UserRequestType.MAINTENANCE, "Description 2", ServiceRequestStatus.IN_PROGRESS);

        when(serviceRequestRepository.getServiceRequestsByDateRange(startDate, endDate)).thenReturn(Arrays.asList(request1, request2));

        List<ServiceRequest> requests = serviceRequestService.getServiceRequestsByDateRange(startDate, endDate);

        assertThat(requests).hasSize(2).contains(request1, request2);
        verify(serviceRequestRepository, times(1)).getServiceRequestsByDateRange(startDate, endDate);
    }

    @Test
    @DisplayName("Should return service requests by type")
    void testGetServiceRequestsByType() {
        ServiceRequest request1 = new ServiceRequest(1, 1, 1, new Date(System.currentTimeMillis()),
                UserRequestType.PURCHASE, "Description 1", ServiceRequestStatus.CREATED);
        ServiceRequest request2 = new ServiceRequest(2, 2, 2, new Date(System.currentTimeMillis()),
                UserRequestType.PURCHASE, "Description 2", ServiceRequestStatus.IN_PROGRESS);

        when(serviceRequestRepository.getServiceRequestsByType(UserRequestType.PURCHASE)).thenReturn(Arrays.asList(request1, request2));

        List<ServiceRequest> requests = serviceRequestService.getServiceRequestsByType(UserRequestType.PURCHASE);

        assertThat(requests).hasSize(2).contains(request1, request2);
        verify(serviceRequestRepository, times(1)).getServiceRequestsByType(UserRequestType.PURCHASE);
    }

    @Test
    @DisplayName("Should return service requests by customer ID")
    void testGetServiceRequestsByCustomerId() {
        ServiceRequest request1 = new ServiceRequest(1, 1, 1, new Date(System.currentTimeMillis()),
                UserRequestType.PURCHASE, "Description", ServiceRequestStatus.CREATED);

        when(serviceRequestRepository.getServiceRequestsByCustomerId(1)).thenReturn(Arrays.asList(request1));

        List<ServiceRequest> requests = serviceRequestService.getServiceRequestsByCustomerId(1);

        assertThat(requests).contains(request1);
        verify(serviceRequestRepository, times(1)).getServiceRequestsByCustomerId(1);
    }

    @Test
    @DisplayName("Should return a service request by customer ID")
    void testGetServiceRequestByCustomerId() {
        ServiceRequest request = new ServiceRequest(1, 1, 1, new Date(System.currentTimeMillis()),
                UserRequestType.PURCHASE, "Description", ServiceRequestStatus.CREATED);

        when(serviceRequestRepository.getServiceRequestByCustomerId(1)).thenReturn(request);

        ServiceRequest foundRequest = serviceRequestService.getServiceRequestByCustomerId(1);

        assertThat(foundRequest).isEqualTo(request);
        verify(serviceRequestRepository, times(1)).getServiceRequestByCustomerId(1);
    }
}
