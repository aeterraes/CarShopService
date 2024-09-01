package aeterraes.carshopservice.service;

import aeterraes.carshopservice.dataaccess.entities.Car;
import aeterraes.carshopservice.dataaccess.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(int id) {
        return carRepository.findById(id).orElse(null);
    }

    public List<Car> getCarsByAvailability() {
        return carRepository.findByAvailability(true);
    }

    public void addCar(Car car) {
        carRepository.save(car);
    }

    public void updateCar(Car car) {
        carRepository.save(car);
    }

    public void deleteCar(int id) {
        carRepository.deleteById(id);
    }

    public List<Car> getCarsByMake(String make) {
        return carRepository.findByMake(make);
    }

    public List<Car> getCarsByModel(String model) {
        return carRepository.findByModel(model);
    }

    public List<Car> getCarsByYear(int year) {
        return carRepository.findByYear(year);
    }

    public List<Car> getCarsByMileage(double mileage) {
        return carRepository.findByMileage(mileage);
    }

    public List<Car> getCarsByColor(String color) {
        return carRepository.findByColor(color);
    }

    public List<Car> getCarsByEngine(String engine) {
        return carRepository.findByEngine(engine);
    }

    public List<Car> getCarsByHorsepower(int horsepower) {
        return carRepository.findByHorsepower(horsepower);
    }

    public List<Car> getCarsByAcceleration(int acceleration) {
        return carRepository.findByAcceleration(acceleration);
    }

    public List<Car> getCarsByDriveTrain(String driveTrain) {
        return carRepository.findByDriveTrain(driveTrain);
    }

    public List<Car> getCarsByPriceRange(double minPrice, double maxPrice) {
        return carRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public void changeAvailability(int id, boolean availability) {
        Car car = carRepository.findById(id).orElse(null);
        if (car != null) {
            car.setAvailability(availability);
            carRepository.save(car);
        }
    }

    public List<Car> getCarsBySuspension(String suspension) {
        return carRepository.findBySuspension(suspension);
    }

    public List<Car> getCarsByGear(String gear) {
        return carRepository.findByGear(gear);
    }
}
