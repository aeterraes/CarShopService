package aeterraes.carshopservice.dataaccess.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "cars", schema = "entity")
public class Car {
    @Id
    @GeneratedValue
    @Column(name = "carid")
    private int carId;
    @Column(name = "make")
    private String make;
    @Column(name = "model")
    private String model;
    @Column(name = "year")
    private int year;
    @Column(name = "mileage")
    private double mileage;
    @Column(name = "color")
    private String color;
    @Column(name = "engine")
    private String engine;
    @Column(name = "horsepower")
    private int horsepower;
    @Column(name = "acceleration")
    private int acceleration;
    @Column(name = "suspension")
    private String suspension;
    @Column(name = "gear")
    private String gear;
    @Column(name = "drivetrain")
    private String driveTrain;
    @Column(name = "price")
    private double price;
    @Column(name = "availability")
    private boolean availability;
}
