package aeterraes.dataaccess.repositories;

import aeterraes.dataaccess.LiquibaseConfig;
import aeterraes.dataaccess.entities.Customer;
import aeterraes.dataaccess.models.Role;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    private final LiquibaseConfig liquibaseConfig;

    public CustomerRepository(LiquibaseConfig liquibaseConfig) {
        this.liquibaseConfig = liquibaseConfig;
    }

    private Connection getConnection() throws SQLException, IOException {
        return liquibaseConfig.getConnection();
    }

    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM entity.customers";
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                customers.add(mapRowToCustomer(rs));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM entity.customers WHERE customerid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToCustomer(rs);
                }
            }
        } catch (SQLException  | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer getCustomerByEmail(String email) {
        String sql = "SELECT * FROM entity.customers WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToCustomer(rs);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO entity.customers (firstname, lastname, email, password, role) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setCustomerParameters(pstmt, customer);
            pstmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(Customer customer) {
        String sql = "UPDATE entity.customers SET firstname = ?, lastname = ?, email = ?, password = ?, role = ? " +
                "WHERE customerid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setCustomerParameters(pstmt, customer);
            pstmt.setInt(6, customer.getCustomerId());
            pstmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int id) {
        String sql = "DELETE FROM entity.customers WHERE customerid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerParameters(PreparedStatement pstmt, Customer customer) throws SQLException {
        pstmt.setString(1, customer.getFirstName());
        pstmt.setString(2, customer.getLastName());
        pstmt.setString(3, customer.getEmail());
        pstmt.setString(4, customer.getPassword());
        pstmt.setString(5, customer.getRole().name());
    }

    private Customer mapRowToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customerid"));
        customer.setFirstName(rs.getString("firstname"));
        customer.setLastName(rs.getString("lastname"));
        customer.setEmail(rs.getString("email"));
        customer.setPassword(rs.getString("password"));
        customer.setRole(Role.valueOf(rs.getString("role")));
        return customer;
    }
}
