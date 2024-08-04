package aeterraes.services;

import aeterraes.entities.Car;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class CarService {

    private final Map<Integer, Car> carStore = new HashMap<>();

    /**
     * Retrieve a list of all cars
     *
     * @return List of all cars
     */
    public List<Car> getAllCars() {
        return new ArrayList<>(carStore.values());
    }

    /**
     * Retrieve a car by its ID
     *
     * @param id The ID of the car
     * @return The car with the specified ID, or null if not found
     */
    public Car getCarById(int id) {
        return carStore.get(id);
    }

    /**
     * Add a new car to the store
     *
     * @param id  The ID of the car
     * @param car The car to add
     */
    public void addCar(int id, Car car) {
        carStore.put(id, car);
    }

    /**
     * Update an existing car in the store
     *
     * @param id  The ID of the car
     * @param car The updated car information
     */
    public void updateCar(int id, Car car) {
        carStore.put(id, car);
    }

    /**
     * Delete a car from the store by its ID
     *
     * @param id The ID of the car to delete
     */
    public void deleteCar(int id) {
        carStore.remove(id);
    }

    /**
     * Retrieve a list of cars by their make
     *
     * @param make The make of the cars
     * @return List of cars with the specified make
     */
    public List<Car> getCarsByMake(String make) {
        return carStore.values().stream()
                .filter(car -> car.getMake().equalsIgnoreCase(make))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of cars by their model
     *
     * @param model The model of the cars
     * @return List of cars with the specified model
     */
    public List<Car> getCarsByModel(String model) {
        return carStore.values().stream()
                .filter(car -> car.getModel().equalsIgnoreCase(model))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of cars by their manufacturing year
     *
     * @param year The year of the cars
     * @return List of cars from the specified year
     */
    public List<Car> getCarsByYear(int year) {
        return carStore.values().stream()
                .filter(car -> car.getYear() == year)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of cars by their mileage
     *
     * @param mileage The mileage of the cars
     * @return List of cars with the specified mileage
     */
    public List<Car> getCarsByMileage(double mileage) {
        return carStore.values().stream()
                .filter(car -> car.getMileage() == mileage)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of cars by their color
     *
     * @param color The color of the cars
     * @return List of cars with the specified color
     */
    public List<Car> getCarsByColor(String color) {
        return carStore.values().stream()
                .filter(car -> car.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of cars by their engine type
     *
     * @param engine The engine type of the cars
     * @return List of cars with the specified engine type
     */
    public List<Car> getCarsByEngine(String engine) {
        return carStore.values().stream()
                .filter(car -> car.getEngine().equalsIgnoreCase(engine))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of cars by their horsepower
     *
     * @param horsepower The horsepower of the cars
     * @return List of cars with the specified horsepower
     */
    public List<Car> getCarsByHorsepower(int horsepower) {
        return carStore.values().stream()
                .filter(car -> car.getHorsepower() == horsepower)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of cars by their acceleration
     *
     * @param acceleration The acceleration of the cars
     * @return List of cars with the specified acceleration
     */
    public List<Car> getCarsByAcceleration(int acceleration) {
        return carStore.values().stream()
                .filter(car -> car.getAcceleration() == acceleration)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of cars by their drive train type
     *
     * @param driveTrain The drive train type of the cars
     * @return List of cars with the specified drive train type
     */
    public List<Car> getCarsByDriveTrain(String driveTrain) {
        return carStore.values().stream()
                .filter(car -> car.getDriveTrain().equalsIgnoreCase(driveTrain))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a list of cars within a specified price range
     *
     * @param minPrice The minimum price
     * @param maxPrice The maximum price
     * @return List of cars within the specified price range
     */
    public List<Car> getCarsByPriceRange(double minPrice, double maxPrice) {
        return carStore.values().stream()
                .filter(car -> car.getPrice() >= minPrice && car.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
}
