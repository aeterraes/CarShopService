package aeterraes.servlets;

import aeterraes.dataaccess.entities.Car;
import aeterraes.dtos.CarDTO;
import aeterraes.mappers.CarMapper;
import aeterraes.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

public class CarServletTest {

    @Mock
    private CarService carService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private CarServlet carServlet;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        carServlet.setCarService(carService);
        carServlet.setObjectMapper(objectMapper);
    }

    @Test
    public void testDoPost() throws IOException, ServletException {
        String carJson = "{\"carId\":0,\"make\":\"Toyota\",\"model\":\"Camry\",\"year\":2024,\"mileage\":10.5,\"color\":\"blue\",\"engine\":\"V6\",\"horsepower\":300,\"acceleration\":6,\"driveTrain\":\"AWD\",\"price\":30000,\"availability\":true,\"suspension\":\"standard\",\"gear\":\"automatic\"}";
        CarDTO carDTO = objectMapper.readValue(carJson, CarDTO.class);
        Car car = CarMapper.INSTANCE.carDTOToCar(carDTO);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(carJson)));


        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        carServlet.doPost(request, response);

        verify(carService, times(1)).addCar(car);
        verify(response, times(1)).setContentType("application/json");
        verify(response, times(1)).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    public void testDoGet_specificCar() throws IOException, ServletException {
        int carId = 1;
        CarDTO carDTO = new CarDTO(carId, "Hyundai", "Solaris", 2018, 10000, "Blue", "Gas", 100, 12, "Independent", "Mechanic", "Backward", 1025000, true);
        String carJson = objectMapper.writeValueAsString(carDTO);

        when(request.getPathInfo()).thenReturn("/" + carId);
        when(carService.getCarById(carId)).thenReturn(CarMapper.INSTANCE.carDTOToCar(carDTO));

        PrintWriter printWriter = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(printWriter);

        carServlet.doGet(request, response);

        verify(carService, times(1)).getCarById(carId);
        verify(response, times(1)).setContentType("application/json");
        verify(printWriter, times(1)).write(carJson);
    }

    @Test
    public void testDoGet_allCars() throws IOException, ServletException {
        CarDTO car1DTO = new CarDTO(1, "Hyundai", "Solaris", 2018, 10000, "Blue", "Gas", 100, 12, "Independent", "Mechanic", "Backward", 1025000, true);
        CarDTO car2DTO = new CarDTO(2, "Mazda", "CX-90", 2023, 0, "Red", "Gas", 328, 7, "Independent", "Automatic", "Independent", 8700000, true);
        List<CarDTO> carDTOs = List.of(car1DTO, car2DTO);
        String carsJson = objectMapper.writeValueAsString(carDTOs);

        when(carService.getAllCars()).thenReturn(carDTOs.stream().map(CarMapper.INSTANCE::carDTOToCar).collect(Collectors.toList()));

        PrintWriter printWriter = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(printWriter);

        carServlet.doGet(request, response);

        verify(carService, times(1)).getAllCars();
        verify(response, times(1)).setContentType("application/json");
        verify(printWriter, times(1)).write(carsJson);
    }
}
