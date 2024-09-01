package aeterraes.controllers;

import aeterraes.dataaccess.entities.ServiceRequest;
import aeterraes.dataaccess.models.ServiceRequestStatus;
import aeterraes.dataaccess.models.UserRequestType;
import aeterraes.dtos.ServiceRequestDTO;
import aeterraes.services.ServiceRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ServiceRequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ServiceRequestService serviceRequestService;

    @Mock
    private ModelMapper modelMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServiceRequestController serviceRequestController = new ServiceRequestController(serviceRequestService, modelMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(serviceRequestController).build();
    }

    @Test
    public void testGetAllServiceRequests() throws Exception {
        ServiceRequest serviceRequest = new ServiceRequest(1, 1, 1, Date.valueOf("2024-08-20"), UserRequestType.MAINTENANCE, "Fix troubles with wheels", ServiceRequestStatus.CREATED);
        ServiceRequestDTO serviceRequestDTO = new ServiceRequestDTO(1, 1, 1, Date.valueOf("2024-08-20"), "MAINTENANCE", "Fix troubles with wheels", "CREATED");

        List<ServiceRequest> serviceRequests = List.of(serviceRequest);

        when(serviceRequestService.getAllServiceRequests()).thenReturn(serviceRequests);
        when(modelMapper.map(any(ServiceRequest.class), any())).thenReturn(serviceRequestDTO);

        mockMvc.perform(get("/service-requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].requestId").value(1))
                .andExpect(jsonPath("$[0].requestType").value("MAINTENANCE"))
                .andExpect(jsonPath("$[0].requestDescription").value("Fix troubles with wheels"));
    }

    @Test
    public void testGetServiceRequestById() throws Exception {
        ServiceRequest serviceRequest = new ServiceRequest(1, 1, 1, Date.valueOf("2024-08-20"), UserRequestType.MAINTENANCE, "Fix troubles with wheels", ServiceRequestStatus.CREATED);
        ServiceRequestDTO serviceRequestDTO = new ServiceRequestDTO(1, 1, 1, Date.valueOf("2024-08-20"), "MAINTENANCE", "Fix troubles with wheels", "CREATED");

        when(serviceRequestService.getServiceRequestById(1)).thenReturn(serviceRequest);
        when(modelMapper.map(any(ServiceRequest.class), any())).thenReturn(serviceRequestDTO);

        mockMvc.perform(get("/service-requests/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestId").value(1))
                .andExpect(jsonPath("$.requestType").value("MAINTENANCE"))
                .andExpect(jsonPath("$.requestDescription").value("Fix troubles with wheels"));
    }

    @Test
    public void testAddServiceRequest() throws Exception {
        ServiceRequest serviceRequest = new ServiceRequest(1, 1, 1, Date.valueOf("2024-08-20"), UserRequestType.MAINTENANCE, "Fix troubles with wheels", ServiceRequestStatus.CREATED);
        ServiceRequestDTO serviceRequestDTO = new ServiceRequestDTO(1, 1, 1, Date.valueOf("2024-08-20"), "MAINTENANCE", "Fix troubles with wheels", "CREATED");

        when(modelMapper.map(any(ServiceRequestDTO.class), any())).thenReturn(serviceRequest);
        when(modelMapper.map(any(ServiceRequest.class), any())).thenReturn(serviceRequestDTO);

        mockMvc.perform(post("/service-requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serviceRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.requestId").value(1))
                .andExpect(jsonPath("$.requestType").value("MAINTENANCE"))
                .andExpect(jsonPath("$.requestDescription").value("Fix troubles with wheels"));
    }

    @Test
    public void testUpdateServiceRequest() throws Exception {
        ServiceRequest serviceRequest = new ServiceRequest(1, 1, 1, Date.valueOf("2024-08-20"), UserRequestType.MAINTENANCE, "Fix troubles with wheels", ServiceRequestStatus.CREATED);
        ServiceRequestDTO serviceRequestDTO = new ServiceRequestDTO(1, 1, 1, Date.valueOf("2024-08-20"), "MAINTENANCE", "Fix troubles with wheels", "CREATED");

        when(serviceRequestService.getServiceRequestById(1)).thenReturn(serviceRequest);
        when(modelMapper.map(any(ServiceRequest.class), any())).thenReturn(serviceRequestDTO);

        mockMvc.perform(put("/service-requests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(serviceRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestId").value(1))
                .andExpect(jsonPath("$.requestType").value("MAINTENANCE"))
                .andExpect(jsonPath("$.requestDescription").value("Fix troubles with wheels"));
    }

    @Test
    public void testDeleteServiceRequest() throws Exception {
        mockMvc.perform(delete("/service-requests/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetServiceRequestsByStatus() throws Exception {
        ServiceRequest serviceRequest = new ServiceRequest(1, 1, 1, Date.valueOf("2024-08-20"), UserRequestType.MAINTENANCE, "Fix troubles with wheels", ServiceRequestStatus.CREATED);
        ServiceRequestDTO serviceRequestDTO = new ServiceRequestDTO(1, 1, 1, Date.valueOf("2024-08-20"), "MAINTENANCE", "Fix troubles with wheels", "CREATED");

        List<ServiceRequest> serviceRequests = List.of(serviceRequest);

        when(serviceRequestService.getServiceRequestsByStatus(ServiceRequestStatus.CREATED)).thenReturn(serviceRequests);
        when(modelMapper.map(any(ServiceRequest.class), any())).thenReturn(serviceRequestDTO);

        mockMvc.perform(get("/service-requests/status")
                        .param("status", "CREATED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].requestId").value(1))
                .andExpect(jsonPath("$[0].requestType").value("MAINTENANCE"))
                .andExpect(jsonPath("$[0].requestDescription").value("Fix troubles with wheels"));
    }

    @Test
    public void testGetServiceRequestsByType() throws Exception {
        ServiceRequest serviceRequest = new ServiceRequest(1, 1, 1, Date.valueOf("2024-08-20"), UserRequestType.MAINTENANCE, "Fix troubles with wheels", ServiceRequestStatus.CREATED);
        ServiceRequestDTO serviceRequestDTO = new ServiceRequestDTO(1, 1, 1, Date.valueOf("2024-08-20"), "MAINTENANCE", "Fix troubles with wheels", "CREATED");

        List<ServiceRequest> serviceRequests = List.of(serviceRequest);

        when(serviceRequestService.getServiceRequestsByType(UserRequestType.MAINTENANCE)).thenReturn(serviceRequests);
        when(modelMapper.map(any(ServiceRequest.class), any())).thenReturn(serviceRequestDTO);

        mockMvc.perform(get("/service-requests/type")
                        .param("type", "MAINTENANCE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].requestId").value(1))
                .andExpect(jsonPath("$[0].requestType").value("MAINTENANCE"))
                .andExpect(jsonPath("$[0].requestDescription").value("Fix troubles with wheels"));
    }

    @Test
    public void testGetServiceRequestsByCustomerId() throws Exception {
        ServiceRequest serviceRequest = new ServiceRequest(1, 1, 1, Date.valueOf("2024-08-20"), UserRequestType.MAINTENANCE, "Fix troubles with wheels", ServiceRequestStatus.CREATED);
        ServiceRequestDTO serviceRequestDTO = new ServiceRequestDTO(1, 1, 1, Date.valueOf("2024-08-20"), "MAINTENANCE", "Fix troubles with wheels", "CREATED");

        List<ServiceRequest> serviceRequests = List.of(serviceRequest);

        when(serviceRequestService.getServiceRequestsByCustomerId(1)).thenReturn(serviceRequests);
        when(modelMapper.map(any(ServiceRequest.class), any())).thenReturn(serviceRequestDTO);

        mockMvc.perform(get("/service-requests/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].requestId").value(1))
                .andExpect(jsonPath("$[0].requestType").value("MAINTENANCE"))
                .andExpect(jsonPath("$[0].requestDescription").value("Fix troubles with wheels"));
    }
}
