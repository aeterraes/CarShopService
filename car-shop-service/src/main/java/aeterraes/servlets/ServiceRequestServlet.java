package aeterraes.servlets;

import aeterraes.dataaccess.LiquibaseConfig;
import aeterraes.dataaccess.entities.ServiceRequest;
import aeterraes.dataaccess.models.ServiceRequestStatus;
import aeterraes.dataaccess.models.UserRequestType;
import aeterraes.dataaccess.repositories.ServiceRequestRepository;
import aeterraes.dtos.ServiceRequestDTO;
import aeterraes.mappers.ServiceRequestMapper;
import aeterraes.services.ServiceRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/service-requests/*")
@RequiredArgsConstructor
public class ServiceRequestServlet extends HttpServlet {

    private ServiceRequestService serviceRequestService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        objectMapper = new ObjectMapper();
        try {
            serviceRequestService = new ServiceRequestService(
                    new ServiceRequestRepository(LiquibaseConfig.getConnection()));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String jsonResponse;

        if (pathInfo == null || pathInfo.equals("/")) {
            List<ServiceRequestDTO> serviceRequests = serviceRequestService.getAllServiceRequests().stream()
                    .map(ServiceRequestMapper.INSTANCE::serviceRequestToServiceRequestDTO)
                    .collect(Collectors.toList());
            jsonResponse = objectMapper.writeValueAsString(serviceRequests);
        } else {
            String[] pathParts = pathInfo.split("/");
            switch (pathParts[1]) {
                case "status":
                    String status = pathParts[2];
                    List<ServiceRequestDTO> requestsByStatus = serviceRequestService.getServiceRequestsByStatus(
                                    ServiceRequestStatus.valueOf(status)).stream()
                            .map(ServiceRequestMapper.INSTANCE::serviceRequestToServiceRequestDTO)
                            .collect(Collectors.toList());
                    jsonResponse = objectMapper.writeValueAsString(requestsByStatus);
                    break;
                case "date":
                    Date startDate = new Date(Long.parseLong(req.getParameter("startDate")));
                    Date endDate = new Date(Long.parseLong(req.getParameter("endDate")));
                    List<ServiceRequestDTO> requestsByDateRange = serviceRequestService.getServiceRequestsByDateRange(
                                    startDate, endDate).stream()
                            .map(ServiceRequestMapper.INSTANCE::serviceRequestToServiceRequestDTO)
                            .collect(Collectors.toList());
                    jsonResponse = objectMapper.writeValueAsString(requestsByDateRange);
                    break;
                case "type":
                    String type = pathParts[2];
                    List<ServiceRequestDTO> requestsByType = serviceRequestService.getServiceRequestsByType(
                                    UserRequestType.valueOf(type)).stream()
                            .map(ServiceRequestMapper.INSTANCE::serviceRequestToServiceRequestDTO)
                            .collect(Collectors.toList());
                    jsonResponse = objectMapper.writeValueAsString(requestsByType);
                    break;
                default:
                    if (pathParts.length == 2) {
                        int requestId = Integer.parseInt(pathParts[1]);
                        ServiceRequest serviceRequest = serviceRequestService.getServiceRequestById(requestId);
                        if (serviceRequest != null) {
                            jsonResponse = objectMapper.writeValueAsString(
                                    ServiceRequestMapper.INSTANCE.serviceRequestToServiceRequestDTO(serviceRequest));
                        } else {
                            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Service request not found");
                            return;
                        }
                    } else if (pathParts.length == 3 && "customer".equals(pathParts[1])) {
                        int customerId = Integer.parseInt(pathParts[2]);
                        List<ServiceRequestDTO> serviceRequestsByCustomerId = serviceRequestService.getServiceRequestsByCustomerId(
                                        customerId).stream()
                                .map(ServiceRequestMapper.INSTANCE::serviceRequestToServiceRequestDTO)
                                .collect(Collectors.toList());
                        jsonResponse = objectMapper.writeValueAsString(serviceRequestsByCustomerId);
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
        ServiceRequestDTO serviceRequestDTO = readJSON(req, ServiceRequestDTO.class);
        ServiceRequest serviceRequest = ServiceRequestMapper.INSTANCE.serviceRequestDTOToServiceRequest(serviceRequestDTO);
        serviceRequestService.addServiceRequest(serviceRequest);
        int newRequestId = serviceRequest.getRequestId();
        writeJSON(resp, Map.of("id", newRequestId));
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceRequestDTO serviceRequestDTO = readJSON(req, ServiceRequestDTO.class);
        ServiceRequest serviceRequest = ServiceRequestMapper.INSTANCE.serviceRequestDTOToServiceRequest(serviceRequestDTO);
        serviceRequestService.updateServiceRequest(serviceRequest);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.split("/").length == 2) {
            int requestId = Integer.parseInt(pathInfo.split("/")[1]);
            serviceRequestService.deleteServiceRequest(requestId);
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
