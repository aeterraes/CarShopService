package aeterraes.services;

import aeterraes.aop.annotation.Audit;
import aeterraes.dataaccess.entities.Car;
import aeterraes.dataaccess.repositories.CarRepository;

import java.util.List;

public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    @Audit
    public List<Car> getAllCars() {
        return carRepository.getAllCars();
    }
    @Audit
    public Car getCarById(int id) {
        return carRepository.getCarById(id);
    }
    @Audit
    public List<Car> getCarsByAvailability() {
        return carRepository.getCarsByAvailability();
    }
    @Audit
    public void addCar(Car car) {
        carRepository.addCar(car);
    }
    @Audit
    public void updateCar(Car car) {
        carRepository.updateCar(car);
    }
    @Audit
    public void deleteCar(int id) {
        carRepository.deleteCar(id);
    }
    @Audit
    public List<Car> getCarsByMake(String make) {
        return carRepository.getCarsByMake(make);
    }
    @Audit
    public List<Car> getCarsByModel(String model) {
        return carRepository.getCarsByModel(model);
    }
    @Audit
    public List<Car> getCarsByYear(int year) {
        return carRepository.getCarsByYear(year);
    }
    @Audit
    public List<Car> getCarsByMileage(double mileage) {
        return carRepository.getCarsByMileage(mileage);
    }
    @Audit
    public List<Car> getCarsByColor(String color) {
        return carRepository.getCarsByColor(color);
    }
    @Audit
    public List<Car> getCarsByEngine(String engine) {
        return carRepository.getCarsByEngine(engine);
    }
    @Audit
    public List<Car> getCarsByHorsepower(int horsepower) {
        return carRepository.getCarsByHorsepower(horsepower);
    }
    @Audit
    public List<Car> getCarsByAcceleration(int acceleration) {return carRepository.getCarsByAcceleration(acceleration);}
    @Audit
    public List<Car> getCarsByDriveTrain(String driveTrain) {
        return carRepository.getCarsByDriveTrain(driveTrain);
    }
    @Audit
    public List<Car> getCarsByPriceRange(double minPrice, double maxPrice) {return carRepository.getCarsByPriceRange(minPrice, maxPrice);}
    @Audit
    public void changeAvailability(int id, boolean availability) {
        carRepository.changeAvailability(id, availability);
    }
    @Audit
    public int getLastAddedCarId() {
        return carRepository.getLastAddedCarId();
    }
    @Audit
    public List<Car> getCarsBySuspension(String suspension) {return carRepository.getCarsBySuspension(suspension);}
    @Audit
    public List<Car> getCarsByGear(String gear) {return carRepository.getCarsByGear(gear);}
    @Audit
    public List<Car> getCarsByAvailability(boolean availability) {return carRepository.getCarsByAvailability(availability);}
}
