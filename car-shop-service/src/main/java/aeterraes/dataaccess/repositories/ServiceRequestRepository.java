package aeterraes.dataaccess.repositories;

import aeterraes.dataaccess.LiquibaseConfig;
import aeterraes.dataaccess.entities.ServiceRequest;
import aeterraes.dataaccess.models.ServiceRequestStatus;
import aeterraes.dataaccess.models.UserRequestType;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRequestRepository {

    private final LiquibaseConfig liquibaseConfig;

    public ServiceRequestRepository(LiquibaseConfig liquibaseConfig) {
        this.liquibaseConfig = liquibaseConfig;
    }

    private Connection getConnection() throws SQLException, IOException {
        return liquibaseConfig.getConnection();
    }

    public List<ServiceRequest> getAllServiceRequests() {
        String sql = "SELECT * FROM entity.serviceRequests";
        List<ServiceRequest> requests = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                requests.add(mapRowToServiceRequest(rs));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public ServiceRequest getServiceRequestById(int id) {
        String sql = "SELECT * FROM entity.serviceRequests WHERE requestid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToServiceRequest(rs);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addServiceRequest(ServiceRequest request) {
        String sql = "INSERT INTO entity.serviceRequests (customerid, carid, requestDate, requestType, requestDescription, requestStatus) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setServiceRequestParameters(pstmt, request);
            pstmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void updateServiceRequest(ServiceRequest request) {
        String sql = "UPDATE entity.serviceRequests SET customerid = ?, carid = ?, requestDate = ?, requestType = ?, requestDescription = ?, requestStatus = ? " +
                "WHERE requestid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setServiceRequestParameters(pstmt, request);
            pstmt.setInt(7, request.getRequestId());
            pstmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteServiceRequest(int id) {
        String sql = "DELETE FROM entity.serviceRequests WHERE requestid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<ServiceRequest> getServiceRequestsByStatus(ServiceRequestStatus status) {
        String sql = "SELECT * FROM entity.serviceRequests WHERE requestStatus = ?";
        List<ServiceRequest> requests = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status.name());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    requests.add(mapRowToServiceRequest(rs));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public List<ServiceRequest> getServiceRequestsByDateRange(Date startDate, Date endDate) {
        String sql = "SELECT * FROM entity.serviceRequests WHERE requestDate BETWEEN ? AND ?";
        List<ServiceRequest> requests = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, startDate);
            pstmt.setDate(2, endDate);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    requests.add(mapRowToServiceRequest(rs));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public List<ServiceRequest> getServiceRequestsByType(UserRequestType type) {
        String sql = "SELECT * FROM entity.serviceRequests WHERE requestType = ?";
        List<ServiceRequest> requests = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type.name());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    requests.add(mapRowToServiceRequest(rs));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public List<ServiceRequest> getServiceRequestsByCustomerId(int id) {
        String sql = "SELECT * FROM entity.serviceRequests WHERE customerid = ?";
        List<ServiceRequest> requests = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    requests.add(mapRowToServiceRequest(rs));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public ServiceRequest getServiceRequestByCustomerId(int id) {
        String sql = "SELECT * FROM entity.serviceRequests WHERE customerid = ? LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToServiceRequest(rs);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    private void setServiceRequestParameters(PreparedStatement pstmt, ServiceRequest request) throws SQLException {
        pstmt.setInt(1, request.getCustomerId());
        pstmt.setInt(2, request.getCarId());
        pstmt.setDate(3, new java.sql.Date(request.getRequestDate().getTime()));
        pstmt.setString(4, request.getRequestType().name());
        pstmt.setString(5, request.getRequestDescription());
        pstmt.setString(6, request.getRequestStatus().name());
    }

    private ServiceRequest mapRowToServiceRequest(ResultSet rs) throws SQLException {
        ServiceRequest request = new ServiceRequest();
        request.setRequestId(rs.getInt("requestid"));
        request.setCustomerId(rs.getInt("customerid"));
        request.setCarId(rs.getInt("carid"));
        request.setRequestDate(rs.getDate("requestDate"));
        request.setRequestType(UserRequestType.valueOf(rs.getString("requestType")));
        request.setRequestDescription(rs.getString("requestDescription"));
        request.setRequestStatus(ServiceRequestStatus.valueOf(rs.getString("requestStatus")));
        return request;
    }
}

