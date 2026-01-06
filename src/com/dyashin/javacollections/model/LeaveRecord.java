package com.dyashin.javacollections.model;

import java.time.LocalDate;

public class LeaveRecord {

	private LocalDate leaveDate;

	private String reason;

	public LeaveRecord(LocalDate leaveDate, String reason) {
		super();
		this.leaveDate = leaveDate;
		this.reason = reason;
	}

	public LocalDate getLeaveDate() {
		return leaveDate;
	}

	public String getReason() {
		return reason;
	}

}
