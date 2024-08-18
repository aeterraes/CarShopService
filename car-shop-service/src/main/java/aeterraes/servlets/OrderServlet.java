package aeterraes.servlets;

import aeterraes.dataaccess.LiquibaseConfig;
import aeterraes.dataaccess.entities.Order;
import aeterraes.dataaccess.models.OrderStatus;
import aeterraes.dataaccess.repositories.OrderRepository;
import aeterraes.dtos.OrderDTO;
import aeterraes.mappers.OrderMapper;
import aeterraes.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Setter
@WebServlet(urlPatterns = "/orders/*")
public class OrderServlet extends HttpServlet {

    private OrderService orderService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        objectMapper = new ObjectMapper();
        try {
            orderService = new OrderService(new OrderRepository(LiquibaseConfig.getConnection()));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String jsonResponse;

        if (pathInfo == null || pathInfo.equals("/")) {
            List<OrderDTO> orders = orderService.getAllOrders().stream()
                    .map(OrderMapper.INSTANCE::orderToOrderDTO)
                    .collect(Collectors.toList());
            jsonResponse = objectMapper.writeValueAsString(orders);
        } else {
            String[] pathParts = pathInfo.split("/");
            switch (pathParts[1]) {
                case "status":
                    String status = pathParts[2];
                    List<OrderDTO> ordersByStatus = orderService.getOrdersByStatus(OrderStatus.valueOf(status)).stream()
                            .map(OrderMapper.INSTANCE::orderToOrderDTO)
                            .collect(Collectors.toList());
                    jsonResponse = objectMapper.writeValueAsString(ordersByStatus);
                    break;
                case "date":
                    Date startDate = new Date(Long.parseLong(req.getParameter("startDate")));
                    Date endDate = new Date(Long.parseLong(req.getParameter("endDate")));
                    List<OrderDTO> ordersByDateRange = orderService.getOrdersByDateRange(startDate, endDate).stream()
                            .map(OrderMapper.INSTANCE::orderToOrderDTO)
                            .collect(Collectors.toList());
                    jsonResponse = objectMapper.writeValueAsString(ordersByDateRange);
                    break;
                case "price":
                    double minPrice = Double.parseDouble(req.getParameter("min"));
                    double maxPrice = Double.parseDouble(req.getParameter("max"));
                    List<OrderDTO> ordersByPriceRange = orderService.getOrdersByPriceRange(minPrice, maxPrice).stream()
                            .map(OrderMapper.INSTANCE::orderToOrderDTO)
                            .collect(Collectors.toList());
                    jsonResponse = objectMapper.writeValueAsString(ordersByPriceRange);
                    break;
                default:
                    if (pathParts.length == 2) {
                        int orderId = Integer.parseInt(pathParts[1]);
                        Order order = orderService.getOrderById(orderId);
                        if (order != null) {
                            jsonResponse = objectMapper.writeValueAsString(OrderMapper.INSTANCE.orderToOrderDTO(order));
                        } else {
                            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
                            return;
                        }
                    } else if (pathParts.length == 3 && "customer".equals(pathParts[1])) {
                        int customerId = Integer.parseInt(pathParts[2]);
                        Order order = orderService.getOrderByCustomerId(customerId);
                        if (order != null) {
                            jsonResponse = objectMapper.writeValueAsString(OrderMapper.INSTANCE.orderToOrderDTO(order));
                        } else {
                            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
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
        OrderDTO orderDTO = readJSON(req, OrderDTO.class);
        Order order = OrderMapper.INSTANCE.orderDTOToOrder(orderDTO);
        orderService.addOrder(order);
        Order addedOrder = orderService.getOrderById(orderDTO.getOrderId());
        int newOrderId = addedOrder != null ? addedOrder.getOrderId() : -1;
        writeJSON(resp, Map.of("id", newOrderId));
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDTO orderDTO = readJSON(req, OrderDTO.class);
        Order order = OrderMapper.INSTANCE.orderDTOToOrder(orderDTO);
        orderService.updateOrder(order);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.split("/").length == 2) {
            int orderId = Integer.parseInt(pathInfo.split("/")[1]);
            orderService.deleteOrder(orderId);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
        }
    }

    private void writeJSON(HttpServletResponse response, Object object) throws IOException {
        if (!response.isCommitted()) {
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(object));
        }
    }

    private <T> T readJSON(HttpServletRequest request, Class<T> clazz) throws IOException {
        return objectMapper.readValue(request.getReader(), clazz);
    }
}
