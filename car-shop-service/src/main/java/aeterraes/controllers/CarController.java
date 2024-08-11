package aeterraes.controllers;

import aeterraes.dataaccess.entities.Car;
import aeterraes.utils.logging.LoggerConfig;
import aeterraes.services.CarService;

import java.util.List;
import java.util.logging.Logger;

public class CarController {

    private final CarService carService;
    private static final Logger logger = LoggerConfig.getLogger();

    public CarController(CarService carService) {
        this.carService = carService;
    }

    public void getAllCars() {
        logger.info("Fetching all cars");
        List<Car> cars = carService.getAllCars();
        cars.forEach(System.out::println);
        logger.info("Fetched " + cars.size() + " cars");
    }

    public void getCarById(int id) {
        logger.info("Fetching car with ID: " + id);
        Car car = carService.getCarById(id);
        if (car != null) {
            System.out.println(car);
            logger.info("Fetched car: " + car);
        } else {
            logger.warning("Car with ID " + id + " not found");
        }
    }

    public void addCar(Car car) {
        logger.info("Adding new car: " + car);
        carService.addCar(car);
        int newCarId = carService.getLastAddedCarId();
        logger.info("Car added with ID: " + newCarId);
    }

    public void updateCar(Car car) {
        logger.info("Updating car: " + car);
        carService.updateCar(car);
        logger.info("Car updated: " + car);
    }

    public void deleteCar(int id) {
        logger.info("Deleting car with ID: " + id);
        carService.deleteCar(id);
        logger.info("Car with ID " + id + " deleted");
    }

    public void getCarsByAvailability() {
        logger.info("Fetching available cars");
        List<Car> cars = carService.getCarsByAvailability();
        cars.forEach(System.out::println);
        logger.info("Fetched " + cars.size() + " available cars");
    }

    public void getCarsByMake(String make) {
        logger.info("Fetching cars by make: " + make);
        List<Car> cars = carService.getCarsByMake(make);
        cars.forEach(System.out::println);
        logger.info("Fetched " + cars.size() + " cars of make: " + make);
    }

    public void getCarsByModel(String model) {
        logger.info("Fetching cars by model: " + model);
        List<Car> cars = carService.getCarsByModel(model);
        cars.forEach(System.out::println);
        logger.info("Fetched " + cars.size() + " cars of model: " + model);
    }

    public void getCarsByYear(int year) {
        logger.info("Fetching cars by year: " + year);
        List<Car> cars = carService.getCarsByYear(year);
        cars.forEach(System.out::println);
        logger.info("Fetched " + cars.size() + " cars for year: " + year);
    }

    public void getCarsByMileage(double mileage) {
        logger.info("Fetching cars by mileage: " + mileage);
        List<Car> cars = carService.getCarsByMileage(mileage);
        cars.forEach(System.out::println);
        logger.info("Fetched " + cars.size() + " cars for mileage: " + mileage);
    }

    public void getCarsByColor(String color) {
        logger.info("Fetching cars by color: " + color);
        List<Car> cars = carService.getCarsByColor(color);
        cars.forEach(System.out::println);
        logger.info("Fetched " + cars.size() + " cars for color: " + color);
    }

    public void getCarsByEngine(String engine) {
        logger.info("Fetching cars by engine: " + engine);
        List<Car> cars = carService.getCarsByEngine(engine);
        cars.forEach(System.out::println);
        logger.info("Fetched " + cars.size() + " cars for engine: " + engine);
    }

    public void getCarsByHorsepower(int horsepower) {
        logger.info("Fetching cars by horsepower: " + horsepower);
        List<Car> cars = carService.getCarsByHorsepower(horsepower);
        cars.forEach(System.out::println);
        logger.info("Fetched " + cars.size() + " cars for horsepower: " + horsepower);
    }

    public void getCarsByAcceleration(int acceleration) {
        logger.info("Fetching cars by acceleration: " + acceleration);
        List<Car> cars = carService.getCarsByAcceleration(acceleration);
        cars.forEach(System.out::println);
        logger.info("Fetched " + cars.size() + " cars for acceleration: " + acceleration);
    }

    public void getCarsByDriveTrain(String driveTrain) {
        logger.info("Fetching cars by driveTrain: " + driveTrain);
        List<Car> cars = carService.getCarsByDriveTrain(driveTrain);
        cars.forEach(System.out::println);
        logger.info("Fetched " + cars.size() + " cars for driveTrain: " + driveTrain);
    }

    public void getCarsByPriceRange(double minPrice, double maxPrice) {
        logger.info("Fetching cars with price between " + minPrice + " and " + maxPrice);
        List<Car> cars = carService.getCarsByPriceRange(minPrice, maxPrice);
        cars.forEach(System.out::println);
        logger.info("Fetched " + cars.size() + " cars in the price range");
    }

    public void changeCarAvailability(int id, boolean availability) {
        logger.info("Changing availability of car with ID: " + id + " to " + availability);
        carService.changeAvailability(id, availability);
        logger.info("Car availability updated: ID " + id + ", Available: " + availability);
    }
}
