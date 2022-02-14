package com.employeemanagement.validation;

import java.time.LocalDate;

import com.employeemanagement.dao.EmployeeDao;
import com.employeemanagement.dao.EmployeeDaoImpl;
import com.employeemanagement.exception.InvalidDateException;

public class EmployeeValidation {
	private static final EmployeeDao EMPLOYEE_DAO = new EmployeeDaoImpl();

	public static boolean validateChoice(final String choice) {

		if (!(choice.matches("[aA-dD]")||"YES".equalsIgnoreCase(choice) || "NO".equalsIgnoreCase(choice)
				|| "GOMENU".equalsIgnoreCase(choice) || "EXIT".equalsIgnoreCase(choice))) {
			return false;
		}
		return true;
	}

	public static boolean validateIdPresent(final String employeeId) {

		if (EMPLOYEE_DAO.getAllEmployees().containsKey(employeeId)) {
			return true;
		}
		return false;
	}

	/**
	 * Validate the employeeId.
	 */
	public static boolean validateEmployeeId(final String employeeId) {

		if (employeeId.matches("[0-9]{1,}|[0-9]{1,}[aA-zZ]{1,}|[aA-zZ]{1,}[0-9]{1,}")) {
			return true;
		}
		return false;
	}

	/**
	 * Validate the employeeName.
	 */
	public static boolean validateEmployeeName(final String employeeName) {

		if (employeeName.matches("[a-zA-Z\\s]*")) {
			return true;
		}
		return false;
	}

	public static boolean validateEmployeeSalary(final String employeeSalary) {

		if (employeeSalary.matches("(\\d+\\.\\d+)|(\\d+)")) {
			return true;
		}
		return false;
	}

	/**
	 * Validate the employeePhoneNo.
	 */
	public static boolean validateEmployeePhoneNo(final String employeePhoneNo) {

		if (employeePhoneNo.matches("(0|91)?[6-9][0-9]{9}")) {
			return true;
		}
		return false;
	}

	/**
	 * Validate the employeeDateOfBirth.
	 */
	public static boolean validateEmployeeDateOfBirth(final String employeeDateOfBirth) {

		try {
			final LocalDate date = LocalDate.parse(employeeDateOfBirth);
			final LocalDate todayDate = LocalDate.now();

			if (todayDate.plusDays(1).isAfter(date)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception exception) {
			throw new InvalidDateException("Please Enter Valid DateOfBirth");
		}
	}
}
