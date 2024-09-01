package aeterraes.dataaccess.repositories;

import aeterraes.configuration.DataSourceConfig;
import aeterraes.dataaccess.entities.Customer;
import aeterraes.dataaccess.models.Role;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Repository
public class CustomerRepository {

    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM entity.customers";
        List<Customer> customers = new ArrayList<>();
        try (Statement stmt = DataSourceConfig.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                customers.add(mapRowToCustomer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM entity.customers WHERE customerid = ?";
        try (PreparedStatement pstmt = DataSourceConfig.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToCustomer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer getCustomerByEmail(String email) {
        String sql = "SELECT * FROM entity.customers WHERE email = ?";
        try (PreparedStatement pstmt = DataSourceConfig.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToCustomer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO entity.customers (firstname, lastname, email, password, role) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = DataSourceConfig.getConnection().prepareStatement(sql)) {
            setCustomerParameters(pstmt, customer);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(Customer customer) {
        String sql = "UPDATE entity.customers SET firstname = ?, lastname = ?, email = ?, password = ?, role = ? " +
                "WHERE customerid = ?";
        try (PreparedStatement pstmt = DataSourceConfig.getConnection().prepareStatement(sql)) {
            setCustomerParameters(pstmt, customer);
            pstmt.setInt(6, customer.getCustomerId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int id) {
        String sql = "DELETE FROM entity.customers WHERE customerid = ?";
        try (PreparedStatement pstmt = DataSourceConfig.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
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
