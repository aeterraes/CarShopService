package aeterraes.services;

import aeterraes.dataaccess.entities.Car;
import aeterraes.dataaccess.repositories.CarRepository;

import java.util.List;

public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.getAllCars();
    }

    public Car getCarById(int id) {
        return carRepository.getCarById(id);
    }

    public List<Car> getCarsByAvailability() {
        return carRepository.getCarsByAvailability();
    }

    public void addCar(Car car) {
        carRepository.addCar(car);
    }

    public void updateCar(Car car) {
        carRepository.updateCar(car);
    }

    public void deleteCar(int id) {
        carRepository.deleteCar(id);
    }

    public List<Car> getCarsByMake(String make) {
        return carRepository.getCarsByMake(make);
    }

    public List<Car> getCarsByModel(String model) {
        return carRepository.getCarsByModel(model);
    }

    public List<Car> getCarsByYear(int year) {
        return carRepository.getCarsByYear(year);
    }

    public List<Car> getCarsByMileage(double mileage) {
        return carRepository.getCarsByMileage(mileage);
    }

    public List<Car> getCarsByColor(String color) {
        return carRepository.getCarsByColor(color);
    }

    public List<Car> getCarsByEngine(String engine) {
        return carRepository.getCarsByEngine(engine);
    }

    public List<Car> getCarsByHorsepower(int horsepower) {
        return carRepository.getCarsByHorsepower(horsepower);
    }

    public List<Car> getCarsByAcceleration(int acceleration) {
        return carRepository.getCarsByAcceleration(acceleration);
    }

    public List<Car> getCarsByDriveTrain(String driveTrain) {
        return carRepository.getCarsByDriveTrain(driveTrain);
    }

    public List<Car> getCarsByPriceRange(double minPrice, double maxPrice) {
        return carRepository.getCarsByPriceRange(minPrice, maxPrice);
    }

    public void changeAvailability(int id, boolean availability) {
        carRepository.changeAvailability(id, availability);
    }

    public int getLastAddedCarId() {
        return carRepository.getLastAddedCarId();
    }
}
