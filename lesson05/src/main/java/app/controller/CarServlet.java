package app.controller;

import app.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CarServlet extends HttpServlet {

    private final CarRepository repository = new CarRepositoryMap();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> parameters = req.getParameterMap();
        PrintWriter respWriter = resp.getWriter();

        String[] idValues = parameters.get("id");
        if (idValues == null) {
            respWriter.write(getAllCars(parameters));
        } else {
            Long id = Long.parseLong(idValues[0]);
            respWriter.write(repository.getById(id).toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Car newCar = mapper.readValue(req.getReader(), Car.class);
        newCar = repository.save(newCar);

        String jsonResult = mapper.writeValueAsString(newCar);
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(jsonResult);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> parameters = req.getParameterMap();
        Long id = Long.parseLong(parameters.get("id")[0]);
        Car car = repository.getById(id);

        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerFor(JsonNode.class);
        JsonNode jsonNode = reader.readTree(req.getReader());
        JsonNode priceNode = jsonNode.path("price");
        BigDecimal newCarPrice = BigDecimal.valueOf(priceNode.asDouble());
        car.setPrice(newCarPrice);
        repository.update(car);

        returnSuccess(resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> parameters = req.getParameterMap();
        Long id = Long.parseLong(parameters.get("id")[0]);
        repository.deleteById(id);

        returnSuccess(resp);
    }

    private void returnSuccess(HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.getWriter().write("{\"status\":\"success\"}");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private String getAllCars(Map<String, String[]> parameters) {
        String[] minParameters = parameters.get("min_price");
        BigDecimal minParameter = minParameters != null ? new BigDecimal(minParameters[0]) : null;
        String[] maxParameters = parameters.get("max_price");
        BigDecimal maxParameter = maxParameters != null ? new BigDecimal(maxParameters[0]) : null;
        String[] sortParameters = parameters.get("sort_by");
        String sortParameter = sortParameters != null ? sortParameters[0] : null;

        List<Car> cars = new CarServiceImpl(repository)
                .setMinPrice(minParameter)
                .setMaxPrice(maxParameter)
                .setSortedBy(sortParameter)
                .getAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (Car car : cars) {
            stringBuilder.append(car.toString()).append("\n");
        }

        return stringBuilder.toString();
    }
}
