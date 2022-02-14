package com.employeemanagement.view;

import java.sql.Date;

import org.apache.log4j.Logger;

import com.employeemanagement.controller.EmployeeController;
import com.employeemanagement.main.EmployeeMain;
import com.employeemanagement.model.Employee;
import com.employeemanagement.validation.EmployeeValidation;
import com.employeemanagement.exception.CustomException;
import com.employeemanagement.exception.IdAlreadyFoundException;

/**
 * Represents on EmployeeView. this class is used to collect all the user
 * inputs.
 * 
 * @author KarthickV
 */
public class EmployeeView {

	private static final Logger LOGGER = Logger.getLogger(EmployeeView.class);
	private static final EmployeeController EMPLOYEE_CONTROLLER = new EmployeeController();

	public static void showMenu() {
		String userChoice;
		do {
			LOGGER.info("EmployeeManagement: \na.CREATE \nb.UPDATE \nc.DELETE \nd.SHOW");
			final String choice = EmployeeView.getMenuChoice();

			if ("a".equalsIgnoreCase(choice)) {
				EmployeeView.createEmployee();
			} else if ("b".equalsIgnoreCase(choice)) {
				EmployeeView.updateEmployee();
			} else if ("c".equalsIgnoreCase(choice)) {
				EmployeeView.deleteEmployee();
			} else if ("d".equalsIgnoreCase(choice)) {
				EmployeeView.getAllEmployees();
			}
			LOGGER.info("Do you need to Continue?(Yes,No)");
			userChoice = EmployeeView.getUserChoice();
		} while ("yes".equalsIgnoreCase(userChoice));
	}

	public static String getMenuChoice() {
		LOGGER.info("Enter the choice(a-d):");
		final String choice = EmployeeMain.SCANNER.nextLine().trim();

		if (EmployeeValidation.validateChoice(choice)) {
			return choice;
		} else {
			LOGGER.error("Please Enter Valid Choice:");
			return EmployeeView.getMenuChoice();
		}
	}

	public static String getUserChoice() {
		LOGGER.info("Enter the choice(yes or no):");
		final String choice = EmployeeMain.SCANNER.nextLine().trim();

		if ("no".equalsIgnoreCase(choice)) {
			LOGGER.info("Thank you!!!!");
			System.exit(0);
		}

		if (EmployeeValidation.validateChoice(choice)) {
			return choice;
		} else {
			LOGGER.error("Please Enter Valid Choice:");
			return EmployeeView.getUserChoice();
		}
	}

	/**
	 * Get employeeId to the user.
	 */
	public static String getEmployeeId() {
		LOGGER.info("Enter the employeeId: !!! Do you want to go Mainmenu press goMenu");
		final String employeeId = EmployeeMain.SCANNER.nextLine().trim();

		if ("goMenu".equalsIgnoreCase(employeeId)) {
			EmployeeView.showMenu();
		}

		if (EmployeeValidation.validateEmployeeId(employeeId)) {
			return employeeId;
		} else {
			LOGGER.error("Please Enter Valid Id");
			return EmployeeView.getEmployeeId();
		}
	}

	/**
	 * Get EmployeeName to the user
	 */
	public static String getEmployeeName() {
		LOGGER.info("Enter the employeeName: !!! Do you want to go Mainmenu press goMenu");
		final String employeeName = EmployeeMain.SCANNER.nextLine().trim();

		if ("goMenu".equalsIgnoreCase(employeeName)) {
			EmployeeView.showMenu();
		}

		if (EmployeeValidation.validateEmployeeName(employeeName)) {
			return employeeName;
		} else {
			LOGGER.error("Please Enter Valid EmployeeName");
			return EmployeeView.getEmployeeName();
		}
	}

	/**
	 * Get EmployeePhoneNo to the user.
	 */
	public static String getEmployeePhoneNo() {
		LOGGER.info("Enter the employeePhoneNo: !!! Do you want to go Mainmenu press goMenu");
		final String employeePhoneNo = EmployeeMain.SCANNER.nextLine().trim();

		if ("goMenu".equalsIgnoreCase(employeePhoneNo)) {
			EmployeeView.showMenu();
		}

		if (EmployeeValidation.validateEmployeePhoneNo(employeePhoneNo)) {
			return employeePhoneNo;
		} else {
			LOGGER.error("Please Enter Valid PhoneNo");
			return EmployeeView.getEmployeePhoneNo();
		}
	}

	/**
	 * Get EmployeeSalary to the user.
	 */
	public static String getEmployeeSalary() {
		LOGGER.info("Enter the employeeSalary: !!! Do you want to go Mainmenu press goMenu");
		final String employeeSalary = EmployeeMain.SCANNER.nextLine().trim();

		if ("goMenu".equalsIgnoreCase(employeeSalary)) {
			EmployeeView.showMenu();
		}
		
		if (EmployeeValidation.validateEmployeeSalary(employeeSalary)) {
			return employeeSalary;
		} else {
			LOGGER.error("Please Enter Valid EmployeeSalary");
			return EmployeeView.getEmployeeSalary();
		}
	}

	/**
	 * Get EmployeeDateOfBirth to the user.
	 */
	public static Date getEmployeeDateOfBirth() {
		LOGGER.info("Enter the employeeDateOfBirth(yyyy-MM-dd) !!! Do you want to go Mainmenu press goMenu");
		final String employeeDateOfBirth = EmployeeMain.SCANNER.nextLine().trim();

		if ("goMenu".equalsIgnoreCase(employeeDateOfBirth)) {
			EmployeeView.showMenu();
		}
		boolean isValidEmployeeDateOfBirth = false;

		try {
			 isValidEmployeeDateOfBirth = EmployeeValidation.validateEmployeeDateOfBirth(employeeDateOfBirth);
		} catch (Exception exception) {
			LOGGER.error(exception);
		}

		if (isValidEmployeeDateOfBirth) {
			return Date.valueOf(employeeDateOfBirth);
		} else {
			return EmployeeView.getEmployeeDateOfBirth();
		}
	}

	/**
	 * Collect all the user input to stored the separated variables.
	 */
	public static void createEmployee() {
		final String employeeId = EmployeeView.getEmployeeId();

		if (EmployeeValidation.validateIdPresent(employeeId)) {
			LOGGER.info("EmpoyeeId Already present \n Please Enter Valid EmployeeId");
			EmployeeView.createEmployee();
			EmployeeView.showMenu();
		}

		final String employeeName = EmployeeView.getEmployeeName();
		final String employeePhoneNo = EmployeeView.getEmployeePhoneNo();
		final String salary = EmployeeView.getEmployeeSalary();
		final Date employeeDateOfBirth = EmployeeView.getEmployeeDateOfBirth();
		final Employee employee = new Employee(employeeId, employeeName, employeePhoneNo, salary, employeeDateOfBirth);

		try {
			EMPLOYEE_CONTROLLER.createEmployee(employee);
			LOGGER.info("Data Added Successfully!!!");
		} catch (IdAlreadyFoundException exception) {
			LOGGER.error(exception);
			EmployeeView.createEmployee();
		}
	}

	/**
	 * Update EmployeeDetails.Then user choose the one properties to call the
	 * related method.
	 */
	public static String getUpdateChoice() {
		LOGGER.info("Enter the choice(yes or no or goMenu or Exit):");
		final String choice = EmployeeMain.SCANNER.nextLine().trim();

		if ("goMenu".equalsIgnoreCase(choice)) {
			EmployeeView.showMenu();
		}

		if ("exit".equalsIgnoreCase(choice)) {
			LOGGER.info("Thank you!!!!");
		}

		if (EmployeeValidation.validateChoice(choice)) {
			return choice;
		} else {
			LOGGER.error("Please Enter Valid Choice:");
			return EmployeeView.getUpdateChoice();
		}
	}

	/**
	 * Update EmployeeDetails.
	 */
	public static void updateEmployee() {
		String employeeName = null;
		String employeePhoneNo = null;
		String employeeSalary = null;
		Date employeeDateOfBirth = null;
		final String employeeId = EmployeeView.getEmployeeId();

		if (!EmployeeValidation.validateIdPresent(employeeId)) {
			LOGGER.info("Please Enter Valid EmployeeId");
			EmployeeView.updateEmployee();
			EmployeeView.showMenu();
		}
		final Employee employeeDetails = new Employee();
		LOGGER.info("do you want to change EmployeeName?\t yes or no or goMenu or Exit");

		if ("yes".equalsIgnoreCase(EmployeeView.getUpdateChoice())) {
			employeeName = EmployeeView.getEmployeeName();
		}
		LOGGER.info("do you want to change EmployeePhoneNo?\t yes or no or goMenu or Exit");

		if ("yes".equalsIgnoreCase(EmployeeView.getUpdateChoice())) {
			employeePhoneNo = EmployeeView.getEmployeePhoneNo();
		}
		LOGGER.info("do you want to change EmployeeSalary?\t yes or no or goMenu or Exit");

		if ("yes".equalsIgnoreCase(EmployeeView.getUpdateChoice())) {
			employeeSalary = EmployeeView.getEmployeeSalary();
		}
		LOGGER.info("do you want to change EmployeeDateOfBirth?\t yes or no or goMenu or Exit");

		if ("yes".equalsIgnoreCase(EmployeeView.getUpdateChoice())) {
			employeeDateOfBirth = EmployeeView.getEmployeeDateOfBirth();
		}

		employeeDetails.setEmployeeId(employeeId);
		employeeDetails.setEmployeeName(employeeName);
		employeeDetails.setEmployeePhoneNo(employeePhoneNo);
		employeeDetails.setEmployeeSalary(employeeSalary);
		employeeDetails.setEmployeeDateOfBirth(employeeDateOfBirth);

		try {
			if (EMPLOYEE_CONTROLLER.updateEmployee(employeeDetails)) {
				LOGGER.info("Data Updated Successfully!!!");
			}
		} catch (CustomException exception) {
			LOGGER.error(exception);
			EmployeeView.updateEmployee();
		}
	}

	/**
	 * Delete employeeDetail.
	 */
	public static void deleteEmployee() {
		final String employeeId = EmployeeView.getEmployeeId();

		try {
			EMPLOYEE_CONTROLLER.deleteEmployee(employeeId);
			LOGGER.info("Data Deleted Successfully!!!");
		} catch (CustomException exception) {
			LOGGER.error(exception);
			EmployeeView.deleteEmployee();
		}
	}

	/**
	 * Show all the employeeDetails.
	 */
	public static void getAllEmployees() {
		LOGGER.info(EMPLOYEE_CONTROLLER.showAllEmployees());
	}
}
