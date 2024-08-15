package aeterraes.services;

import aeterraes.dataaccess.entities.Order;
import aeterraes.dataaccess.models.OrderStatus;
import aeterraes.dataaccess.repositories.OrderRepository;

import java.sql.Date;
import java.util.List;

public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Order getOrderById(int id) {
        return orderRepository.getOrderById(id);
    }

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void updateOrder(Order order) {
        orderRepository.updateOrder(order);
    }

    public void deleteOrder(int id) {
        orderRepository.deleteOrder(id);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.getOrdersByStatus(status);
    }

    public List<Order> getOrdersByDateRange(Date startDate, Date endDate) {
        return orderRepository.getOrdersByDateRange(startDate, endDate);
    }

    public List<Order> getOrdersByPriceRange(double minPrice, double maxPrice) {
        return orderRepository.getOrdersByPriceRange(minPrice, maxPrice);
    }

    public void updateOrderStatus(int id, OrderStatus status) {
        orderRepository.updateOrderStatus(id, status);
    }

    public Order getOrderByCustomerId(int id) {
        return orderRepository.getOrderByCustomerId(id);
    }
}

