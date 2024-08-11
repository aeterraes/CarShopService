package aeterraes.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private int carId;
    private String make;
    private String model;
    private int year;
    private double mileage;
    private String color;
    private String engine;
    private int horsepower;
    private int acceleration;
    private String suspension;
    private String gear;
    private String driveTrain;
    private double price;
    private boolean availability;
}
