package aeterraes.servlets;

import aeterraes.dataaccess.LiquibaseConfig;
import aeterraes.dataaccess.entities.Employee;
import aeterraes.dataaccess.repositories.EmployeeRepository;
import aeterraes.dtos.EmployeeDTO;
import aeterraes.mappers.EmployeeMapper;
import aeterraes.services.EmployeeService;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Setter
@WebServlet(urlPatterns = "/employees/*")
public class EmployeeServlet extends HttpServlet {

    private EmployeeService employeeService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        objectMapper = new ObjectMapper();
        try {
            employeeService = new EmployeeService(new EmployeeRepository(LiquibaseConfig.getConnection()));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String jsonResponse;

        if (pathInfo == null || pathInfo.equals("/")) {
            List<EmployeeDTO> employees = employeeService.getAllEmployees().stream()
                    .map(EmployeeMapper.INSTANCE::employeeToEmployeeDTO)
                    .collect(Collectors.toList());
            jsonResponse = objectMapper.writeValueAsString(employees);
        } else {
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 3 && "email".equals(pathParts[1])) {
                String email = pathParts[2];
                Employee employeeByEmail = employeeService.getEmployeeByEmail(email);
                if (employeeByEmail != null) {
                    jsonResponse = objectMapper.writeValueAsString(EmployeeMapper.INSTANCE.employeeToEmployeeDTO(employeeByEmail));
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee not found");
                    return;
                }
            } else if (pathParts.length == 2) {
                int employeeId = Integer.parseInt(pathParts[1]);
                Employee employee = employeeService.getEmployeeById(employeeId);
                if (employee != null) {
                    jsonResponse = objectMapper.writeValueAsString(EmployeeMapper.INSTANCE.employeeToEmployeeDTO(employee));
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee not found");
                    return;
                }
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
                return;
            }
        }
        writeJSON(resp, jsonResponse);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmployeeDTO employeeDTO = readJSON(req, EmployeeDTO.class);
        Employee employee = EmployeeMapper.INSTANCE.employeeDTOToEmployee(employeeDTO);
        employeeService.addEmployee(employee);
        Employee addedEmployee = employeeService.getEmployeeByEmail(employeeDTO.getEmail());
        int newEmployeeId = addedEmployee != null ? addedEmployee.getEmployeeId() : -1;
        writeJSON(resp, Map.of("id", newEmployeeId));
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmployeeDTO employeeDTO = readJSON(req, EmployeeDTO.class);
        Employee employee = EmployeeMapper.INSTANCE.employeeDTOToEmployee(employeeDTO);
        employeeService.updateEmployee(employee);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.split("/").length == 2) {
            int employeeId = Integer.parseInt(pathInfo.split("/")[1]);
            employeeService.deleteEmployee(employeeId);
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
