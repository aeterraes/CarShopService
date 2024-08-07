package aeterraes.controllers;

import aeterraes.entities.Order;
import aeterraes.entities.ServiceRequest;
import aeterraes.entities.User;
import aeterraes.logging.LoggerConfig;
import aeterraes.models.OrderStatus;
import aeterraes.models.Role;
import aeterraes.models.ServiceRequestStatus;
import aeterraes.security.AuthService;
import aeterraes.services.OrderRequestService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.logging.Logger;

@Setter
@Getter
public class OrderRequestController {
    private static final Logger logger = LoggerConfig.getLogger();
    private final OrderRequestService orderRequestService;
    private final AuthService authService;

    /**
     * Constructor for OrderRequestController
     *
     * @param orderRequestService Service for managing order requests
     * @param authService         Service for authentication and authorization
     */
    public OrderRequestController(OrderRequestService orderRequestService, AuthService authService) {
        this.orderRequestService = orderRequestService;
        this.authService = authService;
    }

    /**
     * Print a list of all orders if the user has manager access
     *
     * @param currentUser The current user requesting the orders
     */
    public void getAllOrders(User currentUser) {
        if (authService.hasAccess(currentUser, Role.MANAGER)) {
            logger.info("Get all orders");
            List<Order> orders = orderRequestService.getAllOrders();
            System.out.println("All Orders:");
            orders.forEach(order -> System.out.println(order.toString()));
        } else {
            logger.warning("Access denied");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Print information about an order by ID
     *
     * @param id The ID of the order
     */
    public void getOrderById(int id) {
        Order order = orderRequestService.getOrderById(id);
        if (order != null) {
            logger.info("Order Info by ID " + id + ": " + order.toString());
            System.out.println("Order Info by ID " + id + ":");
            System.out.println(order.toString());
        } else {
            logger.warning("Order with ID " + id + " not found");
            System.out.println("Order with ID " + id + " not found.");
        }
    }

    /**
     * Add a new order
     *
     * @param order The order to add
     */
    public void addOrder(Order order) {
        boolean resultOfAddingOrder = orderRequestService.addOrder(order);
        if (resultOfAddingOrder) {
            logger.info("Order added: " + order.toString());
            System.out.println("Order with ID " + order.getId() + " added successfully.");
            System.out.println(order.toString());
        } else {
            logger.warning("Error adding order with ID " + order.getId());
            System.out.println("Something went wrong with order with ID " + order.getId());
        }
    }

    /**
     * Update an existing order by ID
     *
     * @param id           The ID of the order
     * @param updatedOrder The updated order details
     */
    public void updateOrderById(int id, Order updatedOrder) {
        orderRequestService.updateOrderById(id, updatedOrder);
        logger.info("Order updated: " + updatedOrder.toString());
        System.out.println("Order updated successfully with ID " + id + ":");
        System.out.println(updatedOrder.toString());
    }

    /**
     * Delete an order by  ID
     *
     * @param id The ID of the order
     */
    public void deleteOrderById(int id) {
        logger.info("Delete order with ID " + id);
        orderRequestService.deleteOrderById(id);
        System.out.println("Order with ID " + id + " deleted successfully.");
    }

    /**
     * Print a list of all service requests if the user has manager access
     *
     * @param currentUser The current user requesting the service requests
     */
    public void getAllServiceRequests(User currentUser) {
        if (authService.hasAccess(currentUser, Role.MANAGER)) {
            logger.info("Get all service requests");
            List<ServiceRequest> serviceRequests = orderRequestService.getAllServiceRequests();
            System.out.println("All Service Requests:");
            serviceRequests.forEach(request -> System.out.println(request.toString()));
        } else {
            logger.warning("Access denied");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Print information about a service request by ID
     *
     * @param id The ID of the service request
     */
    public void getServiceRequestById(int id) {
        ServiceRequest request = orderRequestService.getServiceRequestById(id);
        if (request != null) {
            logger.info("Service Request Info by ID " + id + ": " + request.toString());
            System.out.println("Service Request Info by ID " + id + ":");
            System.out.println(request.toString());
        } else {
            logger.warning("Service Request with ID " + id + " not found");
            System.out.println("Service Request with ID " + id + " not found.");
        }
    }

    /**
     * Add a new service request
     *
     * @param serviceRequest The service request to add
     */
    public void addServiceRequest(ServiceRequest serviceRequest) {
        orderRequestService.addServiceRequest(serviceRequest);
        logger.info("Service Request added: " + serviceRequest.toString());
        System.out.println("Service Request added successfully:");
        System.out.println(serviceRequest.toString());
    }

    /**
     * Update an existing service request by ID
     *
     * @param id                    The ID of the service request
     * @param updatedServiceRequest The updated service request details
     */
    public void updateServiceRequestById(int id, ServiceRequest updatedServiceRequest) {
        logger.info("Update service request with ID " + id + ": " + updatedServiceRequest.toString());
        orderRequestService.updateServiceRequestById(id, updatedServiceRequest);
        System.out.println("Service Request updated successfully with ID " + id + ":");
        System.out.println(updatedServiceRequest.toString());
    }

    /**
     * Delete a service request by ID
     *
     * @param id The ID of the service request
     */
    public void deleteServiceRequestById(int id) {
        logger.info("Delete service request with ID " + id);
        orderRequestService.deleteServiceRequestById(id);
        System.out.println("Service Request with ID " + id + " deleted successfully.");
    }

    /**
     * Print service requests for a specific customer
     *
     * @param customerId The ID of the customer
     */
    public void getServiceRequestsByCustomerId(int customerId) {
        logger.info("Get service requests by customer id " + customerId);
        List<ServiceRequest> requests = orderRequestService.getServiceRequestsByCustomerId(customerId);
        System.out.println("Service Requests for Customer ID " + customerId + ":");
        requests.forEach(request -> System.out.println(request.toString()));
    }

    /**
     * Print service requests for a specific car
     *
     * @param carId The ID of the car
     */
    public void getServiceRequestsByCarId(int carId) {
        logger.info("Get service requests by car id " + carId);
        List<ServiceRequest> requests = orderRequestService.getServiceRequestsByCarId(carId);
        System.out.println("Service Requests for Car ID " + carId + ":");
        requests.forEach(request -> System.out.println(request.toString()));
    }

    /**
     * Print service requests by their status
     *
     * @param status The status of the service requests
     */
    public void getServiceRequestsByStatus(ServiceRequestStatus status) {
        logger.info("Get service requests by status " + status);
        List<ServiceRequest> requests = orderRequestService.getServiceRequestsByStatus(status);
        System.out.println("Service Requests with Status " + status + ":");
        requests.forEach(request -> System.out.println(request.toString()));
    }

    /**
     * Print orders for a specific customer
     *
     * @param customerId The ID of the customer
     */
    public void getOrdersByCustomerId(int customerId) {
        logger.info("Get orders by customer id " + customerId);
        List<Order> orders = orderRequestService.getOrdersByCustomerId(customerId);
        System.out.println("Orders for Customer ID " + customerId + ":");
        orders.forEach(order -> System.out.println(order.toString()));
    }

    /**
     * Print orders by their status
     *
     * @param status The status of the orders
     */
    public void getOrdersByStatus(OrderStatus status) {
        logger.info("Get orders by status " + status);
        List<Order> orders = orderRequestService.getOrdersByStatus(status);
        System.out.println("Orders with Status " + status + ":");
        orders.forEach(order -> System.out.println(order.toString()));
    }
}
