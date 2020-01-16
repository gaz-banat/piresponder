package net.gaz;

import java.time.LocalDate;

public class Sprint {
	
	private LocalDate startDate;
	private LocalDate endDate;
	private float sprintNumber;
	//private boolean isActive;
	
	public Sprint(float sprintNumber, LocalDate startDate, LocalDate endDate) {
		this.sprintNumber = sprintNumber;
		this.startDate = startDate;
		this.endDate = endDate;
		//this.isActive = isActive;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public float getSprintNumber() {
		return sprintNumber;
	}
	
	/*
	 * public boolean getIsActive() { return isActive; }
	 */
}
