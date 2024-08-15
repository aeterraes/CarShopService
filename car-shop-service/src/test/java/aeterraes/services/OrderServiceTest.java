package aeterraes.services;

import aeterraes.dataaccess.entities.Order;
import aeterraes.dataaccess.models.OrderStatus;
import aeterraes.dataaccess.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = Mockito.mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    @Test
    @DisplayName("Should return all orders")
    void testGetAllOrders() {
        Order order1 = new Order(1, 1, 1, 1000000.0, OrderStatus.CREATED, new Date(System.currentTimeMillis()));
        Order order2 = new Order(2, 2, 2, 2000000.0, OrderStatus.PAID, new Date(System.currentTimeMillis()));

        when(orderRepository.getAllOrders()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.getAllOrders();

        assertThat(orders).hasSize(2).contains(order1, order2);
        verify(orderRepository, times(1)).getAllOrders();
    }

    @Test
    @DisplayName("Should return order by ID")
    void testGetOrderById() {
        Order order = new Order(1, 1, 1, 1000000.0, OrderStatus.CREATED, new Date(System.currentTimeMillis()));

        when(orderRepository.getOrderById(1)).thenReturn(order);

        Order foundOrder = orderService.getOrderById(1);

        assertThat(foundOrder).isEqualTo(order);
        verify(orderRepository, times(1)).getOrderById(1);
    }

    @Test
    @DisplayName("Should add a new order")
    void testAddOrder() {
        Order order = new Order(1, 1, 1, 1000000.0, OrderStatus.CREATED, new Date(System.currentTimeMillis()));

        doNothing().when(orderRepository).addOrder(order);

        orderService.addOrder(order);

        verify(orderRepository, times(1)).addOrder(order);
    }

    @Test
    @DisplayName("Should update an existing order")
    void testUpdateOrder() {
        Order order = new Order(1, 1, 1, 1000000.0, OrderStatus.CREATED, new Date(System.currentTimeMillis()));

        doNothing().when(orderRepository).updateOrder(order);

        orderService.updateOrder(order);

        verify(orderRepository, times(1)).updateOrder(order);
    }

    @Test
    @DisplayName("Should delete an order by ID")
    void testDeleteOrder() {
        doNothing().when(orderRepository).deleteOrder(1);

        orderService.deleteOrder(1);

        verify(orderRepository, times(1)).deleteOrder(1);
    }

    @Test
    @DisplayName("Should return orders by status")
    void testGetOrdersByStatus() {
        Order order1 = new Order(1, 1, 1, 1000000.0, OrderStatus.CREATED, new Date(System.currentTimeMillis()));
        Order order2 = new Order(2, 2, 2, 2000000.0, OrderStatus.CREATED, new Date(System.currentTimeMillis()));

        when(orderRepository.getOrdersByStatus(OrderStatus.CREATED)).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.getOrdersByStatus(OrderStatus.CREATED);

        assertThat(orders).hasSize(2).contains(order1, order2);
        verify(orderRepository, times(1)).getOrdersByStatus(OrderStatus.CREATED);
    }

    @Test
    @DisplayName("Should return orders by date range")
    void testGetOrdersByDateRange() {
        Date startDate = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        Date endDate = new Date(System.currentTimeMillis());
        Order order1 = new Order(1, 1, 1, 1000000.0, OrderStatus.CREATED, startDate);
        Order order2 = new Order(2, 2, 2, 2000000.0, OrderStatus.PAID, endDate);

        when(orderRepository.getOrdersByDateRange(startDate, endDate)).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.getOrdersByDateRange(startDate, endDate);

        assertThat(orders).hasSize(2).contains(order1, order2);
        verify(orderRepository, times(1)).getOrdersByDateRange(startDate, endDate);
    }

    @Test
    @DisplayName("Should return orders by price range")
    void testGetOrdersByPriceRange() {
        Order order1 = new Order(1, 1, 1, 1000000.0, OrderStatus.CREATED, new Date(System.currentTimeMillis()));
        Order order2 = new Order(2, 2, 2, 2000000.0, OrderStatus.PAID, new Date(System.currentTimeMillis()));

        when(orderRepository.getOrdersByPriceRange(500000.0, 2500000.0)).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.getOrdersByPriceRange(500000.0, 2500000.0);

        assertThat(orders).hasSize(2).contains(order1, order2);
        verify(orderRepository, times(1)).getOrdersByPriceRange(500000.0, 2500000.0);
    }

    @Test
    @DisplayName("Should update order status")
    void testUpdateOrderStatus() {
        doNothing().when(orderRepository).updateOrderStatus(1, OrderStatus.PAID);

        orderService.updateOrderStatus(1, OrderStatus.PAID);

        verify(orderRepository, times(1)).updateOrderStatus(1, OrderStatus.PAID);
    }

    @Test
    @DisplayName("Should return order by customer ID")
    void testGetOrderByCustomerId() {
        Order order = new Order(1, 1, 1, 1000000.0, OrderStatus.CREATED, new Date(System.currentTimeMillis()));

        when(orderRepository.getOrderByCustomerId(1)).thenReturn(order);

        Order foundOrder = orderService.getOrderByCustomerId(1);

        assertThat(foundOrder).isEqualTo(order);
        verify(orderRepository, times(1)).getOrderByCustomerId(1);
    }
}
