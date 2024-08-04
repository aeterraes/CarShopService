package aeterraes.entities;

import aeterraes.models.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private int id;
    private int customerId;
    private List<Car> cars;
    private double totalPrice;
    private OrderStatus status;
    private Date orderDate;

    public void calculateTotalPrice() {
        this.totalPrice = cars.stream()
                .mapToDouble(Car::getPrice)
                .sum();
    }
}
