package aeterraes.controllers;

import aeterraes.entities.Customer;
import aeterraes.entities.Employee;
import aeterraes.entities.User;
import aeterraes.models.LoginStatus;
import aeterraes.models.OrderStatus;
import aeterraes.models.Role;
import aeterraes.models.UserRequestType;
import aeterraes.security.AuthService;
import aeterraes.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthService authorizationService;

    @InjectMocks
    private UserController userController;

    private Employee annaVolkova;
    private Employee maksimPlotnikov;
    private Customer romanRomanov;
    private Customer alinaAlexandrova;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        annaVolkova = new Employee(1, "Anna", "Volkova", "annavolkova1999@somemailservice.com", "password1234", Role.ADMIN);
        maksimPlotnikov = new Employee(2, "Maksim", "Plotnikov", "maxplotnikov2000@somemailservice.com", "qwertyQ", Role.MANAGER);
        romanRomanov = new Customer(3, "Roman", "Romanov", "romromych777@somemailservice.com", "RoMKrUteee",
                Role.USER, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        alinaAlexandrova = new Customer(4, "Alina", "Alexandrova", "alinochkasolnyshko1998@somemailservice.com", "passwordpassword",
                Role.USER, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    @Test
    void getAllUsers() {
        List<User> users = List.of(annaVolkova, maksimPlotnikov, romanRomanov, alinaAlexandrova);
        when(userService.getAllUsers()).thenReturn(users);
        when(authorizationService.hasAccess(maksimPlotnikov, Role.MANAGER)).thenReturn(true);

        userController.getAllUsers(maksimPlotnikov);

        verify(userService).getAllUsers();
    }

    @Test
    void getUserById() {
        when(userService.getUserById(1)).thenReturn(annaVolkova);
        when(authorizationService.hasAccess(maksimPlotnikov, Role.MANAGER)).thenReturn(true);

        userController.getUserById(1, maksimPlotnikov);

        verify(userService).getUserById(1);
    }

    @Test
    void registerUser() {
        Customer newUser = new Customer(5, "New", "User", "newuser@somemailservice.com",
                "newpassword", Role.USER, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        when(authorizationService.register(newUser)).thenReturn(LoginStatus.AUTHENTICATED);

        userController.registerUser(newUser);

        verify(authorizationService).register(newUser);
    }

    @Test
    void loginUser() {
        when(authorizationService.login("annavolkova1999@somemailservice.com", "password1234"))
                .thenReturn(LoginStatus.AUTHENTICATED);
        when(authorizationService.getAuthenticatedUsers()).thenReturn(List.of(annaVolkova));

        userController.loginUser("annavolkova1999@somemailservice.com", "password1234");

        verify(authorizationService).login("annavolkova1999@somemailservice.com", "password1234");
    }

    @Test
    void addUser() {
        when(authorizationService.hasAccess(maksimPlotnikov, Role.ADMIN)).thenReturn(true);
        when(authorizationService.register(romanRomanov)).thenReturn(LoginStatus.AUTHENTICATED);

        userController.addUser(romanRomanov, maksimPlotnikov);

        verify(userService).addUser(romanRomanov);
    }

    @Test
    void updateUserById() {
        when(authorizationService.hasAccess(maksimPlotnikov, Role.MANAGER)).thenReturn(true);

        userController.updateUserById(3, romanRomanov, maksimPlotnikov);

        verify(userService).updateUserById(3, romanRomanov);
    }

    @Test
    void deleteUserById() {
        when(authorizationService.hasAccess(maksimPlotnikov, Role.ADMIN)).thenReturn(true);

        userController.deleteUserById(4, maksimPlotnikov);

        verify(userService).deleteUserById(4);
    }

    @Test
    void getNumberOfOrdersByUserId() {
        when(authorizationService.hasAccess(maksimPlotnikov, Role.MANAGER)).thenReturn(true);
        when(userService.getNumberOfOrdersByUserId(3)).thenReturn(5);

        userController.getNumberOfOrdersByUserId(3, maksimPlotnikov);

        verify(userService).getNumberOfOrdersByUserId(3);
    }

    @Test
    void getCustomersByRequestType() {
        List<Customer> customers = new ArrayList<>();
        when(authorizationService.hasAccess(maksimPlotnikov, Role.MANAGER)).thenReturn(true);
        when(userService.getCustomersByRequestType(UserRequestType.MAINTENANCE)).thenReturn(customers);

        userController.getCustomersByRequestType(UserRequestType.MAINTENANCE, maksimPlotnikov);

        verify(userService).getCustomersByRequestType(UserRequestType.MAINTENANCE);
    }

    @Test
    void getCustomersByOrderStatus() {
        List<Customer> customers = new ArrayList<>();
        when(authorizationService.hasAccess(maksimPlotnikov, Role.MANAGER)).thenReturn(true);
        when(userService.getCustomersByOrderStatus(OrderStatus.DELIVERED)).thenReturn(customers);

        userController.getCustomersByOrderStatus(OrderStatus.DELIVERED, maksimPlotnikov);

        verify(userService).getCustomersByOrderStatus(OrderStatus.DELIVERED);
    }

    @Test
    void getAllCustomers() {
        List<Customer> customers = List.of();
        when(authorizationService.hasAccess(maksimPlotnikov, Role.MANAGER)).thenReturn(true);
        when(userService.getAllCustomers()).thenReturn(customers);

        userController.getAllCustomers(maksimPlotnikov);

        verify(userService).getAllCustomers();
    }

    @Test
    void getAllEmployees() {
        List<Employee> employees = List.of();
        when(authorizationService.hasAccess(maksimPlotnikov, Role.ADMIN)).thenReturn(true);
        when(userService.getAllEmployees()).thenReturn(employees);

        userController.getAllEmployees(maksimPlotnikov);

        verify(userService).getAllEmployees();
    }

    @Test
    void getUsersByFirstName() {
        List<User> users = List.of(annaVolkova);
        when(authorizationService.hasAccess(maksimPlotnikov, Role.MANAGER)).thenReturn(true);
        when(userService.getUsersByFirstName("Anna")).thenReturn(users);

        userController.getUsersByFirstName("Anna", maksimPlotnikov);

        verify(userService).getUsersByFirstName("Anna");
    }

    @Test
    void getUsersByLastName() {
        List<User> users = List.of(romanRomanov);
        when(authorizationService.hasAccess(maksimPlotnikov, Role.MANAGER)).thenReturn(true);
        when(userService.getUsersByLastName("Romanov")).thenReturn(users);

        userController.getUsersByLastName("Romanov", maksimPlotnikov);

        verify(userService).getUsersByLastName("Romanov");
    }

    @Test
    void addEmployee() {
        when(authorizationService.hasAccess(maksimPlotnikov, Role.ADMIN)).thenReturn(true);
        userController.addEmployee(annaVolkova, maksimPlotnikov);
        verify(userService).addEmployee(any(Employee.class));
    }

    @Test
    void updateEmployeeById() {
        when(authorizationService.hasAccess(maksimPlotnikov, Role.ADMIN)).thenReturn(true);
        userController.updateEmployeeById(1, annaVolkova, maksimPlotnikov);
        verify(userService).updateEmployeeById(anyInt(), any(Employee.class));
    }
}

