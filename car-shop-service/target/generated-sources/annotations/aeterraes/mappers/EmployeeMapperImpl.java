package aeterraes.mappers;

import aeterraes.dataaccess.entities.Employee;
import aeterraes.dataaccess.models.Role;
import aeterraes.dtos.EmployeeDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-18T20:45:23+0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeDTO employeeToEmployeeDTO(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setEmployeeId( employee.getEmployeeId() );
        employeeDTO.setFirstName( employee.getFirstName() );
        employeeDTO.setLastName( employee.getLastName() );
        employeeDTO.setEmail( employee.getEmail() );
        employeeDTO.setPassword( employee.getPassword() );
        if ( employee.getRole() != null ) {
            employeeDTO.setRole( employee.getRole().name() );
        }

        return employeeDTO;
    }

    @Override
    public Employee employeeDTOToEmployee(EmployeeDTO employeeDTO) {
        if ( employeeDTO == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setEmployeeId( employeeDTO.getEmployeeId() );
        employee.setFirstName( employeeDTO.getFirstName() );
        employee.setLastName( employeeDTO.getLastName() );
        employee.setEmail( employeeDTO.getEmail() );
        employee.setPassword( employeeDTO.getPassword() );
        if ( employeeDTO.getRole() != null ) {
            employee.setRole( Enum.valueOf( Role.class, employeeDTO.getRole() ) );
        }

        return employee;
    }
}
