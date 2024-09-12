package aeterraes.carshopservice.dataaccess.repositories;

import aeterraes.carshopservice.dataaccess.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findByAvailability(boolean availability);

    List<Car> findByMake(String make);

    List<Car> findByModel(String model);

    List<Car> findByYear(int year);

    List<Car> findByMileage(double mileage);

    List<Car> findByColor(String color);

    List<Car> findByEngine(String engine);

    List<Car> findByHorsepower(int horsepower);

    List<Car> findByAcceleration(int acceleration);

    List<Car> findByDriveTrain(String driveTrain);

    List<Car> findByPriceBetween(double minPrice, double maxPrice);

    List<Car> findBySuspension(String suspension);

    List<Car> findByGear(String gear);
}
