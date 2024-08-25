package aeterraes.controllers;

import aeterraes.dataaccess.entities.Car;
import aeterraes.dtos.CarDTO;
import aeterraes.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CarControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @Mock
    private ModelMapper modelMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarController carController = new CarController(carService, modelMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

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

        when(carService.getAllCars()).thenReturn(cars);
        when(modelMapper.map(any(Car.class), any())).thenReturn(carDTO);

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

        when(carService.getCarById(1)).thenReturn(car);
        when(modelMapper.map(any(Car.class), any())).thenReturn(carDTO);

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

        when(modelMapper.map(any(CarDTO.class), any())).thenReturn(car);
        when(modelMapper.map(any(Car.class), any())).thenReturn(carDTO);

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carId").value(1))
                .andExpect(jsonPath("$.make").value("Lada"));
    }
}
