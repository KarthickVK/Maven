package com.employeemanagement.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.employeemanagement.model.Employee;
import com.employeemanagement.exception.AccessfailedException;

/**
 * EmployeeDaoImpl
 * 
 * @author KarthickV
 */
public class EmployeeDaoImpl implements EmployeeDao {

	/**
	 * Create the mySql insert statement.
	 */
	public boolean createEmployee(final Employee employee) {
		final String query = "insert into employeedetails (employeeId, employeeName, employeePhoneNo, employeeSalary, employeeDateOfBirth, isactive) values (?, ?, ?, ?, ?, true)";

		try (Connection connection = ConnectDataBase.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			final Date startDate = new Date(employee.getEmployeeDateOfBirth().getTime());

			preparedStatement.setString(1, employee.getEmployeeId());
			preparedStatement.setString(2, employee.getEmployeeName());
			preparedStatement.setString(3, employee.getEmployeePhoneNo());
			preparedStatement.setString(4, employee.getEmployeeSalary());
			preparedStatement.setDate(5, startDate);

			return preparedStatement.executeUpdate() > 0;
		} catch (Exception exception) {
			throw new AccessfailedException("Database Access Failed");
		}
	}

	/**
	 * Create the mySql update statement.
	 */
	public boolean updateEmployee(final Employee employeeDetails) {
		final StringBuilder stringBuilder = new StringBuilder();
		String query = stringBuilder.append("update employeedetails set").toString();
		boolean hasNextColumn = false;

		try (Connection connection = ConnectDataBase.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			
			if (employeeDetails.getEmployeeName() != null) {
				query = stringBuilder.append(" employeeName = '").append(employeeDetails.getEmployeeName()).append("'")
						.toString();
				hasNextColumn = true;
			}

			if (employeeDetails.getEmployeePhoneNo() != null) {

				if (hasNextColumn) {
					query = stringBuilder.append(",").toString();
				}
				query = stringBuilder.append(" employeePhoneNo = '").append(employeeDetails.getEmployeePhoneNo())
						.append("'").toString();
				hasNextColumn = true;
			}

			if (employeeDetails.getEmployeeSalary() != null) {

				if (hasNextColumn) {
					query = stringBuilder.append(",").toString();
				}
				query = stringBuilder.append(" employeeSalary = '").append(employeeDetails.getEmployeeSalary())
						.append("'").toString();
				hasNextColumn = true;
			}

			if (employeeDetails.getEmployeeDateOfBirth() != null) {
				
				if (hasNextColumn) {
					query = stringBuilder.append(",").toString();
				}
				query = stringBuilder.append(" employeeDateOfBirth = '")
						.append(employeeDetails.getEmployeeDateOfBirth()).append("'").toString();
				hasNextColumn = true;
			}
			query = stringBuilder.append(" where employeeId = '").append(employeeDetails.getEmployeeId()).append("'")
					.toString();
			
			return statement.executeUpdate(query) > 0;
		} catch (Exception exception) {
			throw new AccessfailedException("Database Access Failed");
		}
	}

	/**
	 * Create the mySql delete statement.
	 */
	public boolean deleteEmployee(final String employeeId) {
		final String query = "update employeedetails set isactive = false where employeeId = ?";

		try (Connection connection = ConnectDataBase.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, employeeId);
			
			return preparedStatement.executeUpdate() > 0;
		} catch (Exception exception) {
			throw new AccessfailedException("Database Access Failed");
		}
	}

	/**
	 * Create the mySql select statement.
	 */
	public Map<String,Employee> getAllEmployees() {
		final Map<String,Employee> employeeMap = new HashMap<String,Employee>();

		final String query = "select * from employeedetails where isactive = true";

		try (Connection connection = ConnectDataBase.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {

			while (resultSet.next()) {
				final String employeeId = resultSet.getString("employeeId");
				final String employeeName = resultSet.getString("employeeName");
				final String employeePhoneNo = resultSet.getString("employeePhoneNo");
				final String employeeSalary = resultSet.getString("employeeSalary");
				final Date employeeDateOfBirth = resultSet.getDate("employeeDateOfBirth");
				final Employee employee = new Employee(employeeId, employeeName, employeePhoneNo, employeeSalary,
						employeeDateOfBirth);

				employeeMap.put(employee.getEmployeeId(),employee);
			}
			return employeeMap;
		} catch (Exception exception) {
			throw new AccessfailedException("Database Access Failed");
		}
	}
}
