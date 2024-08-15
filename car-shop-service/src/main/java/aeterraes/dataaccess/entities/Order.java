package aeterraes.dataaccess.entities;

import aeterraes.dataaccess.models.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int orderId;
    private int customerId;
    private int carId;
    private double totalPrice;
    private OrderStatus status;
    private Date orderDate;
}
