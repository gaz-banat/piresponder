package net.gaz;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PIResponder {

	private List<ProgramIncrement> piList;
	private DateTimeFormatter dtf ;
	
	public PIResponder() {
		
		piList = new ArrayList<>();
		dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
		
			piList.add(new ProgramIncrement(13, "2019-12-30", "2020-03-29"));
			piList.add(new ProgramIncrement(14, "2020-03-30", "2020-06-28"));
			piList.add(new ProgramIncrement(15, "2020-06-29", "2020-09-27"));
			piList.add(new ProgramIncrement(16, "2020-09-28", "2020-12-27"));
		
	}
	
	public String getAllPIs() {
		
		StringBuilder aString = new StringBuilder();
		for (ProgramIncrement pi: piList) {
			aString.append(pi.getAllPIs());
		}
		return aString.toString();
	}
	
	public static void main(String[] args) {
			
		// setup the Program Increments
		PIResponder pires = new PIResponder();
		
		if (args.length > 0) {
			if (args[0].contentEquals("printpis")) {
					pires.printPIs();
			} else if (args[0].contentEquals("today") )  { 
				pires.howAboutNow();
			} else if (args[0].matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
				pires.howAbout(LocalDate.parse(args[0]));
			} else {
				System.err.println("Unrecognized argument to program. Make sure date is in ISO 8601 format of YYYY-MM-DD");
				System.exit(121);
			}
		} else {
			System.err.println("Usage: java -jar PIResponder.jar <printpis | today | YYYY-MM-DD>");
			System.exit(123);
		}
	
		
	}
	
	
	
	
	public void printPIs() {
		for (ProgramIncrement pi : piList) {
			pi.printPI();
		}
	}
	
	
	public void howAboutNow() {
		LocalDate dateNow = LocalDate.now();

		for (ProgramIncrement pi : piList) {
			if ( DateChecker.checkDate(dateNow, pi.getPIStartDate(), pi.getPIEndDate()) ) {
				for( Sprint s: pi.getSprints() ) {
					if ( DateChecker.checkDate(dateNow, s.getStartDate(), s.getEndDate()) ) {
						System.out.println("Today " + dateNow.format(dtf) + " we are in sprint " + s.getSprintNumber() + 
								" (" + s.getStartDate().format(dtf) + " to " + s.getEndDate().format(dtf) + ")");
					}
				}
				if ( DateChecker.checkDate(dateNow, pi.getBW().getStartDate(), pi.getBW().getEndDate() )) {
					System.out.println("Today " + dateNow.format(dtf) + " we are in Buffer Week of PI " +  pi.getPINumber());
				}
				
			} 
		}
		
	}
	
	public void howAbout(LocalDate aDate) {
	
		for (ProgramIncrement pi : piList) {

			if ( DateChecker.checkDate(aDate, pi.getPIStartDate(), pi.getPIEndDate()) ) {

				for( Sprint s: pi.getSprints() ) {

					if ( DateChecker.checkDate(aDate, s.getStartDate(), s.getEndDate()) ) {
						System.out.println(aDate.format(dtf) + " is in sprint " + s.getSprintNumber() + 
								" (" + s.getStartDate().format(dtf) + " to " + s.getEndDate().format(dtf) + ")");
					}
				}
				
				if ( DateChecker.checkDate(aDate, pi.getBW().getStartDate(), pi.getBW().getEndDate()) ) {
					System.out.println(aDate.format(dtf) + " is in Buffer Week of PI " +  pi.getPINumber());
				}
			} 
		}
	}
	
}
