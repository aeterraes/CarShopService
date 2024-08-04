package aeterraes.controllers;

import aeterraes.entities.*;
import aeterraes.models.OrderStatus;
import aeterraes.models.Role;
import aeterraes.models.ServiceRequestStatus;
import aeterraes.models.UserRequestType;
import aeterraes.security.AuthService;
import aeterraes.services.OrderRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderRequestControllerTest {
    @Mock
    private OrderRequestService orderRequestService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private OrderRequestController orderRequestController;

    private Employee maksimPlotnikov;
    private Customer alinaAlexandrova;
    private Car hyundaiSolaris;
    private Car mazdaCX90;
    private Order order1;
    private ServiceRequest serviceRequest1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        maksimPlotnikov = new Employee(2, "Maksim", "Plotnikov", "maxplotnikov2000@somemailservice.com", "qwertyQ", Role.MANAGER);
        alinaAlexandrova = new Customer(4, "Alina", "Alexandrova",
                "alinochkasolnyshko1998@somemailservice.com", "passwordpassword", Role.USER, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

        hyundaiSolaris = new Car("Hyundai", "Solaris",
                2018, 10000, "Blue", "Gas",
                100, 12, "Independent",
                "Mechanic", "Backward", 1025000, true);
        mazdaCX90 = new Car("Mazda", "CX-90", 2023,
                0, "Red", "Gas", 328,
                7, "Independent", "Automatic",
                "Independent", 8700000, true);

        order1 = new Order(1, 1, List.of(hyundaiSolaris), 1025000,
                OrderStatus.CREATED, new Date(2024 - 1900, 4, 6));

        serviceRequest1 = new ServiceRequest(1, 1, 1,
                new Date(2024, 4, 6), UserRequestType.MAINTENANCE,
                "Fix engine problems", ServiceRequestStatus.CREATED);
    }

    @Test
    void getAllOrders() {
        when(authService.hasAccess(maksimPlotnikov, Role.MANAGER)).thenReturn(true);
        when(orderRequestService.getAllOrders()).thenReturn(List.of(order1));

        orderRequestController.getAllOrders(maksimPlotnikov);

        verify(orderRequestService).getAllOrders();
    }

    @Test
    void getOrderById() {
        when(orderRequestService.getOrderById(1)).thenReturn(order1);

        orderRequestController.getOrderById(1);

        verify(orderRequestService).getOrderById(1);
    }

    @Test
    void addOrder() {
        orderRequestController.addOrder(order1);

        verify(orderRequestService).addOrder(order1);
    }

    @Test
    void updateOrderById() {
        orderRequestController.updateOrderById(1, order1);

        verify(orderRequestService).updateOrderById(1, order1);
    }

    @Test
    void deleteOrderById() {
        orderRequestController.deleteOrderById(1);

        verify(orderRequestService).deleteOrderById(1);
    }

    @Test
    void getAllServiceRequests() {
        when(authService.hasAccess(maksimPlotnikov, Role.MANAGER)).thenReturn(true);
        when(orderRequestService.getAllServiceRequests()).thenReturn(List.of(serviceRequest1));

        orderRequestController.getAllServiceRequests(maksimPlotnikov);

        verify(orderRequestService).getAllServiceRequests();
    }

    @Test
    void getServiceRequestById() {
        when(orderRequestService.getServiceRequestById(1)).thenReturn(serviceRequest1);

        orderRequestController.getServiceRequestById(1);

        verify(orderRequestService).getServiceRequestById(1);
    }

    @Test
    void addServiceRequest() {
        orderRequestController.addServiceRequest(serviceRequest1);

        verify(orderRequestService).addServiceRequest(serviceRequest1);
    }

    @Test
    void updateServiceRequestById() {
        orderRequestController.updateServiceRequestById(1, serviceRequest1);

        verify(orderRequestService).updateServiceRequestById(1, serviceRequest1);
    }

    @Test
    void deleteServiceRequestById() {
        orderRequestController.deleteServiceRequestById(1);

        verify(orderRequestService).deleteServiceRequestById(1);
    }

    @Test
    void getServiceRequestsByCustomerId() {
        when(orderRequestService.getServiceRequestsByCustomerId(1)).thenReturn(List.of(serviceRequest1));

        orderRequestController.getServiceRequestsByCustomerId(1);

        verify(orderRequestService).getServiceRequestsByCustomerId(1);
    }

    @Test
    void getServiceRequestsByCarId() {
        when(orderRequestService.getServiceRequestsByCarId(1)).thenReturn(List.of(serviceRequest1));

        orderRequestController.getServiceRequestsByCarId(1);

        verify(orderRequestService).getServiceRequestsByCarId(1);
    }

    @Test
    void getServiceRequestsByStatus() {
        when(orderRequestService.getServiceRequestsByStatus(ServiceRequestStatus.CREATED)).thenReturn(List.of(serviceRequest1));

        orderRequestController.getServiceRequestsByStatus(ServiceRequestStatus.CREATED);

        verify(orderRequestService).getServiceRequestsByStatus(ServiceRequestStatus.CREATED);
    }

    @Test
    void getOrdersByCustomerId() {
        when(orderRequestService.getOrdersByCustomerId(1)).thenReturn(List.of(order1));

        orderRequestController.getOrdersByCustomerId(1);

        verify(orderRequestService).getOrdersByCustomerId(1);
    }

    @Test
    void getOrdersByStatus() {
        when(orderRequestService.getOrdersByStatus(OrderStatus.CREATED)).thenReturn(List.of(order1));

        orderRequestController.getOrdersByStatus(OrderStatus.CREATED);

        verify(orderRequestService).getOrdersByStatus(OrderStatus.CREATED);
    }

    @Test
    void addOrderWithUnavailableCar() {

        Car unavailableCar = new Car("Mazda", "CX-90", 2023,
                0, "Red", "Gas", 328, 7,
                "Independent", "Automatic", "Independent",
                8700000, false);
        Order orderWithUnavailableCar = new Order(2, alinaAlexandrova.getId(),
                List.of(unavailableCar), 0, OrderStatus.CREATED, new Date());

        when(orderRequestService.addOrder(orderWithUnavailableCar)).thenReturn(false);
        orderRequestController.addOrder(orderWithUnavailableCar);

        verify(orderRequestService).addOrder(orderWithUnavailableCar);
    }

    @Test
    void testOrderWithTwoCars() {
        Order orderWithTwoCars = new Order(2, 1, List.of(hyundaiSolaris, mazdaCX90), 0, OrderStatus.CREATED, new Date());
        orderWithTwoCars.calculateTotalPrice();

        double expectedTotalPrice = hyundaiSolaris.getPrice() + mazdaCX90.getPrice();

        assertThat(orderWithTwoCars.getTotalPrice()).isEqualTo(expectedTotalPrice);

        orderRequestController.addOrder(orderWithTwoCars);

        verify(orderRequestService).addOrder(orderWithTwoCars);
    }

    @Test
    void getOrdersByCustomerAlinaId() {
        when(orderRequestService.getOrdersByCustomerId(alinaAlexandrova.getId()))
                .thenReturn(List.of(new Order(1, alinaAlexandrova.getId(), List.of(hyundaiSolaris), 1025000, OrderStatus.CREATED, new Date())));

        orderRequestController.getOrdersByCustomerId(alinaAlexandrova.getId());

        verify(orderRequestService).getOrdersByCustomerId(alinaAlexandrova.getId());
    }

    @Test
    void getOrdersByCustomerAlinaIdIfNoOrders() {
        when(orderRequestService.getOrdersByCustomerId(alinaAlexandrova.getId()))
                .thenReturn(Collections.emptyList());

        orderRequestController.getOrdersByCustomerId(alinaAlexandrova.getId());

        verify(orderRequestService).getOrdersByCustomerId(alinaAlexandrova.getId());
    }
}