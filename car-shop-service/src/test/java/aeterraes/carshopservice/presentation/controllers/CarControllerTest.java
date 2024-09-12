package aeterraes.carshopservice.presentation.controllers;

import aeterraes.carshopservice.dataaccess.entities.Car;
import aeterraes.carshopservice.presentation.dtos.CarDTO;
import aeterraes.carshopservice.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllCars() throws Exception {
        Car car = new Car(1, "Lada", "Vesta",
                2024, 0, "White", "Gas",
                122, 13, "Independent",
                "Automatic", "Independent", 1992000, true);
        CarDTO carDTO = new CarDTO(1, "Lada", "Vesta",
                2024, 0, "White", "Gas",
                122, 13, "Independent",
                "Automatic", "Independent", 1992000, true);

        List<Car> cars = List.of(car);

        Mockito.when(carService.getAllCars()).thenReturn(cars);
        Mockito.when(modelMapper.map(ArgumentMatchers.any(Car.class), ArgumentMatchers.any())).thenReturn(carDTO);

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].carId").value(1))
                .andExpect(jsonPath("$[0].make").value("Lada"));
    }

    @Test
    public void testGetCarById() throws Exception {
        Car car = new Car(1, "Lada", "Vesta",
                2024, 0, "White", "Gas",
                122, 13, "Independent",
                "Automatic", "Independent", 1992000, true);
        CarDTO carDTO = new CarDTO(1, "Lada", "Vesta",
                2024, 0, "White", "Gas",
                122, 13, "Independent",
                "Automatic", "Independent", 1992000, true);

        Mockito.when(carService.getCarById(1)).thenReturn(car);
        Mockito.when(modelMapper.map(ArgumentMatchers.any(Car.class), ArgumentMatchers.any())).thenReturn(carDTO);

        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carId").value(1))
                .andExpect(jsonPath("$.make").value("Lada"));
    }

    @Test
    public void testAddCar() throws Exception {
        Car car = new Car(1, "Lada", "Vesta",
                2024, 0, "White", "Gas",
                122, 13, "Independent",
                "Automatic", "Independent", 1992000, true);
        CarDTO carDTO = new CarDTO(1, "Lada", "Vesta",
                2024, 0, "White", "Gas",
                122, 13, "Independent",
                "Automatic", "Independent", 1992000, true);

        Mockito.when(modelMapper.map(ArgumentMatchers.any(CarDTO.class), ArgumentMatchers.any())).thenReturn(car);
        Mockito.when(modelMapper.map(ArgumentMatchers.any(Car.class), ArgumentMatchers.any())).thenReturn(carDTO);

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carId").value(1))
                .andExpect(jsonPath("$.make").value("Lada"));
    }
}
