package aeterraes.controllers;

import aeterraes.dataaccess.entities.Order;
import aeterraes.dataaccess.models.OrderStatus;
import aeterraes.dtos.OrderDTO;
import aeterraes.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @Autowired
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDTO> orderDTOs = orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") int id) {
        Order order = orderService.getOrderById(id);
        OrderDTO orderDTO = this.toDto(order);
        return ResponseEntity.ok(orderDTO);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> addOrder(@RequestBody OrderDTO orderDTO) {
        Order order = this.toEntity(orderDTO);
        orderService.addOrder(order);
        OrderDTO createdOrderDTO = this.toDto(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable("id") int id) {
        Order existingOrder = orderService.getOrderById(id);
        orderService.updateOrder(existingOrder);
        OrderDTO updatedOrderDTO = this.toDto(existingOrder);
        return ResponseEntity.ok(updatedOrderDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") int id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@RequestParam("status") OrderStatus status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        List<OrderDTO> orderDTOs = orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<OrderDTO>> getOrdersByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        List<Order> orders = orderService.getOrdersByDateRange(startDate, endDate);
        List<OrderDTO> orderDTOs = orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<OrderDTO>> getOrdersByPriceRange(
            @RequestParam("minPrice") double minPrice,
            @RequestParam("maxPrice") double maxPrice) {
        List<Order> orders = orderService.getOrdersByPriceRange(minPrice, maxPrice);
        List<OrderDTO> orderDTOs = orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable("id") int id, @RequestParam("status") OrderStatus status) {
        orderService.updateOrderStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<OrderDTO> getOrderByCustomerId(@PathVariable("id") int id) {
        Order order = orderService.getOrderByCustomerId(id);
        OrderDTO orderDTO = this.toDto(order);
        return ResponseEntity.ok(orderDTO);
    }

    private Order toEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }

    private OrderDTO toDto(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
}

