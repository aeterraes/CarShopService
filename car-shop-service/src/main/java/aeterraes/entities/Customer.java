package aeterraes.entities;

import aeterraes.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Customer extends User {
    private final List<ServiceRequest> serviceRequests;
    private final List<Order> orders;
    private final List<Car> cars;

    public Customer(int id, String firstName, String lastName, String email, String password, Role role,
                    List<ServiceRequest> serviceRequests, List<Order> orders, List<Car> cars) {
        super(id, firstName, lastName, email, password, role);
        this.serviceRequests = serviceRequests;
        this.orders = orders;
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role=" + getRole() +
                ", serviceRequests=" + serviceRequests +
                ", orders=" + orders +
                ", cars=" + cars +
                '}';
    }
}
