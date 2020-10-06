package net.gaz;

import java.nio.Buffer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import net.gaz.BufferWeek;

public class ProgramIncrement {
	
	private int piNumber;
	private LocalDate piStartDate;
	private LocalDate piEndDate;
	private long piNumDays;
	private final long SPRINT_LENGTH = 14;
	private long numSprints;
	private BufferWeek bw;
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
	private List<Sprint> sprints = new ArrayList<>();
	
	public LocalDate getPIStartDate() { return piStartDate; }
	public LocalDate getPIEndDate() { return piEndDate; }
	public List<Sprint> getSprints() { return sprints; }
	public BufferWeek getBW() { return bw; }
	public int getPINumber() { return piNumber; }
	
	public ProgramIncrement(int number, String startDate, String endDate) {
		// Initialize stuff
		piNumber = number;
		piStartDate = LocalDate.parse(startDate);
		piEndDate = LocalDate.parse(endDate);
		
		// get the number of days in the PI
		piNumDays = ChronoUnit.DAYS.between(piStartDate, piEndDate);
		
		// find out how many sprints based on number of days in PI
		this.numSprints = calcNumSprints(piNumDays);
		
		// Populate the sprints in the List of sprints
		// this will also initialize the bufferweek
		populateSprints();
	}
	
	
	public void printPI() {
		System.out.println("This is PI " + piNumber + ", it has " + piNumDays + 
				" days from " + piStartDate.format(dtf) + " to " + piEndDate.format(dtf) + ". It is made up of " + sprints.size() + " sprints");
		for (Sprint s : sprints) {
			String start = s.getStartDate().getDayOfWeek() + "-" + s.getStartDate().getDayOfMonth() + "-" + s.getStartDate().getMonth();
			String end = s.getEndDate().getDayOfWeek() + "-" + s.getEndDate().getDayOfMonth() + "-" + s.getEndDate().getMonth();
			System.out.printf("%s %.1f %s %-20s %-5s %-20s", "\tSprint ", s.getSprintNumber(), ": ", start, "to", end );
			if (DateChecker.checkToday(s.getStartDate(), s.getEndDate())) {
				System.out.println(" -- TODAY --");
			} else { System.out.println(); }
			
		}
		if(bw != null) {
			String bwStart = bw.getStartDate().getDayOfWeek() + "-" + bw.getStartDate().getDayOfMonth() + "-" + bw.getStartDate().getMonth();
			String bwEnd = bw.getEndDate().getDayOfWeek() + "-" + bw.getEndDate().getDayOfMonth() + "-" + bw.getEndDate().getMonth();
			System.out.printf("%s %-20s %-5s %s%n", "\tBuffer Week  : ", bwStart, "to", bwEnd );	
		}
		
	}
	
	public String getAllPIs() {
		StringBuilder returnString = new StringBuilder("This is PI " + piNumber + ", it has " + piNumDays + 
				" days from " + piStartDate.format(dtf) + " to " + piEndDate.format(dtf) + ". It is made up of " + sprints.size() + " sprints");
		for (Sprint s : sprints) {
			String start = s.getStartDate().getDayOfWeek() + "-" + s.getStartDate().getDayOfMonth() + "-" + s.getStartDate().getMonth();
			String end = s.getEndDate().getDayOfWeek() + "-" + s.getEndDate().getDayOfMonth() + "-" + s.getEndDate().getMonth();
			returnString.append(String.format("%s %.1f %s %-20s %-5s %-20s", "\tSprint ", s.getSprintNumber(), ": ", start, "to", end ));
			if (DateChecker.checkToday(s.getStartDate(), s.getEndDate())) {
				returnString.append(String.format("%s%n", " -- TODAY --"));
			} else { returnString.append("\n"); }
			
		}
		if(bw != null) {
			String bwStart = bw.getStartDate().getDayOfWeek() + "-" + bw.getStartDate().getDayOfMonth() + "-" + bw.getStartDate().getMonth();
			String bwEnd = bw.getEndDate().getDayOfWeek() + "-" + bw.getEndDate().getDayOfMonth() + "-" + bw.getEndDate().getMonth();
			returnString.append(String.format("%s %-20s %-5s %s%n", "\tBuffer Week  : ", bwStart, "to", bwEnd ));	
		}
		
		return returnString.toString();
	}

	
	
	private long calcNumSprints(long numDaysInPI) { 
		long numSprints = 0;
	
		if (numDaysInPI == 0) {
			return numSprints;
		} else if (numDaysInPI > 0 && numDaysInPI <= SPRINT_LENGTH) {
			return numSprints = 1;
		} else {
			numSprints = numDaysInPI / SPRINT_LENGTH;
		}
	
		return numSprints;
	}
	 
	
	private void populateSprints() {
		Sprint aSprint;
		LocalDate sprintStartDate = piStartDate;						// first sprint starts with the PI
		LocalDate sprintEndDate; 
		LocalDate dateNow = LocalDate.now();
		//boolean isActive = false;
		
		for( int i = 1; i <= numSprints; i++) {
			sprintEndDate = sprintStartDate.plusDays(SPRINT_LENGTH-1);	// first sprint ends after 13 days have passed from start date
			float sprintNumber = piNumber + ((float)i)/10;				// sprint number will be PINumber.SprintNumber, e.g. 13.2
			if(sprintEndDate.isAfter(piEndDate)) {						// if sprint is ending after PI has ended
				sprintEndDate = piEndDate;								// finish the sprint on the day the PI ends
				sprints.add(new Sprint(sprintNumber, sprintStartDate, sprintEndDate));
				break;
			} else {													// the sprint is not ending after PI has ended
				if(sprintEndDate.plusDays(7).isEqual(piEndDate)) { 					// need to know if there is only one week left in PI after sprint end
					sprints.add(new Sprint(sprintNumber, sprintStartDate, sprintEndDate));  // add in the final sprint
					bw = new BufferWeek(sprintEndDate.plusDays(1), piEndDate);		// add in the buffer week then
					break;															// and we are done 
				} else {															// does not look like only one week is left in PI after sprint end
					sprints.add(new Sprint(sprintNumber, sprintStartDate, sprintEndDate));
					sprintStartDate = sprintEndDate.plusDays(1);
				}	
			}
				
		}
	
	}
	
}
