package com.alfy.accent;

import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.alfy.accent.db.ConnectionPool;
import com.alfy.accent.dto.Employee;
import com.alfy.accent.exception.BadRequestException;
import com.alfy.accent.mapper.EmployeeMapper;
import com.sun.jersey.api.NotFoundException;
import org.codehaus.enunciate.jaxrs.TypeHint;

/**
 * This service handles the employee data.
 */

@Path("/employee")
public class EmployeeService {

  @POST
  @Path("/")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @TypeHint(Employee.class)
  public Response createEmployee(Employee employee) {
    if (employee.getName() == null || employee.getName().equals("")) {
      throw new BadRequestException("employee name cannot be null");
    }

    try (Connection conn = ConnectionPool.getInstance().getConnection()) {
      try (PreparedStatement statement = conn.prepareStatement(
          "INSERT INTO employee (name, phone_number, address) VALUES (?, ?, ?)",
          Statement.RETURN_GENERATED_KEYS
      )) {
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getPhoneNumber());
        statement.setString(3, employee.getAddress());

        statement.execute();

        try (ResultSet resultSet = statement.getGeneratedKeys()) {
          resultSet.next();
          employee.setId(resultSet.getString("employee_id"));
          return Response.created(URI.create(employee.getId())).entity(employee).build();
        }
      }
    }
    catch (SQLException e) {
      return Response.status(500).entity(e).build();
    }
  }

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  @TypeHint(Employee.class)
  public Response getEmployees() {
    try (Connection conn = ConnectionPool.getInstance().getConnection()) {
      try (PreparedStatement statement = conn.prepareStatement(
          "SELECT employee_id, name, phone_number, address FROM employee"
      )) {
        try (ResultSet resultSet = statement.executeQuery()) {
          List<Employee> employeeList = new ArrayList<>();
          while (resultSet.next()) {
            employeeList.add(EmployeeMapper.fromResultSet(resultSet));
          }
          return Response.ok(employeeList).build();
        }
      }
    }
    catch (SQLException e) {
      return Response.status(500).entity(e).build();
    }
  }

  @PUT
  @Path("/{employeeId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @TypeHint(Employee.class)
  public Response createEmployee(@PathParam("employeeId") String employeeId,
                                 Employee employee) {
    if (employee.getName() == null || employee.getName().equals("")) {
      throw new BadRequestException("employee name cannot be null");
    }
    employee.setId(employeeId);

    try (Connection conn = ConnectionPool.getInstance().getConnection()) {
      try (PreparedStatement statement = conn.prepareStatement(
          "UPDATE employee SET name = ?, phone_number = ?, address = ? WHERE employee_id = ?"
      )) {
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getPhoneNumber());
        statement.setString(3, employee.getAddress());
        statement.setLong(4, Long.parseLong(employee.getId()));

        int updatedRows = statement.executeUpdate();

        if (updatedRows == 0) {
          throw new NotFoundException();
        }

        return Response.ok(employee).build();
      }
    }
    catch (SQLException e) {
      return Response.status(500).entity(e).build();
    }
  }

  @GET
  @Path("/{employeeId}")
  @Produces(MediaType.APPLICATION_JSON)
  @TypeHint(Employee.class)
  public Response getEmployee(@PathParam("employeeId") long employeeId) {
    try (Connection conn = ConnectionPool.getInstance().getConnection()) {
      try (PreparedStatement statement = conn.prepareStatement(
          "SELECT employee_id, name, phone_number, address FROM employee WHERE employee_id = ?"
      )) {
        statement.setLong(1, employeeId);

        try (ResultSet resultSet = statement.executeQuery()) {
          if (!resultSet.next()) {
            throw new NotFoundException();
          }

          return Response.ok(EmployeeMapper.fromResultSet(resultSet)).build();
        }
      }
    }
    catch (SQLException e) {
      return Response.status(500).entity(e).build();
    }
  }

  @DELETE
  @Path("/{employeeId}")
  @TypeHint(TypeHint.NO_CONTENT.class)
  public Response deleteTodo(@PathParam("employeeId") long employeeId) {
    try (Connection conn = ConnectionPool.getInstance().getConnection()) {
      try (PreparedStatement statement = conn.prepareStatement(
          "DELETE FROM employee WHERE employee_id = ?"
      )) {
        statement.setLong(1, employeeId);
        statement.execute();

        return Response.noContent().build();
      }
    }
    catch (SQLException e) {
      return Response.status(500).entity(e).build();
    }
  }
}
