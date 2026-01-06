package com.dyashin.javacollections.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtil {

	public static boolean isMondayOrFriday(LocalDate date) {
		DayOfWeek day = date.getDayOfWeek();
		if (day == DayOfWeek.MONDAY || day == DayOfWeek.FRIDAY) {
			return true;
		}
		return false;
	}
}
