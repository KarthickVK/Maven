package com.employeemanagement.service;

import java.util.ArrayList;
import java.util.List;

import com.employeemanagement.dao.EmployeeDao;
import com.employeemanagement.dao.EmployeeDaoImpl;
import com.employeemanagement.exception.IdAlreadyFoundException;
import com.employeemanagement.exception.InvalidIdException;
import com.employeemanagement.model.Employee;

/**
 * EmployeeServiceImplVersion2
 * 
 * @author KarthickV
 */
public class EmployeeServiceImplVersion2 implements EmployeeService {

	private static final EmployeeDao EMPLOYEE_DAO = new EmployeeDaoImpl();
	
	/**
	 * EmployeeDetails are created.
	 */

	public boolean createEmployee(final Employee employee) {

		if (EMPLOYEE_DAO.createEmployee(employee)) {
			return true;
		} else {
			throw new IdAlreadyFoundException("Id Already Found");
		}
	}

	/**
	 * Updated EmployeeDetails.
	 */
	public boolean updateEmployee(final Employee employeeDetails) {

		if (EMPLOYEE_DAO.updateEmployee(employeeDetails)) {
			return true;
		} else {
			throw new InvalidIdException("please Enter the valid Id");
		}
	}

	/**
	 * Delete EmployeeDetails.
	 */
	public boolean deleteEmployee(final String employeeId) {

		if (EMPLOYEE_DAO.deleteEmployee(employeeId)) {
			return true;
		} else {
			throw new InvalidIdException("Please Enter the valid Id");
		}
	}

	/**
	 * Get EmployeeDetails
	 */
	public List<Employee> getAllEmployees() {
		List<Employee> employeeList= new ArrayList<Employee>(EMPLOYEE_DAO.getAllEmployees().values());
		return employeeList;
	}
}
