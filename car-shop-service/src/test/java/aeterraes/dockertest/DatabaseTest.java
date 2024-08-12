package aeterraes.dockertest;

import aeterraes.dataaccess.LiquibaseConfig;
import aeterraes.dataaccess.entities.*;
import aeterraes.dataaccess.entities.Order;
import aeterraes.dataaccess.models.OrderStatus;
import aeterraes.dataaccess.models.Role;
import aeterraes.dataaccess.models.ServiceRequestStatus;
import aeterraes.dataaccess.models.UserRequestType;
import aeterraes.dataaccess.repositories.*;
import aeterraes.services.*;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;
import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class DatabaseTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("user")
            .withPassword("password");

    private static LiquibaseConfig liquibaseConfig;

    @BeforeAll
    public static void setUp() throws Exception {
        postgresContainer.start();
        liquibaseConfig = new LiquibaseConfig() {
            @Override
            public Connection getConnection() throws SQLException {
                return DriverManager.getConnection(
                        postgresContainer.getJdbcUrl(),
                        postgresContainer.getUsername(),
                        postgresContainer.getPassword()
                );
            }
        };

        try (Connection connection = liquibaseConfig.getConnection()) {
            Liquibase liquibase = new Liquibase("db.changelog-1.0.xml",
                    new ClassLoaderResourceAccessor(),
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection)));
            liquibase.update(new Contexts(), new LabelExpression());
        }

    }

    @AfterAll
    public static void tearDown() {
        postgresContainer.stop();
    }

    @Test
    @DisplayName("Should return all cars")
    void testGetAllCars() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);
        List<Car> cars = carService.getAllCars();

        assertThat(cars).hasSize(2);
    }

    @Test
    @DisplayName("Should return car by ID")
    void testGetCarById() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);

        Car car = carService.getCarById(2);

        assertThat(car).isNotNull();
    }

    @Test
    @DisplayName("Should return available cars")
    void testGetCarsByAvailability() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);

        List<Car> cars = carService.getCarsByAvailability();

        assertThat(cars).hasSize(1);
    }

    @Test
    @DisplayName("Should add a new car")
    void testAddCar() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);

        Car car = new Car(0, "Toyota", "Corolla", 2024,
                5000, "White", "Hybrid", 120,
                10, "Independent", "Automatic",
                "Forward", 2500000, true);
        carService.addCar(car);

        Car addedCar = carService.getCarById(carService.getLastAddedCarId());
        assertThat(addedCar).isNotNull();
    }

    @Test
    @DisplayName("Should update a car")
    void testUpdateCar() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);

        Car car = carService.getCarById(1);
        car.setColor("Green");
        carService.updateCar(car);

        Car updatedCar = carService.getCarById(1);
        assertThat(updatedCar.getColor()).isEqualTo("Green");
    }

    @Test
    @DisplayName("Should delete a car by ID")
    void testDeleteCar() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);

        Car car = carService.getCarById(1);
        carService.deleteCar(car.getCarId());

        Car deletedCar = carService.getCarById(1);
        assertThat(deletedCar).isNull();
    }

    @Test
    @DisplayName("Should return cars by make")
    void testGetCarsByMake() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);

        List<Car> cars = carService.getCarsByMake("Hyundai");

        assertThat(cars).hasSize(1);
    }

    @Test
    @DisplayName("Should return cars by model")
    void testGetCarsByModel() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);

        List<Car> cars = carService.getCarsByModel("CX-90");

        assertThat(cars).hasSize(1);
    }

    @Test
    @DisplayName("Should return cars by year")
    void testGetCarsByYear() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);

        List<Car> cars = carService.getCarsByYear(2023);

        assertThat(cars).hasSize(1);
    }

    @Test
    @DisplayName("Should return cars by mileage")
    void testGetCarsByMileage() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);

        List<Car> cars = carService.getCarsByMileage(10000);

        assertThat(cars).hasSize(1);
    }

    @Test
    @DisplayName("Should return cars by color")
    void testGetCarsByColor() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);

        List<Car> cars = carService.getCarsByColor("Red");

        assertThat(cars).hasSize(1);
    }

    @Test
    @DisplayName("Should return cars by engine")
    void testGetCarsByEngine() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);

        List<Car> cars = carService.getCarsByEngine("Gas");

        assertThat(cars).hasSize(1);
    }

    @Test
    @DisplayName("Should return cars by acceleration")
    void testGetCarsByAcceleration() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);

        List<Car> cars = carService.getCarsByAcceleration(7);

        assertThat(cars).hasSize(1);
    }

    @Test
    @DisplayName("Should return cars by drive train")
    void testGetCarsByDriveTrain() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);

        List<Car> cars = carService.getCarsByDriveTrain("Independent");

        assertThat(cars).hasSize(1);
    }

    @Test
    @DisplayName("Should return cars by price range")
    void testGetCarsByPriceRange() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);

        List<Car> cars = carService.getCarsByPriceRange(1000000, 9000000);

        assertThat(cars).hasSize(2);
    }

    @Test
    @DisplayName("Should change car availability")
    void testChangeAvailability() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);

        Car car = carService.getCarById(1);
        carService.changeAvailability(car.getCarId(), false);
        Car updatedCar = carService.getCarById(car.getCarId());

        assertThat(updatedCar.isAvailability()).isFalse();
    }

    @Test
    @DisplayName("Should return last added car ID")
    void testGetLastAddedCarId() {
        CarRepository carRepository = new CarRepository(liquibaseConfig);
        CarService carService = new CarService(carRepository);

        int lastAddedId = carService.getLastAddedCarId();

        assertThat(lastAddedId).isGreaterThan(0);
    }

    @Test
    @DisplayName("Should return all customers")
    void testGetAllCustomers() {
        CustomerRepository customerRepository = new CustomerRepository(liquibaseConfig);
        CustomerService customerService = new CustomerService(customerRepository);

        List<Customer> customers = customerService.getAllCustomers();

        assertThat(customers).hasSize(3);
    }

    @Test
    @DisplayName("Should return customer by ID")
    void testGetCustomerById() {
        CustomerRepository customerRepository = new CustomerRepository(liquibaseConfig);
        CustomerService customerService = new CustomerService(customerRepository);

        Customer customer = customerService.getCustomerById(1);

        assertThat(customer).isNotNull()
                .extracting(Customer::getEmail)
                .isEqualTo("alinochkasolnyshko1998@somemailservice.com");
    }

    @Test
    @DisplayName("Should add a new customer")
    void testAddCustomer() {
        CustomerRepository customerRepository = new CustomerRepository(liquibaseConfig);
        CustomerService customerService = new CustomerService(customerRepository);

        Customer customer = new Customer(3, "Oleg", "Petrov",
                "olegpetrov@somemailservice.com", "newpassword", Role.USER);
        customerService.addCustomer(customer);

        Customer addedCustomer = customerService.getCustomerById(3);
        assertThat(addedCustomer).isNotNull()
                .extracting(Customer::getEmail)
                .isEqualTo("olegpetrov@somemailservice.com");
    }

    @Test
    @DisplayName("Should update an existing customer")
    void testUpdateCustomer() {
        CustomerRepository customerRepository = new CustomerRepository(liquibaseConfig);
        CustomerService customerService = new CustomerService(customerRepository);

        Customer customer = customerService.getCustomerById(1);
        customer.setPassword("newpassword");
        customerService.updateCustomer(customer);

        Customer updatedCustomer = customerService.getCustomerById(1);
        assertThat(updatedCustomer).isNotNull()
                .extracting(Customer::getPassword)
                .isEqualTo("newpassword");
    }

    @Test
    @DisplayName("Should delete a customer by ID")
    void testDeleteCustomer() {
        CustomerRepository customerRepository = new CustomerRepository(liquibaseConfig);
        CustomerService customerService = new CustomerService(customerRepository);

        customerService.deleteCustomer(3);

        Customer deletedCustomer = customerService.getCustomerById(3);
        assertThat(deletedCustomer).isNull();
    }

    @Test
    @DisplayName("Should return customer by email")
    void testGetCustomerByEmail() {
        CustomerRepository customerRepository = new CustomerRepository(liquibaseConfig);
        CustomerService customerService = new CustomerService(customerRepository);

        Customer customer = customerService.getCustomerByEmail("alinochkasolnyshko1998@somemailservice.com");

        assertThat(customer).isNotNull()
                .extracting(Customer::getFirstName)
                .isEqualTo("Alina");
    }

    @Test
    @DisplayName("Should return all employees")
    void testGetAllEmployees() {
        EmployeeRepository employeeRepository = new EmployeeRepository(liquibaseConfig);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        List<Employee> employees = employeeService.getAllEmployees();

        assertThat(employees).hasSize(2);
    }

    @Test
    @DisplayName("Should return employee by ID")
    void testGetEmployeeById() {
        EmployeeRepository employeeRepository = new EmployeeRepository(liquibaseConfig);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        Employee employee = employeeService.getEmployeeById(1);

        assertThat(employee).isNotNull()
                .extracting(Employee::getEmail)
                .isEqualTo("annavolkova1999@somemailservice.com");
    }

    @Test
    @DisplayName("Should add a new employee")
    void testAddEmployee() {
        EmployeeRepository employeeRepository = new EmployeeRepository(liquibaseConfig);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        Employee newEmployee = new Employee(3, "Ivan", "Ivanov", "vanyacool@somemailservice.com", "12345Qwerty", Role.MANAGER);
        employeeService.addEmployee(newEmployee);

        Employee addedEmployee = employeeService.getEmployeeById(3);
        assertThat(addedEmployee).isNotNull()
                .extracting(Employee::getEmail)
                .isEqualTo("vanyacool@somemailservice.com");
    }

    @Test
    @DisplayName("Should update an existing employee")
    void testUpdateEmployee() {
        EmployeeRepository employeeRepository = new EmployeeRepository(liquibaseConfig);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        Employee employee = employeeService.getEmployeeById(1);
        employee.setPassword("newpassword");
        employeeService.updateEmployee(employee);

        Employee updatedEmployee = employeeService.getEmployeeById(1);
        assertThat(updatedEmployee).isNotNull()
                .extracting(Employee::getPassword)
                .isEqualTo("newpassword");
    }

    @Test
    @DisplayName("Should delete an employee by ID")
    void testDeleteEmployee() {
        EmployeeRepository employeeRepository = new EmployeeRepository(liquibaseConfig);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        employeeService.deleteEmployee(2);

        Employee deletedEmployee = employeeService.getEmployeeById(2);
        assertThat(deletedEmployee).isNull();
    }

    @Test
    @DisplayName("Should return employee by email")
    void testGetEmployeeByEmail() {
        EmployeeRepository employeeRepository = new EmployeeRepository(liquibaseConfig);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        Employee employee = employeeService.getEmployeeByEmail("annavolkova1999@somemailservice.com");

        assertThat(employee).isNotNull()
                .extracting(Employee::getFirstName)
                .isEqualTo("Anna");
    }

    @Test
    @DisplayName("Should add and retrieve order")
    void testAddAndRetrieveOrder() {
        Order order = new Order(1, 2, 2, 2000000.0,
                OrderStatus.CREATED, new Date(System.currentTimeMillis()));

        OrderRepository orderRepository = new OrderRepository(liquibaseConfig);
        OrderService orderService = new OrderService(orderRepository);

        orderService.addOrder(order);

        Order retrievedOrder = orderService.getOrderById(1);

        assertThat(retrievedOrder).isNotNull()
                .extracting(Order::getOrderId, Order::getTotalPrice, Order::getStatus)
                .containsExactly(1, 2000000.0, OrderStatus.CREATED);
    }

    @Test
    @DisplayName("Should add and retrieve service request")
    void testAddAndRetrieveServiceRequest() {
        ServiceRequestRepository serviceRequestRepository = new ServiceRequestRepository(liquibaseConfig);
        ServiceRequestService serviceRequestService = new ServiceRequestService(serviceRequestRepository);
        ServiceRequest request = new ServiceRequest(1, 2, 2, new Date(System.currentTimeMillis()),
                UserRequestType.PURCHASE, "Description", ServiceRequestStatus.CREATED);

        serviceRequestService.addServiceRequest(request);

        ServiceRequest retrievedRequest = serviceRequestService.getServiceRequestById(1);

        assertThat(retrievedRequest).isNotNull()
                .extracting(ServiceRequest::getRequestId, ServiceRequest::getRequestType, ServiceRequest::getRequestStatus)
                .containsExactly(1, UserRequestType.PURCHASE, ServiceRequestStatus.CREATED);
    }
}
