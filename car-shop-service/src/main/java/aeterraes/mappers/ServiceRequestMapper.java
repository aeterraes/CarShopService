package aeterraes.mappers;

import aeterraes.dataaccess.entities.ServiceRequest;
import aeterraes.dtos.ServiceRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ServiceRequestMapper {
    ServiceRequestMapper INSTANCE = Mappers.getMapper(ServiceRequestMapper.class);

    ServiceRequestDTO serviceRequestToServiceRequestDTO(ServiceRequest serviceRequest);

    ServiceRequest serviceRequestDTOToServiceRequest(ServiceRequestDTO serviceRequestDTO);
}

