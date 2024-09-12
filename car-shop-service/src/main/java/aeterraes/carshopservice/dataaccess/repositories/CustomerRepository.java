package aeterraes.carshopservice.dataaccess.repositories;

import aeterraes.carshopservice.dataaccess.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByEmail(String email);
}
