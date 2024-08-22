package aeterraes.servlets;

import aeterraes.dataaccess.LiquibaseConfig;
import aeterraes.dataaccess.repositories.CustomerRepository;
import aeterraes.dtos.CustomerDTO;
import aeterraes.security.JwtService;
import aeterraes.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CustomerService customerService = new CustomerService(
            new CustomerRepository(LiquibaseConfig.getConnection()));

    public LoginServlet() throws SQLException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CustomerDTO loginRequest = objectMapper.readValue(req.getInputStream(), CustomerDTO.class);
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        if (customerService.getCustomerByEmail(email) != null
                && customerService.getCustomerByEmail(email).getPassword().equals(password)) {
            String token = JwtService.generateToken(email);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"token\":\"" + token + "\"}");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password");
        }
    }
}

