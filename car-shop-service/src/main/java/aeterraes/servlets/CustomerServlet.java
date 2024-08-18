package aeterraes.servlets;

import aeterraes.dataaccess.LiquibaseConfig;

import aeterraes.dataaccess.entities.Customer;
import aeterraes.dataaccess.repositories.CustomerRepository;
import aeterraes.dtos.CustomerDTO;
import aeterraes.mappers.CustomerMapper;
import aeterraes.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@WebServlet("/customers/*")
public class CustomerServlet extends HttpServlet {

    private CustomerService customerService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();

        objectMapper = new ObjectMapper();
        try {
            customerService = new CustomerService(new CustomerRepository(LiquibaseConfig.getConnection()));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            List<Customer> customers = customerService.getAllCustomers();
            List<CustomerDTO> customerDTOs = customers.stream()
                    .map(CustomerMapper.INSTANCE::customerToCustomerDTO)
                    .collect(Collectors.toList());
            writeJSON(response, customerDTOs);
        } else {
            try {
                String idStr = pathInfo.substring(1);
                int id = Integer.parseInt(idStr);
                Customer customer = customerService.getCustomerById(id);
                if (customer != null) {
                    CustomerDTO customerDTO = CustomerMapper.INSTANCE.customerToCustomerDTO(customer);
                    writeJSON(response, customerDTO);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            CustomerDTO customerDTO = readJSON(request, CustomerDTO.class);
            Customer customer = CustomerMapper.INSTANCE.customerDTOToCustomer(customerDTO);
            customerService.addCustomer(customer);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            CustomerDTO customerDTO = readJSON(request, CustomerDTO.class);
            Customer customer = CustomerMapper.INSTANCE.customerDTOToCustomer(customerDTO);
            customerService.updateCustomer(customer);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            String idStr = pathInfo.substring(1);
            int id = Integer.parseInt(idStr);
            customerService.deleteCustomer(id);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
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