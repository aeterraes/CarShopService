package aeterraes.controllers;

import aeterraes.dataaccess.entities.ServiceRequest;
import aeterraes.dataaccess.models.ServiceRequestStatus;
import aeterraes.dataaccess.models.UserRequestType;
import aeterraes.dtos.ServiceRequestDTO;
import aeterraes.services.ServiceRequestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/service-requests")
public class ServiceRequestController {

    @Autowired
    private final ServiceRequestService serviceRequestService;

    @Autowired
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ServiceRequestDTO>> getAllServiceRequests() {
        List<ServiceRequest> serviceRequests = serviceRequestService.getAllServiceRequests();
        List<ServiceRequestDTO> serviceRequestDTOs = serviceRequests.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(serviceRequestDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequestDTO> getServiceRequestById(@PathVariable("id") int id) {
        ServiceRequest serviceRequest = serviceRequestService.getServiceRequestById(id);
        ServiceRequestDTO serviceRequestDTO = this.toDto(serviceRequest);
        return ResponseEntity.ok(serviceRequestDTO);
    }

    @PostMapping
    public ResponseEntity<ServiceRequestDTO> addServiceRequest(@RequestBody ServiceRequestDTO serviceRequestDTO) {
        ServiceRequest serviceRequest = this.toEntity(serviceRequestDTO);
        serviceRequestService.addServiceRequest(serviceRequest);
        ServiceRequestDTO createdServiceRequestDTO = this.toDto(serviceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdServiceRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceRequestDTO> updateServiceRequest(@PathVariable("id") int id) {
        ServiceRequest existingServiceRequest = serviceRequestService.getServiceRequestById(id);
        serviceRequestService.updateServiceRequest(existingServiceRequest);
        ServiceRequestDTO updatedServiceRequestDTO = this.toDto(existingServiceRequest);
        return ResponseEntity.ok(updatedServiceRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceRequest(@PathVariable("id") int id) {
        serviceRequestService.deleteServiceRequest(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    public ResponseEntity<List<ServiceRequestDTO>> getServiceRequestsByStatus(@RequestParam("status") ServiceRequestStatus status) {
        List<ServiceRequest> serviceRequests = serviceRequestService.getServiceRequestsByStatus(status);
        List<ServiceRequestDTO> serviceRequestDTOs = serviceRequests.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(serviceRequestDTOs);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<ServiceRequestDTO>> getServiceRequestsByDateRange(
            @RequestParam("startDate") Date startDate,
            @RequestParam("endDate") Date endDate) {
        List<ServiceRequest> serviceRequests = serviceRequestService.getServiceRequestsByDateRange((java.sql.Date) startDate, (java.sql.Date) endDate);
        List<ServiceRequestDTO> serviceRequestDTOs = serviceRequests.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(serviceRequestDTOs);
    }

    @GetMapping("/type")
    public ResponseEntity<List<ServiceRequestDTO>> getServiceRequestsByType(@RequestParam("type") UserRequestType type) {
        List<ServiceRequest> serviceRequests = serviceRequestService.getServiceRequestsByType(type);
        List<ServiceRequestDTO> serviceRequestDTOs = serviceRequests.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(serviceRequestDTOs);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<ServiceRequestDTO>> getServiceRequestsByCustomerId(@PathVariable("id") int id) {
        List<ServiceRequest> serviceRequests = serviceRequestService.getServiceRequestsByCustomerId(id);
        List<ServiceRequestDTO> serviceRequestDTOs = serviceRequests.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(serviceRequestDTOs);
    }

    private ServiceRequest toEntity(ServiceRequestDTO serviceRequestDTO) {
        return modelMapper.map(serviceRequestDTO, ServiceRequest.class);
    }

    private ServiceRequestDTO toDto(ServiceRequest serviceRequest) {
        return modelMapper.map(serviceRequest, ServiceRequestDTO.class);
    }
}

