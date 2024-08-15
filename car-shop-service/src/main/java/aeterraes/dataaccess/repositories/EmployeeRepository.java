package aeterraes.dataaccess.repositories;

import aeterraes.dataaccess.LiquibaseConfig;
import aeterraes.dataaccess.entities.Employee;
import aeterraes.dataaccess.models.Role;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EmployeeRepository {

    private final LiquibaseConfig liquibaseConfig;

    public EmployeeRepository(LiquibaseConfig liquibaseConfig) {
        this.liquibaseConfig = liquibaseConfig;
    }

    private Connection getConnection() throws SQLException, IOException {
        return liquibaseConfig.getConnection();
    }

    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM entity.employees";
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                employees.add(mapRowToEmployee(rs));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM entity.employees WHERE employeeid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToEmployee(rs);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Employee getEmployeeByEmail(String email) {
        String sql = "SELECT * FROM entity.employees WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToEmployee(rs);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO entity.employees (firstname, lastname, email, password, role) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setEmployeeParameters(pstmt, employee);
            pstmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee) {
        String sql = "UPDATE entity.employees SET firstname = ?, lastname = ?, email = ?, password = ?, role = ? " +
                "WHERE employeeid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setEmployeeParameters(pstmt, employee);
            pstmt.setInt(6, employee.getEmployeeId());
            pstmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int id) {
        String sql = "DELETE FROM entity.employees WHERE employeeid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setEmployeeParameters(PreparedStatement pstmt, Employee employee) throws SQLException {
        pstmt.setString(1, employee.getFirstName());
        pstmt.setString(2, employee.getLastName());
        pstmt.setString(3, employee.getEmail());
        pstmt.setString(4, employee.getPassword());
        pstmt.setString(5, employee.getRole().name());
    }

    private Employee mapRowToEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(rs.getInt("employeeid"));
        employee.setFirstName(rs.getString("firstname"));
        employee.setLastName(rs.getString("lastname"));
        employee.setEmail(rs.getString("email"));
        employee.setPassword(rs.getString("password"));
        employee.setRole(Role.valueOf(rs.getString("role")));
        return employee;
    }
}

