package aeterraes.controllers;

import aeterraes.entities.Car;
import aeterraes.entities.Customer;
import aeterraes.entities.Employee;
import aeterraes.models.Role;
import aeterraes.security.AuthService;
import aeterraes.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class CarControllerTest {

    @Mock
    private CarService carService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private CarController carController;

    private Employee maksimPlotnikov;
    private Customer alinaAlexandrova;
    private Car hyundaiSolaris;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        maksimPlotnikov = new Employee(2, "Maksim", "Plotnikov",
                "maxplotnikov2000@somemailservice.com", "qwertyQ", Role.MANAGER);
        alinaAlexandrova = new Customer(4, "Alina", "Alexandrova",
                "alinochkasolnyshko1998@somemailservice.com", "passwordpassword",
                Role.USER, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

        hyundaiSolaris = new Car("Hyundai", "Solaris",
                2018, 10000, "Blue", "Gas",
                100, 12, "Independent",
                "Mechanic", "Backward", 1025000, true);
    }

    @Test
    void testGetCarById() {
        when(carService.getCarById(1)).thenReturn(hyundaiSolaris);

        carController.getCarById(1);

        verify(carService, times(1)).getCarById(1);
    }

    @Test
    void testAddCarWithAdminAccess() {
        when(authService.hasAccess(maksimPlotnikov, Role.ADMIN)).thenReturn(true);
        doNothing().when(carService).addCar(anyInt(), any(Car.class));

        carController.addCar(1, hyundaiSolaris, maksimPlotnikov);

        verify(authService, times(1)).hasAccess(maksimPlotnikov, Role.ADMIN);
        verify(carService, times(1)).addCar(1, hyundaiSolaris);
    }

    @Test
    void testAddCarWithoutAdminAccess() {
        when(authService.hasAccess(alinaAlexandrova, Role.ADMIN)).thenReturn(false);

        carController.addCar(1, hyundaiSolaris, alinaAlexandrova);

        verify(authService, times(1)).hasAccess(alinaAlexandrova, Role.ADMIN);
        verify(carService, never()).addCar(anyInt(), any(Car.class));
    }

    @Test
    void testUpdateCarWithAdminAccess() {
        when(authService.hasAccess(maksimPlotnikov, Role.ADMIN)).thenReturn(true);
        doNothing().when(carService).updateCar(anyInt(), any(Car.class));

        carController.updateCar(1, hyundaiSolaris, maksimPlotnikov);

        verify(authService, times(1)).hasAccess(maksimPlotnikov, Role.ADMIN);
        verify(carService, times(1)).updateCar(1, hyundaiSolaris);
    }

    @Test
    void testUpdateCarWithoutAdminAccess() {
        when(authService.hasAccess(alinaAlexandrova, Role.ADMIN)).thenReturn(false);

        carController.updateCar(1, hyundaiSolaris, alinaAlexandrova);

        verify(authService, times(1)).hasAccess(alinaAlexandrova, Role.ADMIN);
        verify(carService, never()).updateCar(anyInt(), any(Car.class));
    }

    @Test
    void testDeleteCarWithAdminAccess() {
        when(authService.hasAccess(maksimPlotnikov, Role.ADMIN)).thenReturn(true);
        doNothing().when(carService).deleteCar(anyInt());

        carController.deleteCar(1, maksimPlotnikov);

        verify(authService, times(1)).hasAccess(maksimPlotnikov, Role.ADMIN);
        verify(carService, times(1)).deleteCar(1);
    }

    @Test
    void testDeleteCarWithoutAdminAccess() {
        when(authService.hasAccess(alinaAlexandrova, Role.ADMIN)).thenReturn(false);

        carController.deleteCar(1, alinaAlexandrova);

        verify(authService, times(1)).hasAccess(alinaAlexandrova, Role.ADMIN);
        verify(carService, never()).deleteCar(anyInt());
    }

    @Test
    void testGetCarsByMake() {
        List<Car> cars = List.of(hyundaiSolaris);
        when(carService.getCarsByMake("Hyundai")).thenReturn(cars);

        carController.getCarsByMake("Hyundai");

        verify(carService, times(1)).getCarsByMake("Hyundai");
    }

    @Test
    void testGetCarsByModel() {
        List<Car> cars = List.of(hyundaiSolaris);
        when(carService.getCarsByModel("Solaris")).thenReturn(cars);

        carController.getCarsByModel("Solaris");

        verify(carService, times(1)).getCarsByModel("Solaris");
    }

    @Test
    void testGetCarsByYear() {
        List<Car> cars = List.of(hyundaiSolaris);
        when(carService.getCarsByYear(2018)).thenReturn(cars);

        carController.getCarsByYear(2018);

        verify(carService, times(1)).getCarsByYear(2018);
    }

    @Test
    void testGetCarsByMileage() {
        List<Car> cars = List.of(hyundaiSolaris);
        when(carService.getCarsByMileage(10000)).thenReturn(cars);

        carController.getCarsByMileage(10000);

        verify(carService, times(1)).getCarsByMileage(10000);
    }

    @Test
    void testGetCarsByColor() {
        List<Car> cars = List.of(hyundaiSolaris);
        when(carService.getCarsByColor("Blue")).thenReturn(cars);

        carController.getCarsByColor("Blue");

        verify(carService, times(1)).getCarsByColor("Blue");
    }

    @Test
    void testGetCarsByEngine() {
        List<Car> cars = List.of(hyundaiSolaris);
        when(carService.getCarsByEngine("Gas")).thenReturn(cars);

        carController.getCarsByEngine("Gas");

        verify(carService, times(1)).getCarsByEngine("Gas");
    }

    @Test
    void testGetCarsByHorsepower() {
        List<Car> cars = List.of(hyundaiSolaris);
        when(carService.getCarsByHorsepower(100)).thenReturn(cars);

        carController.getCarsByHorsepower(100);

        verify(carService, times(1)).getCarsByHorsepower(100);
    }

    @Test
    void testGetCarsByAcceleration() {
        List<Car> cars = List.of(hyundaiSolaris);
        when(carService.getCarsByAcceleration(12)).thenReturn(cars);

        carController.getCarsByAcceleration(12);

        verify(carService, times(1)).getCarsByAcceleration(12);
    }

    @Test
    void testGetCarsByDriveTrain() {
        List<Car> cars = List.of(hyundaiSolaris);
        when(carService.getCarsByDriveTrain("Backward")).thenReturn(cars);

        carController.getCarsByDriveTrain("Backward");

        verify(carService, times(1)).getCarsByDriveTrain("Backward");
    }

    @Test
    void testGetCarsByPriceRange() {
        List<Car> cars = List.of(hyundaiSolaris);
        when(carService.getCarsByPriceRange(1000000, 1050000)).thenReturn(cars);

        carController.getCarsByPriceRange(1000000, 1050000);

        verify(carService, times(1)).getCarsByPriceRange(1000000, 1050000);
    }
}