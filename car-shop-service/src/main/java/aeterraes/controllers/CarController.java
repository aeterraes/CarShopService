package aeterraes.controllers;

import aeterraes.entities.Car;
import aeterraes.entities.User;
import aeterraes.logging.LoggerConfig;
import aeterraes.models.Role;
import aeterraes.security.AuthService;
import aeterraes.services.CarService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.logging.Logger;

@Getter
@Setter
public class CarController {

    private static final Logger logger = LoggerConfig.getLogger();
    private final CarService carService;
    private final AuthService authService;

    /**
     * @param carService  Service for managing cars
     * @param authService Service for authentication and authorization
     */
    public CarController(CarService carService, AuthService authService) {
        this.carService = carService;
        this.authService = authService;
    }

    /**
     * print a list of all cars
     */
    public void getAllCars() {
        logger.info("Get all cars");
        List<Car> cars = carService.getAllCars();
        System.out.println("All Cars:");
        cars.forEach(car -> System.out.println(car.toString()));
    }

    /**
     * Print information about a car by ID
     *
     * @param id The ID of the car
     */
    public void getCarById(int id) {
        logger.info("Get car by id: " + id);
        Car car = carService.getCarById(id);
        if (car != null) {
            System.out.println("Car Info by ID " + id + ":");
            System.out.println(car.toString());
        } else {
            System.out.println("Car with ID " + id + " not found.");
        }
    }

    /**
     * Add a new car
     *
     * @param id          The ID of the car
     * @param car         The car to add.
     * @param currentUser The current user who is doing the action
     */
    public void addCar(int id, Car car, User currentUser) {
        if (authService.hasAccess(currentUser, Role.ADMIN)) {
            logger.info("Add car: " + car.toString());
            carService.addCar(id, car);
            System.out.println("Car added successfully:");
            System.out.println(car.toString());
        } else {
            logger.info("Access denied.");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Update information about a car
     *
     * @param id          The ID of the car
     * @param car         The updated car
     * @param currentUser The current user who is doing the action
     */
    public void updateCar(int id, Car car, User currentUser) {
        if (authService.hasAccess(currentUser, Role.ADMIN)) {
            logger.info("Update car: " + car.toString());
            carService.updateCar(id, car);
            System.out.println("Car updated successfully with ID " + id + ":");
            System.out.println(car.toString());
        } else {
            logger.info("Access denied.");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Delete a car by ID
     *
     * @param id          The ID of the car
     * @param currentUser The current user who is doing the action
     */
    public void deleteCar(int id, User currentUser) {
        if (authService.hasAccess(currentUser, Role.ADMIN)) {
            logger.info("Delete car: " + id);
            carService.deleteCar(id);
            System.out.println("Car with ID " + id + " deleted successfully.");
        } else {
            logger.info("Access denied.");
            System.out.println("Access Denied: Insufficient permissions");
        }
    }

    /**
     * Print cars by make
     *
     * @param make The make of the car
     */
    public void getCarsByMake(String make) {
        logger.info("Get cars by make: " + make);
        List<Car> cars = carService.getCarsByMake(make);
        System.out.println("Cars with Make " + make + ":");
        cars.forEach(car -> System.out.println(car.toString()));
    }

    /**
     * Print cars by model
     *
     * @param model The model of the car
     */
    public void getCarsByModel(String model) {
        logger.info("Get cars by model: " + model);
        List<Car> cars = carService.getCarsByModel(model);
        System.out.println("Cars with Model " + model + ":");
        cars.forEach(car -> System.out.println(car.toString()));
    }

    /**
     * Print cars by year of manufacture
     *
     * @param year The year of manufacture
     */
    public void getCarsByYear(int year) {
        logger.info("Get cars by year: " + year);
        List<Car> cars = carService.getCarsByYear(year);
        System.out.println("Cars with Year " + year + ":");
        cars.forEach(car -> System.out.println(car.toString()));
    }

    /**
     * Print cars by mileage
     *
     * @param mileage The mileage of the car
     */
    public void getCarsByMileage(double mileage) {
        logger.info("Get cars by mileage: " + mileage);
        List<Car> cars = carService.getCarsByMileage(mileage);
        System.out.println("Cars with Mileage " + mileage + ":");
        cars.forEach(car -> System.out.println(car.toString()));
    }

    /**
     * Print cars by color
     *
     * @param color The color of the car
     */
    public void getCarsByColor(String color) {
        logger.info("Get cars by color: " + color);
        List<Car> cars = carService.getCarsByColor(color);
        System.out.println("Cars with Color " + color + ":");
        cars.forEach(car -> System.out.println(car.toString()));
    }

    /**
     * Print  cars by engine type
     *
     * @param engine The engine type
     */
    public void getCarsByEngine(String engine) {
        logger.info("Get cars by engine: " + engine);
        List<Car> cars = carService.getCarsByEngine(engine);
        System.out.println("Cars with Engine " + engine + ":");
        cars.forEach(car -> System.out.println(car.toString()));
    }

    /**
     * Print  cars by horsepower.
     *
     * @param horsepower The horsepower of the car
     */
    public void getCarsByHorsepower(int horsepower) {
        logger.info("Get cars by horsepower: " + horsepower);
        List<Car> cars = carService.getCarsByHorsepower(horsepower);
        System.out.println("Cars with Horsepower " + horsepower + ":");
        cars.forEach(car -> System.out.println(car.toString()));
    }

    /**
     * Print cars by acceleration
     *
     * @param acceleration The acceleration of the car
     */
    public void getCarsByAcceleration(int acceleration) {
        logger.info("Get cars by acceleration: " + acceleration);
        List<Car> cars = carService.getCarsByAcceleration(acceleration);
        System.out.println("Cars with Acceleration " + acceleration + ":");
        cars.forEach(car -> System.out.println(car.toString()));
    }

    /**
     * Print cars by drive train type
     *
     * @param driveTrain The drive train type
     */
    public void getCarsByDriveTrain(String driveTrain) {
        logger.info("Get cars by drive train: " + driveTrain);
        List<Car> cars = carService.getCarsByDriveTrain(driveTrain);
        System.out.println("Cars with Drive Train " + driveTrain + ":");
        cars.forEach(car -> System.out.println(car.toString()));
    }

    /**
     * Print cars in a given range of prices
     *
     * @param minPrice The minimum price
     * @param maxPrice The maximum price
     */
    public void getCarsByPriceRange(double minPrice, double maxPrice) {
        logger.info("Get cars by price range: " + minPrice + " - " + maxPrice);
        List<Car> cars = carService.getCarsByPriceRange(minPrice, maxPrice);
        System.out.println("Cars with Price Range " + minPrice + " to " + maxPrice + ":");
        cars.forEach(car -> System.out.println(car.toString()));
    }
}

