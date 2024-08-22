package aeterraes.dataaccess.repositories;

import aeterraes.dataaccess.entities.Order;
import aeterraes.dataaccess.models.OrderStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private final Connection connection;

    public OrderRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM entity.orders";
        List<Order> orders = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                orders.add(mapRowToOrder(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Order getOrderById(int id) {
        String sql = "SELECT * FROM entity.orders WHERE orderid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToOrder(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addOrder(Order order) {
        String sql = "INSERT INTO entity.orders (customerid, carid, totalPrice, orderStatus, orderDate) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            setOrderParameters(pstmt, order);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrder(Order order) {
        String sql = "UPDATE entity.orders SET customerid = ?, carid = ?, totalPrice = ?, orderStatus = ?, orderDate = ? " +
                "WHERE orderid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            setOrderParameters(pstmt, order);
            pstmt.setInt(6, order.getOrderId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(int id) {
        String sql = "DELETE FROM entity.orders WHERE orderid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        String sql = "SELECT * FROM entity.orders WHERE orderStatus = ?";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status.name());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapRowToOrder(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getOrdersByDateRange(Date startDate, Date endDate) {
        String sql = "SELECT * FROM entity.orders WHERE orderDate BETWEEN ? AND ?";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, startDate);
            pstmt.setDate(2, endDate);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapRowToOrder(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getOrdersByPriceRange(double minPrice, double maxPrice) {
        String sql = "SELECT * FROM entity.orders WHERE totalPrice BETWEEN ? AND ?";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, minPrice);
            pstmt.setDouble(2, maxPrice);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapRowToOrder(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void updateOrderStatus(int orderId, OrderStatus newStatus) {
        String sql = "UPDATE entity.orders SET orderStatus = ? WHERE orderid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newStatus.name());
            pstmt.setInt(2, orderId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Order getOrderByCustomerId(int id) {
        String sql = "SELECT * FROM entity.orders WHERE customerid = ? ORDER BY orderid DESC LIMIT 1";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToOrder(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setOrderParameters(PreparedStatement pstmt, Order order) throws SQLException {
        pstmt.setInt(1, order.getCustomerId());
        pstmt.setInt(2, order.getCarId());
        pstmt.setDouble(3, order.getTotalPrice());
        pstmt.setString(4, order.getStatus().name());
        pstmt.setDate(5, new java.sql.Date(order.getOrderDate().getTime()));
    }

    private Order mapRowToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("orderid"));
        order.setCustomerId(rs.getInt("customerid"));
        order.setCarId(rs.getInt("carid"));
        order.setTotalPrice(rs.getDouble("totalPrice"));
        order.setStatus(OrderStatus.valueOf(rs.getString("orderStatus")));
        order.setOrderDate(rs.getDate("orderDate"));
        return order;
    }
}

