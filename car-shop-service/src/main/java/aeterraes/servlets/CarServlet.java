package aeterraes.servlets;

import aeterraes.dataaccess.LiquibaseConfig;
import aeterraes.dataaccess.entities.Car;
import aeterraes.dataaccess.repositories.CarRepository;
import aeterraes.dtos.CarDTO;
import aeterraes.mappers.CarMapper;
import aeterraes.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;

import java.io.IOException;
import java.sql.SQLException;

import java.util.Map;
import java.util.stream.Collectors;

@Setter
@WebServlet(urlPatterns = "/cars/*")
public class CarServlet extends HttpServlet {

    private CarService carService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        objectMapper = new ObjectMapper();
        try {
            carService = new CarService(new CarRepository(LiquibaseConfig.getConnection()));

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        Object jsonResponse;

        if (pathInfo == null || pathInfo.equals("/")) {
            jsonResponse = carService.getAllCars().stream()
                    .map(CarMapper.INSTANCE::carToCarDTO)
                    .collect(Collectors.toList());
        } else {
            String[] pathParts = pathInfo.split("/");
            switch (pathParts[1]) {
                case "availability":
                    boolean availability = Boolean.parseBoolean(req.getParameter("available"));
                    jsonResponse = carService.getCarsByAvailability(availability).stream()
                            .map(CarMapper.INSTANCE::carToCarDTO)
                            .collect(Collectors.toList());
                    break;
                case "make":
                    String make = pathParts[2];
                    jsonResponse = carService.getCarsByMake(make).stream()
                            .map(CarMapper.INSTANCE::carToCarDTO)
                            .collect(Collectors.toList());
                    break;
                case "model":
                    String model = pathParts[2];
                    jsonResponse = carService.getCarsByModel(model).stream()
                            .map(CarMapper.INSTANCE::carToCarDTO)
                            .collect(Collectors.toList());
                    break;
                case "year":
                    int year = Integer.parseInt(pathParts[2]);
                    jsonResponse = carService.getCarsByYear(year).stream()
                            .map(CarMapper.INSTANCE::carToCarDTO)
                            .collect(Collectors.toList());
                    break;
                case "mileage":
                    double mileage = Double.parseDouble(pathParts[2]);
                    jsonResponse = carService.getCarsByMileage(mileage).stream()
                            .map(CarMapper.INSTANCE::carToCarDTO)
                            .collect(Collectors.toList());
                    break;
                case "color":
                    String color = pathParts[2];
                    jsonResponse = carService.getCarsByColor(color).stream()
                            .map(CarMapper.INSTANCE::carToCarDTO)
                            .collect(Collectors.toList());
                    break;
                case "engine":
                    String engine = pathParts[2];
                    jsonResponse = carService.getCarsByEngine(engine).stream()
                            .map(CarMapper.INSTANCE::carToCarDTO)
                            .collect(Collectors.toList());
                    break;
                case "horsepower":
                    int horsepower = Integer.parseInt(pathParts[2]);
                    jsonResponse = carService.getCarsByHorsepower(horsepower).stream()
                            .map(CarMapper.INSTANCE::carToCarDTO)
                            .collect(Collectors.toList());
                    break;
                case "acceleration":
                    int acceleration = Integer.parseInt(pathParts[2]);
                    jsonResponse = carService.getCarsByAcceleration(acceleration).stream()
                            .map(CarMapper.INSTANCE::carToCarDTO)
                            .collect(Collectors.toList());
                    break;
                case "drivetrain":
                    String drivetrain = pathParts[2];
                    jsonResponse = carService.getCarsByDriveTrain(drivetrain).stream()
                            .map(CarMapper.INSTANCE::carToCarDTO)
                            .collect(Collectors.toList());
                    break;
                case "price":
                    double minPrice = Double.parseDouble(req.getParameter("min"));
                    double maxPrice = Double.parseDouble(req.getParameter("max"));
                    jsonResponse = carService.getCarsByPriceRange(minPrice, maxPrice).stream()
                            .map(CarMapper.INSTANCE::carToCarDTO)
                            .collect(Collectors.toList());
                    break;
                case "suspension":
                    String suspension = pathParts[2];
                    jsonResponse = carService.getCarsBySuspension(suspension).stream()
                            .map(CarMapper.INSTANCE::carToCarDTO)
                            .collect(Collectors.toList());
                    break;
                case "gear":
                    String gear = pathParts[2];
                    jsonResponse = carService.getCarsByGear(gear).stream()
                            .map(CarMapper.INSTANCE::carToCarDTO)
                            .collect(Collectors.toList());
                    break;
                default:
                    if (pathParts.length == 2) {
                        int carId = Integer.parseInt(pathParts[1]);
                        Car car = carService.getCarById(carId);
                        if (car != null) {
                            jsonResponse = CarMapper.INSTANCE.carToCarDTO(car);
                        } else {
                            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Car not found");
                            return;
                        }
                    } else {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
                        return;
                    }
            }
        }

        writeJSON(resp, jsonResponse);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarDTO carDTO = readJSON(req, CarDTO.class);
        Car car = CarMapper.INSTANCE.carDTOToCar(carDTO);
        carService.addCar(car);
        int newCarId = carService.getLastAddedCarId();
        writeJSON(resp, Map.of("id", newCarId));
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarDTO carDTO = readJSON(req, CarDTO.class);
        Car car = CarMapper.INSTANCE.carDTOToCar(carDTO);
        carService.updateCar(car);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.split("/").length == 2) {
            int carId = Integer.parseInt(pathInfo.split("/")[1]);
            carService.deleteCar(carId);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
        }
    }

    private void writeJSON(HttpServletResponse response, Object object) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(object));
    }

    private <T> T readJSON(HttpServletRequest request, Class<T> clazz) throws IOException {
        return objectMapper.readValue(request.getReader(), clazz);
    }
}
