package com.alfy.accent.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.alfy.accent.dto.Employee;

/**
 * Author: Alan Christensen
 * 12/23/2014
 */
public class EmployeeMapper {
  public static Employee fromResultSet(ResultSet resultSet) throws SQLException {
    Employee employee = new Employee();
    employee.setId(resultSet.getString("employee_id"));
    employee.setName(resultSet.getString("name"));
    employee.setPhoneNumber(resultSet.getString("phone_number"));
    employee.setAddress(resultSet.getString("address"));
    return employee;
  }
}
