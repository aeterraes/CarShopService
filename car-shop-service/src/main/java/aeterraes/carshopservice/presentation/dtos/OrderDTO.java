package aeterraes.carshopservice.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {
    private int orderId;
    private int customerId;
    private int carId;
    private double totalPrice;
    private String status;
    private Date orderDate;
}

