package aeterraes.controllers;

import aeterraes.dataaccess.entities.Order;
import aeterraes.utils.logging.LoggerConfig;
import aeterraes.dataaccess.models.OrderStatus;
import aeterraes.services.OrderService;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

public class OrderController {

    private final OrderService orderService;
    private static final Logger logger = LoggerConfig.getLogger();

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public void getAllOrders() {
        logger.info("Fetching all orders");
        List<Order> orders = orderService.getAllOrders();
        orders.forEach(System.out::println);
        logger.info("Fetched " + orders.size() + " orders");
    }

    public void getOrderById(int id) {
        logger.info("Fetching order with ID: " + id);
        Order order = orderService.getOrderById(id);
        if (order != null) {
            System.out.println(order);
            logger.info("Fetched order: " + order);
        } else {
            logger.warning("Order with ID " + id + " not found");
        }
    }

    public void addOrder(Order order) {
        logger.info("Adding new order: " + order);
        orderService.addOrder(order);
        logger.info("Order added: " + order);
    }

    public void updateOrder(Order order) {
        logger.info("Updating order: " + order);
        orderService.updateOrder(order);
        logger.info("Order updated: " + order);
    }

    public void deleteOrder(int id) {
        logger.info("Deleting order with ID: " + id);
        orderService.deleteOrder(id);
        logger.info("Order with ID " + id + " deleted");
    }

    public void getOrdersByStatus(OrderStatus status) {
        logger.info("Fetching orders with status: " + status);
        List<Order> orders = orderService.getOrdersByStatus(status);
        orders.forEach(System.out::println);
        logger.info("Fetched " + orders.size() + " orders with status " + status);
    }

    public void getOrdersByDateRange(Date startDate, Date endDate) {
        logger.info("Fetching orders from " + startDate + " to " + endDate);
        List<Order> orders = orderService.getOrdersByDateRange(startDate, endDate);
        orders.forEach(System.out::println);
        logger.info("Fetched " + orders.size() + " orders from " + startDate + " to " + endDate);
    }

    public void getOrdersByPriceRange(double minPrice, double maxPrice) {
        logger.info("Fetching orders with price between " + minPrice + " and " + maxPrice);
        List<Order> orders = orderService.getOrdersByPriceRange(minPrice, maxPrice);
        orders.forEach(System.out::println);
        logger.info("Fetched " + orders.size() + " orders with price between " + minPrice + " and " + maxPrice);
    }

    public void updateOrderStatus(int id, OrderStatus status) {
        logger.info("Updating status of order with ID: " + id + " to " + status);
        orderService.updateOrderStatus(id, status);
        logger.info("Updated status of order with ID: " + id + " to " + status);
    }

    public void getOrderByCustomerId(int id) {
        logger.info("Fetching order with customer ID: " + id);
        Order order = orderService.getOrderByCustomerId(id);
        if (order != null) {
            System.out.println(order);
            logger.info("Fetched order: " + order);
        } else {
            logger.warning("Order with customer ID " + id + " not found");
        }
    }
}
