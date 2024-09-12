package aeterraes.carshopservice.dataaccess.entities;

import aeterraes.carshopservice.dataaccess.models.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "orders", schema = "entity")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private int orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerid", referencedColumnName = "customerid")
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carid", referencedColumnName = "carid")
    private Car car;
    @Column(name = "totalprice")
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    @Column(name = "orderstatus")
    private OrderStatus status;
    @Column(name = "orderdate")
    private Date orderDate;
}
