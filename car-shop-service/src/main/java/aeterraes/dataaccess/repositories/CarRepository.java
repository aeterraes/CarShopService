package aeterraes.dataaccess.repositories;

import aeterraes.dataaccess.LiquibaseConfig;
import aeterraes.dataaccess.entities.Car;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {

    private final LiquibaseConfig liquibaseConfig;

    public CarRepository(LiquibaseConfig liquibaseConfig) {
        this.liquibaseConfig = liquibaseConfig;
    }

    private Connection getConnection() throws SQLException, IOException {
        return liquibaseConfig.getConnection();
    }

    public List<Car> getAllCars() {
        String sql = "SELECT * FROM entity.cars";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cars.add(mapRowToCar(rs));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public Car getCarById(int id) {
        String sql = "SELECT * FROM entity.cars WHERE carid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToCar(rs);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Car> getCarsByAvailability() {
        String sql = "SELECT * FROM entity.cars WHERE availability = true";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cars.add(mapRowToCar(rs));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public void changeAvailability(int carId, boolean availability) {
        String sql = "UPDATE entity.cars SET availability = ? WHERE carid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, availability);
            pstmt.setInt(2, carId);
            pstmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void addCar(Car car) {
        String sql = "INSERT INTO entity.cars (make, model, year, mileage, color, engine, horsepower, acceleration, suspension, gear, drivetrain, price, availability) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setCarParameters(pstmt, car);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    car.setCarId(rs.getInt(1));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void updateCar(Car car) {
        String sql = "UPDATE entity.cars SET make = ?, model = ?, year = ?, mileage = ?, color = ?, engine = ?, horsepower = ?, acceleration = ?, " +
                "suspension = ?, gear = ?, drivetrain = ?, price = ?, availability = ? WHERE carid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setCarParameters(pstmt, car);
            pstmt.setInt(14, car.getCarId());
            pstmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteCar(int id) {
        String sql = "DELETE FROM entity.cars WHERE carid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getCarsByMake(String make) {
        String sql = "SELECT * FROM entity.cars WHERE make = ?";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, make);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cars.add(mapRowToCar(rs));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> getCarsByModel(String model) {
        String sql = "SELECT * FROM entity.cars WHERE model = ?";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, model);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cars.add(mapRowToCar(rs));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> getCarsByYear(int year) {
        String sql = "SELECT * FROM entity.cars WHERE year = ?";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, year);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cars.add(mapRowToCar(rs));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> getCarsByMileage(double mileage) {
        String sql = "SELECT * FROM entity.cars WHERE mileage = ?";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, mileage);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cars.add(mapRowToCar(rs));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> getCarsByColor(String color) {
        String sql = "SELECT * FROM entity.cars WHERE color = ?";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, color);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cars.add(mapRowToCar(rs));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> getCarsByEngine(String engine) {
        String sql = "SELECT * FROM entity.cars WHERE engine = ?";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, engine);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cars.add(mapRowToCar(rs));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> getCarsByHorsepower(int horsepower) {
        String sql = "SELECT * FROM entity.cars WHERE horsepower = ?";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, horsepower);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cars.add(mapRowToCar(rs));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> getCarsByAcceleration(int acceleration) {
        String sql = "SELECT * FROM entity.cars WHERE acceleration = ?";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, acceleration);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cars.add(mapRowToCar(rs));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> getCarsByDriveTrain(String driveTrain) {
        String sql = "SELECT * FROM entity.cars WHERE drivetrain = ?";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, driveTrain);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cars.add(mapRowToCar(rs));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> getCarsByPriceRange(double minPrice, double maxPrice) {
        String sql = "SELECT * FROM entity.cars WHERE price BETWEEN ? AND ?";
        List<Car> cars = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setDouble(1, minPrice);
             pstmt.setDouble(2, maxPrice);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cars.add(mapRowToCar(rs));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public int getLastAddedCarId() {
        String sql = "SELECT carid FROM entity.cars ORDER BY carid DESC LIMIT 1";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("carid");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return -1;
    }


    private void setCarParameters(PreparedStatement pstmt, Car car) throws SQLException {
        pstmt.setString(1, car.getMake());
        pstmt.setString(2, car.getModel());
        pstmt.setInt(3, car.getYear());
        pstmt.setDouble(4, car.getMileage());
        pstmt.setString(5, car.getColor());
        pstmt.setString(6, car.getEngine());
        pstmt.setInt(7, car.getHorsepower());
        pstmt.setInt(8, car.getAcceleration());
        pstmt.setString(9, car.getSuspension());
        pstmt.setString(10, car.getGear());
        pstmt.setString(11, car.getDriveTrain());
        pstmt.setDouble(12, car.getPrice());
        pstmt.setBoolean(13, car.isAvailability());
    }

    private Car mapRowToCar(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setCarId(rs.getInt("carid"));
        car.setMake(rs.getString("make"));
        car.setModel(rs.getString("model"));
        car.setYear(rs.getInt("year"));
        car.setMileage(rs.getDouble("mileage"));
        car.setColor(rs.getString("color"));
        car.setEngine(rs.getString("engine"));
        car.setHorsepower(rs.getInt("horsepower"));
        car.setAcceleration(rs.getInt("acceleration"));
        car.setSuspension(rs.getString("suspension"));
        car.setGear(rs.getString("gear"));
        car.setDriveTrain(rs.getString("drivetrain"));
        car.setPrice(rs.getDouble("price"));
        car.setAvailability(rs.getBoolean("availability"));
        return car;
    }
}
