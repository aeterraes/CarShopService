package aeterraes.carshopservice.presentation.controllers;

import aeterraes.carshopservice.dataaccess.entities.Car;
import aeterraes.carshopservice.presentation.dtos.CarDTO;
import aeterraes.carshopservice.service.CarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private final CarService carService;

    @Autowired
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        List<CarDTO> carDTOs = cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable("id") int id) {
        Car car = carService.getCarById(id);
        CarDTO carDTO = this.toDto(car);
        return ResponseEntity.ok(carDTO);
    }

    @GetMapping("/available")
    public ResponseEntity<List<CarDTO>> getCarsByAvailability() {
        List<Car> cars = carService.getCarsByAvailability();
        List<CarDTO> carDTOs = cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }

    @PostMapping
    public ResponseEntity<CarDTO> addCar(@RequestBody CarDTO carDTO) {
        Car car = this.toEntity(carDTO);
        carService.addCar(car);
        CarDTO createdCarDTO = this.toDto(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCarDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable("id") int id) {
        Car existingCar = carService.getCarById(id);
        carService.updateCar(existingCar);
        CarDTO updatedCarDTO = this.toDto(existingCar);
        return ResponseEntity.ok(updatedCarDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable("id") int id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/make/{make}")
    public ResponseEntity<List<CarDTO>> getCarsByMake(@PathVariable("make") String make) {
        List<Car> cars = carService.getCarsByMake(make);
        List<CarDTO> carDTOs = cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }

    @GetMapping("/model/{model}")
    public ResponseEntity<List<CarDTO>> getCarsByModel(@PathVariable("model") String model) {
        List<Car> cars = carService.getCarsByModel(model);
        List<CarDTO> carDTOs = cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<CarDTO>> getCarsByYear(@PathVariable("year") int year) {
        List<Car> cars = carService.getCarsByYear(year);
        List<CarDTO> carDTOs = cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }

    @GetMapping("/mileage/{mileage}")
    public ResponseEntity<List<CarDTO>> getCarsByMileage(@PathVariable("mileage") double mileage) {
        List<Car> cars = carService.getCarsByMileage(mileage);
        List<CarDTO> carDTOs = cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<CarDTO>> getCarsByColor(@PathVariable("color") String color) {
        List<Car> cars = carService.getCarsByColor(color);
        List<CarDTO> carDTOs = cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }

    @GetMapping("/engine/{engine}")
    public ResponseEntity<List<CarDTO>> getCarsByEngine(@PathVariable("engine") String engine) {
        List<Car> cars = carService.getCarsByEngine(engine);
        List<CarDTO> carDTOs = cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }

    @GetMapping("/horsepower/{horsepower}")
    public ResponseEntity<List<CarDTO>> getCarsByHorsepower(@PathVariable("horsepower") int horsepower) {
        List<Car> cars = carService.getCarsByHorsepower(horsepower);
        List<CarDTO> carDTOs = cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }

    @GetMapping("/acceleration/{acceleration}")
    public ResponseEntity<List<CarDTO>> getCarsByAcceleration(@PathVariable("acceleration") int acceleration) {
        List<Car> cars = carService.getCarsByAcceleration(acceleration);
        List<CarDTO> carDTOs = cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }

    @GetMapping("/drivetrain/{driveTrain}")
    public ResponseEntity<List<CarDTO>> getCarsByDriveTrain(@PathVariable("driveTrain") String driveTrain) {
        List<Car> cars = carService.getCarsByDriveTrain(driveTrain);
        List<CarDTO> carDTOs = cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<CarDTO>> getCarsByPriceRange(@RequestParam("minPrice") double minPrice, @RequestParam("maxPrice") double maxPrice) {
        List<Car> cars = carService.getCarsByPriceRange(minPrice, maxPrice);
        List<CarDTO> carDTOs = cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<Void> changeAvailability(@PathVariable("id") int id, @RequestParam("availability") boolean availability) {
        carService.changeAvailability(id, availability);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/suspension/{suspension}")
    public ResponseEntity<List<CarDTO>> getCarsBySuspension(@PathVariable("suspension") String suspension) {
        List<Car> cars = carService.getCarsBySuspension(suspension);
        List<CarDTO> carDTOs = cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }

    @GetMapping("/gear/{gear}")
    public ResponseEntity<List<CarDTO>> getCarsByGear(@PathVariable("gear") String gear) {
        List<Car> cars = carService.getCarsByGear(gear);
        List<CarDTO> carDTOs = cars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }

    private CarDTO toDto(Car car) {
        return modelMapper.map(car, CarDTO.class);
    }

    private Car toEntity(CarDTO carDTO) {
        return modelMapper.map(carDTO, Car.class);
    }
}
