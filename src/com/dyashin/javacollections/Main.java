package com.dyashin.javacollections;

import java.time.LocalDate;
import java.time.Month;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.dyashin.javacollections.exception.EmployeeException;
import com.dyashin.javacollections.model.Employee;
import com.dyashin.javacollections.model.LeaveRecord;
import com.dyashin.javacollections.service.LeaveAnalyzerService;
import com.dyashin.javacollections.validation.EmployeeValidation;

public class Main {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		LeaveAnalyzerService leaveAnalyzerService = new LeaveAnalyzerService();
		boolean exit = false;
		while (!exit) {
			System.out.println("------------------------------------");
			System.out.println("Employee Leave Pattern Analyzer");
			System.out.println("Enter " + "\n 1. Add Employee " + " \n 2. Add leave " + " \n 3. View total leaves "
					+ " \n 4. View frequent long weekend employess" + " \n 5. View monthly leave summary "
					+ " \n 6. View sorted employees by leave count " + " \n 0. Exit(0)");
			System.out.print("Choose option: ");
			int choice = in.nextInt();
			in.nextLine();
			Main.validateNumber(choice);
			switch (choice) {
			case 1: {
				System.out.println("Enter employee id:");
				int id = in.nextInt();
				if (!Main.validateNumber(id)) {
					break;
				}

				in.nextLine();
				System.out.println("Enter employee name:");
				String name = in.nextLine();
				if (!Main.validateString(name)) {
					break;
				}

				leaveAnalyzerService.addEmployee(new Employee(id, name));
				System.out.println("Employee added succeessfully");

				System.out.println("Back to menu y(yes) or n(exit):");
				String close = in.nextLine();
				if (!Main.validateClose(close)) {
					break;
				}
				if (close.equalsIgnoreCase("n")) {
					exit = true;
				}
				break;
			}

			case 2: {
				System.out.println("Enter employee id:");
				int empId = in.nextInt();
				if(!Main.validateNumber(empId)) {
					break;
				}
				in.nextLine();

				Employee employee = leaveAnalyzerService.getEmployeeById(empId);
				if (employee == null) {
					System.out.println("Employee not found");
					break;
				}

				System.out.println("Enter leave date in YYYY-MM-DD format:");
				LocalDate date=null;
				try {
					 date = LocalDate.parse(in.nextLine());
				} catch (Exception e) {
					System.err.println("Invalid date format.Enter in YYYY-MM-DD format only");
					break;
				}
				if(!Main.validateDate(date)) {
					break;
				}

				System.out.println("Enter leave reason:");
				String reason = in.nextLine();
				if(!Main.validateString(reason)) {
					break;
				}

				leaveAnalyzerService.addLeave(employee, new LeaveRecord(date, reason));
				System.out.println("Leave added successfully");

				System.out.println("Back to menu y(yes) or n(exit):");
				String close = in.nextLine();
				if (!Main.validateClose(close)) {
					break;
				}
				if (close.equalsIgnoreCase("n")) {
					exit = true;
				}
				break;
			}

			case 3: {
				Map<Employee, Integer> total = leaveAnalyzerService.totalLeaves();
				for (Map.Entry<Employee, Integer> entry : total.entrySet()) {
					System.out.println(
							"Employee name - " + entry.getKey().getName() + " : Total leaves - " + entry.getValue());
				}

				System.out.println("Back to menu y(yes) or n(exit):");
				String close = in.nextLine();
				if (!Main.validateClose(close)) {
					break;
				}
				if (close.equalsIgnoreCase("n")) {
					exit = true;
				}
				break;
			}

			case 4: {
				System.out.println("Enter the long weekend no. of days count: ");
				int value = in.nextInt();
				in.nextLine();
				if(!Main.validateNumber(value)) {
					break;
				}

				List<Employee> frequent = leaveAnalyzerService.frequentLongWeekendEmployess(value);
				System.out.println("Frequent long weekend employees:");
				for (Employee employee : frequent) {
					System.out.println(employee.getName());
				}

				System.out.println("Back to menu y(yes) or n(exit):");
				String close = in.nextLine();
				if (!Main.validateClose(close)) {
					break;
				}
				if (close.equalsIgnoreCase("n")) {
					exit = true;
				}
				break;
			}

			case 5: {
				Map<Month, Integer> data = leaveAnalyzerService.monthlyLeaveCount();

				for (Map.Entry<Month, Integer> entry : data.entrySet()) {
					System.out.println("Month - " + entry.getKey() + " : Leave count - " + entry.getValue());
				}

				System.out.println("Back to menu y(yes) or n(exit):");
				String close = in.nextLine();
				if (!Main.validateClose(close)) {
					break;
				}
				if (close.equalsIgnoreCase("n")) {
					exit = true;
				}
				break;
			}

			case 6: {
				List<Map.Entry<Employee, Integer>> sortedData = leaveAnalyzerService.sortedLeaveSummary();

				for (Map.Entry<Employee, Integer> entry : sortedData) {
					System.out.println(
							"Employee name - " + entry.getKey().getName() + " : Total leaves - " + entry.getValue());
				}

				System.out.println("Back to menu y(yes) or n(exit):");
				String close = in.nextLine();
				if (!Main.validateClose(close)) {
					break;
				}
				if (close.equalsIgnoreCase("n")) {
					exit = true;
				}
				break;
			}

			case 0: {
				System.out.println("Exited");
				exit=true;
				break;
			}
			default:
				System.err.println("Invalid choice entered");
			}
		}
	}

	public static boolean validateNumber(int num) {
		try {
			EmployeeValidation.validateNumber(num);
			return true;
		} catch (InputMismatchException e) {
			System.err.println("Error: Must contain only numbers!");
			return false;
		} catch (EmployeeException e) {
			System.err.println("Error: " + e.getMessage());
			return false;
		}
	}

	public static boolean validateString(String string) {
		try {
			EmployeeValidation.validateString(string);
			return true;
		} catch (InputMismatchException e) {
			System.err.println("Error: Must contain only letters and spaces!");
			return false;
		} catch (EmployeeException e) {
			System.err.println("Error: " + e.getMessage());
			return false;
		}
	}

	public static boolean validateClose(String close) {
		try {
			EmployeeValidation.validateClose(close);
			return true;
		} catch (EmployeeException e) {
			System.err.println("Error: " + e.getMessage());
			return false;
		}
	}

	public static boolean validateDate(LocalDate date) {
		try {
			EmployeeValidation.validateDate(date);
			return true;
		} catch (EmployeeException e) {
			System.err.println("Error: Enter date in YYYY-MM-DD format only");
			return false;
		}
	}

}
