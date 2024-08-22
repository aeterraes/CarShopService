package aeterraes.mappers;

import aeterraes.dataaccess.entities.Customer;
import aeterraes.dataaccess.models.Role;
import aeterraes.dtos.CustomerDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-18T20:45:23+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerDTO customerToCustomerDTO(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setCustomerId( customer.getCustomerId() );
        customerDTO.setFirstName( customer.getFirstName() );
        customerDTO.setLastName( customer.getLastName() );
        customerDTO.setEmail( customer.getEmail() );
        customerDTO.setPassword( customer.getPassword() );
        if ( customer.getRole() != null ) {
            customerDTO.setRole( customer.getRole().name() );
        }

        return customerDTO;
    }

    @Override
    public Customer customerDTOToCustomer(CustomerDTO customerDTO) {
        if ( customerDTO == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setCustomerId( customerDTO.getCustomerId() );
        customer.setFirstName( customerDTO.getFirstName() );
        customer.setLastName( customerDTO.getLastName() );
        customer.setEmail( customerDTO.getEmail() );
        customer.setPassword( customerDTO.getPassword() );
        if ( customerDTO.getRole() != null ) {
            customer.setRole( Enum.valueOf( Role.class, customerDTO.getRole() ) );
        }

        return customer;
    }
}
