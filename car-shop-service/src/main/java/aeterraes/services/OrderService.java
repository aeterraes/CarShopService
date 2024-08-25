package aeterraes.services;

import aeterraes.aop.annotation.Audit;
import aeterraes.dataaccess.entities.Order;
import aeterraes.dataaccess.models.OrderStatus;
import aeterraes.dataaccess.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Audit
    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    @Audit
    public Order getOrderById(int id) {
        return orderRepository.getOrderById(id);
    }

    @Audit
    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    @Audit
    public void updateOrder(Order order) {
        orderRepository.updateOrder(order);
    }

    @Audit
    public void deleteOrder(int id) {
        orderRepository.deleteOrder(id);
    }

    @Audit
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.getOrdersByStatus(status);
    }

    @Audit
    public List<Order> getOrdersByDateRange(Date startDate, Date endDate) {
        return orderRepository.getOrdersByDateRange((java.sql.Date) startDate, (java.sql.Date) endDate);
    }

    @Audit
    public List<Order> getOrdersByPriceRange(double minPrice, double maxPrice) {
        return orderRepository.getOrdersByPriceRange(minPrice, maxPrice);
    }

    @Audit
    public void updateOrderStatus(int id, OrderStatus status) {
        orderRepository.updateOrderStatus(id, status);
    }

    @Audit
    public Order getOrderByCustomerId(int id) {
        return orderRepository.getOrderByCustomerId(id);
    }
}

