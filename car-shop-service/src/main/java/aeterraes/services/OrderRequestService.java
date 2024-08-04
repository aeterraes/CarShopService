package aeterraes.services;

import aeterraes.entities.Car;
import aeterraes.entities.Order;
import aeterraes.entities.ServiceRequest;
import aeterraes.models.OrderStatus;
import aeterraes.models.ServiceRequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderRequestService {

    private List<Order> orders = new ArrayList<>();
    private List<ServiceRequest> serviceRequests = new ArrayList<>();

    /**
     * Retrieve a list of all orders
     *
     * @return List of all orders
     */
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    /**
     * Retrieve an order by its ID
     *
     * @param id The ID of the order
     * @return The order with the specified ID, or null if not found
     */
    public Order getOrderById(int id) {
        return orders.stream()
                .filter(order -> order.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Add a new order if all cars in the order are available for sale
     *
     * @param order The order to add
     * @return True if the order was added successfully, false otherwise
     */
    public boolean addOrder(Order order) {
        boolean allCarsAreAvailable = order.getCars().stream().allMatch(Car::isAvailableForSale);
        if (allCarsAreAvailable) {
            order.calculateTotalPrice();
            orders.add(order);
            return true;
        }
        return false;
    }

    /**
     * Update an existing order by its ID
     *
     * @param id           The ID of the order to update
     * @param updatedOrder The updated order information
     */
    public void updateOrderById(int id, Order updatedOrder) {
        orders = orders.stream()
                .map(order -> order.getId() == id ? updatedOrder : order)
                .collect(Collectors.toList());
    }

    /**
     * Delete an order by its ID
     *
     * @param id The ID of the order to delete
     */
    public void deleteOrderById(int id) {
        orders.removeIf(order -> order.getId() == id);
    }

    /**
     * Retrieve a list of all service requests
     *
     * @return List of all service requests
     */
    public List<ServiceRequest> getAllServiceRequests() {
        return new ArrayList<>(serviceRequests);
    }

    /**
     * Retrieve a service request by its ID
     *
     * @param id The ID of the service request
     * @return The service request with the specified ID, or null if not found
     */
    public ServiceRequest getServiceRequestById(int id) {
        return serviceRequests.stream()
                .filter(request -> request.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Add a new service request
     *
     * @param serviceRequest The service request to add
     */
    public void addServiceRequest(ServiceRequest serviceRequest) {
        serviceRequests.add(serviceRequest);
    }

    /**
     * Update an existing service request by its ID
     *
     * @param id                    The ID of the service request to update
     * @param updatedServiceRequest The updated service request information
     */
    public void updateServiceRequestById(int id, ServiceRequest updatedServiceRequest) {
        serviceRequests = serviceRequests.stream()
                .map(request -> request.getId() == id ? updatedServiceRequest : request)
                .collect(Collectors.toList());
    }

    /**
     * Delete a service request by its ID
     *
     * @param id The ID of the service request to delete
     */
    public void deleteServiceRequestById(int id) {
        serviceRequests.removeIf(request -> request.getId() == id);
    }

    /**
     * Retrieve a list of service requests by customer ID
     *
     * @param customerId The customer ID associated with the service requests
     * @return List of service requests for the specified customer ID
     */
    public List<ServiceRequest> getServiceRequestsByCustomerId(int customerId) {
        return serviceRequests.stream()
                .filter(request -> request.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of service requests by car ID
     *
     * @param carId The car ID associated with the service requests
     * @return List of service requests for the specified car ID
     */
    public List<ServiceRequest> getServiceRequestsByCarId(int carId) {
        return serviceRequests.stream()
                .filter(request -> request.getCarId() == carId)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of service requests by their status
     *
     * @param status The status of the service requests
     * @return List of service requests with the specified status
     */
    public List<ServiceRequest> getServiceRequestsByStatus(ServiceRequestStatus status) {
        return serviceRequests.stream()
                .filter(request -> request.getRequestStatus() == status)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of orders by customer ID
     *
     * @param customerId The customer ID associated with the orders
     * @return List of orders for the specified customer ID
     */
    public List<Order> getOrdersByCustomerId(int customerId) {
        return orders.stream()
                .filter(order -> order.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of orders by their status
     *
     * @param status The status of the orders
     * @return List of orders with the specified status
     */
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orders.stream()
                .filter(order -> order.getStatus() == status)
                .collect(Collectors.toList());
    }
}
