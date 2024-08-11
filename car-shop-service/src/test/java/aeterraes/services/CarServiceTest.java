package aeterraes.services;

import aeterraes.dataaccess.entities.Car;
import aeterraes.dataaccess.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("CarService Tests")
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all cars")
    void testGetAllCars() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carRepository.getAllCars()).thenReturn(cars);

        List<Car> result = carService.getAllCars();

        assertThat(result).hasSize(2);
        verify(carRepository, times(1)).getAllCars();
    }

    @Test
    @DisplayName("Should return car by ID")
    void testGetCarById() {
        Car car = new Car();
        when(carRepository.getCarById(anyInt())).thenReturn(car);

        Car result = carService.getCarById(1);

        assertThat(result).isNotNull();
        verify(carRepository, times(1)).getCarById(1);
    }

    @Test
    @DisplayName("Should return available cars")
    void testGetCarsByAvailability() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carRepository.getCarsByAvailability()).thenReturn(cars);

        List<Car> result = carService.getCarsByAvailability();

        assertThat(result).hasSize(2);
        verify(carRepository, times(1)).getCarsByAvailability();
    }

    @Test
    @DisplayName("Should add a new car")
    void testAddCar() {
        Car car = new Car();
        doNothing().when(carRepository).addCar(car);

        carService.addCar(car);

        verify(carRepository, times(1)).addCar(car);
    }

    @Test
    @DisplayName("Should update a car")
    void testUpdateCar() {
        Car car = new Car();
        doNothing().when(carRepository).updateCar(car);

        carService.updateCar(car);

        verify(carRepository, times(1)).updateCar(car);
    }

    @Test
    @DisplayName("Should delete a car by ID")
    void testDeleteCar() {
        doNothing().when(carRepository).deleteCar(anyInt());

        carService.deleteCar(1);

        verify(carRepository, times(1)).deleteCar(1);
    }

    @Test
    @DisplayName("Should return cars by make")
    void testGetCarsByMake() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carRepository.getCarsByMake(anyString())).thenReturn(cars);

        List<Car> result = carService.getCarsByMake("Toyota");

        assertThat(result).hasSize(2);
        verify(carRepository, times(1)).getCarsByMake("Toyota");
    }

    @Test
    @DisplayName("Should return cars by model")
    void testGetCarsByModel() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carRepository.getCarsByModel(anyString())).thenReturn(cars);

        List<Car> result = carService.getCarsByModel("Camry");

        assertThat(result).hasSize(2);
        verify(carRepository, times(1)).getCarsByModel("Camry");
    }

    @Test
    @DisplayName("Should return cars by year")
    void testGetCarsByYear() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carRepository.getCarsByYear(anyInt())).thenReturn(cars);

        List<Car> result = carService.getCarsByYear(2020);

        assertThat(result).hasSize(2);
        verify(carRepository, times(1)).getCarsByYear(2020);
    }

    @Test
    @DisplayName("Should return cars by mileage")
    void testGetCarsByMileage() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carRepository.getCarsByMileage(anyDouble())).thenReturn(cars);

        List<Car> result = carService.getCarsByMileage(15000);

        assertThat(result).hasSize(2);
        verify(carRepository, times(1)).getCarsByMileage(15000);
    }

    @Test
    @DisplayName("Should return cars by color")
    void testGetCarsByColor() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carRepository.getCarsByColor(anyString())).thenReturn(cars);

        List<Car> result = carService.getCarsByColor("Red");

        assertThat(result).hasSize(2);
        verify(carRepository, times(1)).getCarsByColor("Red");
    }

    @Test
    @DisplayName("Should return cars by engine")
    void testGetCarsByEngine() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carRepository.getCarsByEngine(anyString())).thenReturn(cars);

        List<Car> result = carService.getCarsByEngine("V6");

        assertThat(result).hasSize(2);
        verify(carRepository, times(1)).getCarsByEngine("V6");
    }

    @Test
    @DisplayName("Should return cars by horsepower")
    void testGetCarsByHorsepower() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carRepository.getCarsByHorsepower(anyInt())).thenReturn(cars);

        List<Car> result = carService.getCarsByHorsepower(300);

        assertThat(result).hasSize(2);
        verify(carRepository, times(1)).getCarsByHorsepower(300);
    }

    @Test
    @DisplayName("Should return cars by acceleration")
    void testGetCarsByAcceleration() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carRepository.getCarsByAcceleration(anyInt())).thenReturn(cars);

        List<Car> result = carService.getCarsByAcceleration(5);

        assertThat(result).hasSize(2);
        verify(carRepository, times(1)).getCarsByAcceleration(5);
    }

    @Test
    @DisplayName("Should return cars by drive train")
    void testGetCarsByDriveTrain() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carRepository.getCarsByDriveTrain(anyString())).thenReturn(cars);

        List<Car> result = carService.getCarsByDriveTrain("AWD");

        assertThat(result).hasSize(2);
        verify(carRepository, times(1)).getCarsByDriveTrain("AWD");
    }

    @Test
    @DisplayName("Should return cars by price range")
    void testGetCarsByPriceRange() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carRepository.getCarsByPriceRange(anyDouble(), anyDouble())).thenReturn(cars);

        List<Car> result = carService.getCarsByPriceRange(20000, 30000);

        assertThat(result).hasSize(2);
        verify(carRepository, times(1)).getCarsByPriceRange(20000, 30000);
    }

    @Test
    @DisplayName("Should change car availability")
    void testChangeAvailability() {
        doNothing().when(carRepository).changeAvailability(anyInt(), anyBoolean());

        carService.changeAvailability(1, true);

        verify(carRepository, times(1)).changeAvailability(1, true);
    }

    @Test
    @DisplayName("Should return last added car ID")
    void testGetLastAddedCarId() {
        when(carRepository.getLastAddedCarId()).thenReturn(1);

        int result = carService.getLastAddedCarId();

        assertThat(result).isEqualTo(1);
        verify(carRepository, times(1)).getLastAddedCarId();
    }
}
