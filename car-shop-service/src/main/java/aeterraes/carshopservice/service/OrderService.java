package aeterraes.carshopservice.service;

import aeterraes.carshopservice.dataaccess.entities.Order;
import aeterraes.carshopservice.dataaccess.models.OrderStatus;
import aeterraes.carshopservice.dataaccess.repositories.OrderRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public void updateOrder(Order order) {
        if (orderRepository.existsById(order.getOrderId())) {
            orderRepository.save(order);
        }
    }

    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public List<Order> getOrdersByDateRange(Date startDate, Date endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }

    public List<Order> getOrdersByPriceRange(double minPrice, double maxPrice) {
        return orderRepository.findByTotalPriceBetween(minPrice, maxPrice);
    }

    public void updateOrderStatus(int id, OrderStatus status) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        return orderRepository.findByCustomerCustomerId(customerId);
    }

    public Order getOrderByCustomerId(int id) {
        return (Order) orderRepository.findByCustomerCustomerId(id);
    }
}

