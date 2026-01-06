package com.dyashin.javacollections.validation;

import java.time.LocalDate;

import com.dyashin.javacollections.exception.EmployeeException;

public class EmployeeValidation {

	public static void validateString(String string) throws EmployeeException {
		if (!string.matches("^[A-Za-z ]+$")) {
			throw new EmployeeException("Must contain only letters and spaces");
		}
	}
	
	public static void validateClose(String close) throws EmployeeException {
		if(!(close.equalsIgnoreCase("y")|| close.equalsIgnoreCase("n"))){
			throw new EmployeeException("Type y or n only");
		}
	}
	
	public static void validateDate(LocalDate date) throws EmployeeException {
		
		if(date == null) {
			throw new EmployeeException("Date cannot be null");
		}
		
	}
	
	public static void validateNumber(int number) throws EmployeeException {
		if(number<=0) {
			throw new EmployeeException(number+" must be positive");
		}
	}
}
