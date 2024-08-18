package aeterraes.mappers;

import aeterraes.dataaccess.entities.ServiceRequest;
import aeterraes.dataaccess.models.ServiceRequestStatus;
import aeterraes.dataaccess.models.UserRequestType;
import aeterraes.dtos.ServiceRequestDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-18T20:45:23+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
public class ServiceRequestMapperImpl implements ServiceRequestMapper {

    @Override
    public ServiceRequestDTO serviceRequestToServiceRequestDTO(ServiceRequest serviceRequest) {
        if ( serviceRequest == null ) {
            return null;
        }

        ServiceRequestDTO serviceRequestDTO = new ServiceRequestDTO();

        serviceRequestDTO.setRequestId( serviceRequest.getRequestId() );
        serviceRequestDTO.setCustomerId( serviceRequest.getCustomerId() );
        serviceRequestDTO.setCarId( serviceRequest.getCarId() );
        serviceRequestDTO.setRequestDate( serviceRequest.getRequestDate() );
        if ( serviceRequest.getRequestType() != null ) {
            serviceRequestDTO.setRequestType( serviceRequest.getRequestType().name() );
        }
        serviceRequestDTO.setRequestDescription( serviceRequest.getRequestDescription() );
        if ( serviceRequest.getRequestStatus() != null ) {
            serviceRequestDTO.setRequestStatus( serviceRequest.getRequestStatus().name() );
        }

        return serviceRequestDTO;
    }

    @Override
    public ServiceRequest serviceRequestDTOToServiceRequest(ServiceRequestDTO serviceRequestDTO) {
        if ( serviceRequestDTO == null ) {
            return null;
        }

        ServiceRequest serviceRequest = new ServiceRequest();

        serviceRequest.setRequestId( serviceRequestDTO.getRequestId() );
        serviceRequest.setCustomerId( serviceRequestDTO.getCustomerId() );
        serviceRequest.setCarId( serviceRequestDTO.getCarId() );
        serviceRequest.setRequestDate( serviceRequestDTO.getRequestDate() );
        if ( serviceRequestDTO.getRequestType() != null ) {
            serviceRequest.setRequestType( Enum.valueOf( UserRequestType.class, serviceRequestDTO.getRequestType() ) );
        }
        serviceRequest.setRequestDescription( serviceRequestDTO.getRequestDescription() );
        if ( serviceRequestDTO.getRequestStatus() != null ) {
            serviceRequest.setRequestStatus( Enum.valueOf( ServiceRequestStatus.class, serviceRequestDTO.getRequestStatus() ) );
        }

        return serviceRequest;
    }
}
