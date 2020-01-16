package net.gaz;

import java.time.LocalDate;

public class DateChecker {

	public static boolean checkToday(LocalDate startDate, LocalDate endDate) {
		
		LocalDate dateNow = LocalDate.now();
		
		if( (dateNow.isAfter(startDate) || dateNow.isEqual(startDate)) 
			&& 
			(dateNow.isBefore(endDate)  || dateNow.isEqual(endDate)) ) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkDate(LocalDate aDate, LocalDate startDate, LocalDate endDate) {
		
		if( (aDate.isAfter(startDate) || aDate.isEqual(startDate)) 
			&& 
			(aDate.isBefore(endDate)  || aDate.isEqual(endDate)) ) {
			return true;
		} else {
			return false;
		}
	}
	
}
