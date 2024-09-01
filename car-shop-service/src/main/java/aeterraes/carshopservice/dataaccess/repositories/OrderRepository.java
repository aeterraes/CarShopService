package aeterraes.carshopservice.dataaccess.repositories;

import aeterraes.carshopservice.dataaccess.entities.Order;
import aeterraes.carshopservice.dataaccess.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByOrderDateBetween(Date startDate, Date endDate);

    List<Order> findByTotalPriceBetween(double minPrice, double maxPrice);

    List<Order> findByCustomerCustomerId(int customerId);
}
