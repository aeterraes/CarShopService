package aeterraes.carshopservice.presentation.controllers;

import aeterraes.carshopservice.dataaccess.entities.Car;
import aeterraes.carshopservice.dataaccess.entities.Customer;
import aeterraes.carshopservice.dataaccess.entities.Order;
import aeterraes.carshopservice.dataaccess.models.OrderStatus;
import aeterraes.carshopservice.presentation.dtos.OrderDTO;
import aeterraes.carshopservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllOrders() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(1);

        Car car = new Car();
        car.setCarId(1);

        Order order = new Order(1, customer, car, 1000000, OrderStatus.CREATED, Date.valueOf("2024-08-20"));
        OrderDTO orderDTO = new OrderDTO(1, 1, 1, 1000000, "CREATED", Date.valueOf("2024-08-20"));

        List<Order> orders = List.of(order);

        when(orderService.getAllOrders()).thenReturn(orders);
        when(modelMapper.map(any(Order.class), any())).thenReturn(orderDTO);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(1))
                .andExpect(jsonPath("$[0].totalPrice").value(1000000));
    }

    @Test
    public void testGetOrderById() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(1);

        Car car = new Car();
        car.setCarId(1);

        Order order = new Order(1, customer, car, 1000000, OrderStatus.CREATED, Date.valueOf("2024-08-20"));
        OrderDTO orderDTO = new OrderDTO(1, 1, 1, 1000000, "CREATED", Date.valueOf("2024-08-20"));

        when(orderService.getOrderById(1)).thenReturn(order);
        when(modelMapper.map(any(Order.class), any())).thenReturn(orderDTO);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.totalPrice").value(1000000));
    }

    @Test
    public void testAddOrder() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(1);

        Car car = new Car();
        car.setCarId(1);

        Order order = new Order(1, customer, car, 1000000, OrderStatus.CREATED, Date.valueOf("2024-08-20"));
        OrderDTO orderDTO = new OrderDTO(1, 1, 1, 1000000, "CREATED", Date.valueOf("2024-08-20"));

        when(modelMapper.map(any(OrderDTO.class), any())).thenReturn(order);
        when(modelMapper.map(any(Order.class), any())).thenReturn(orderDTO);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.totalPrice").value(1000000));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(1);

        Car car = new Car();
        car.setCarId(1);

        Order order = new Order(1, customer, car, 1000000, OrderStatus.CREATED, Date.valueOf("2024-08-20"));
        OrderDTO orderDTO = new OrderDTO(1, 1, 1, 1000000, "CREATED", Date.valueOf("2024-08-20"));

        when(orderService.getOrderById(1)).thenReturn(order);
        when(modelMapper.map(any(Order.class), any())).thenReturn(orderDTO);

        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.totalPrice").value(1000000));
    }

    @Test
    public void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetOrdersByStatus() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(1);

        Car car = new Car();
        car.setCarId(1);

        Order order = new Order(1, customer, car, 1000000, OrderStatus.CREATED, Date.valueOf("2024-08-20"));
        OrderDTO orderDTO = new OrderDTO(1, 1, 1, 1000000, "CREATED", Date.valueOf("2024-08-20"));

        List<Order> orders = List.of(order);

        when(orderService.getOrdersByStatus(OrderStatus.CREATED)).thenReturn(orders);
        when(modelMapper.map(any(Order.class), any())).thenReturn(orderDTO);

        mockMvc.perform(get("/orders/status")
                        .param("status", "CREATED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(1))
                .andExpect(jsonPath("$[0].totalPrice").value(1000000));
    }

    @Test
    public void testGetOrdersByPriceRange() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(1);

        Car car = new Car();
        car.setCarId(1);

        Order order = new Order(1, customer, car, 1000000, OrderStatus.CREATED, Date.valueOf("2024-08-20"));
        OrderDTO orderDTO = new OrderDTO(1, 1, 1, 1000000, "CREATED", Date.valueOf("2024-08-20"));

        List<Order> orders = List.of(order);

        when(orderService.getOrdersByPriceRange(900000, 1100000)).thenReturn(orders);
        when(modelMapper.map(any(Order.class), any())).thenReturn(orderDTO);

        mockMvc.perform(get("/orders/price-range")
                        .param("minPrice", "900000")
                        .param("maxPrice", "1100000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(1))
                .andExpect(jsonPath("$[0].totalPrice").value(1000000));
    }

    @Test
    public void testUpdateOrderStatus() throws Exception {
        mockMvc.perform(patch("/orders/1/status")
                        .param("status", String.valueOf(OrderStatus.DELIVERED)))
                .andExpect(status().isNoContent());
    }
}