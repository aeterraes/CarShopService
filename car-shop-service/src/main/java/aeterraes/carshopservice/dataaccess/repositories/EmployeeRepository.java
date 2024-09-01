package aeterraes.carshopservice.dataaccess.repositories;

import aeterraes.carshopservice.dataaccess.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByEmail(String email);
}
