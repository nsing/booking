package com.exercise;

import java.util.Comparator;
import java.util.Date;

/**
 * This class represents a booking
 *
 */
public class Booking {

	/*
	 * Comparator to sort on the basis of booking request date
	 */
	public static Comparator<Booking> RequestDateComparator = new Comparator<Booking>() {
		public int compare(Booking booking1, Booking booking2) {
			return booking1.getRequestDate().compareTo(booking2.getRequestDate());
		}
	};

	/*
	 * Comparator to sort on the basis of meeting start date
	 */
	public static Comparator<Booking> MeetingStartComparator = new Comparator<Booking>() {
		public int compare(Booking booking1, Booking booking2) {
			return booking1.getMeetingStart().compareTo(booking2.getMeetingStart());
		}
	};

	/*
	 * Submission time of booking request
	 */
	private Date requestDate;

	/*
	 * Start time of booking
	 */
	private Date meetingStart;

	/*
	 * End time of booking
	 */
	private Date meetingEnd;

	/*
	 * Employee id who did booking
	 */
	private String employeeId;

	/**
	 * Booking constructor
	 *
	 * @param requestDate
	 * @param meetingStart
	 * @param meetingEnd
	 * @param employeeId
	 */
	public Booking(Date requestDate, Date meetingStart, Date meetingEnd,
			String employeeId) {
		this.requestDate = requestDate;
		this.meetingStart = meetingStart;
		this.meetingEnd = meetingEnd;
		this.employeeId = employeeId;
	}

	/**
	 * Returns booking request date
	 *
	 * @return
	 */
	public Date getRequestDate() {
		return requestDate;
	}

	/**
	 * Returns meeting start date
	 *
	 * @return
	 */
	public Date getMeetingStart() {
		return meetingStart;
	}

	/**
	 * Returns meeting end date
	 *
	 * @return
	 */
	public Date getMeetingEnd() {
		return meetingEnd;
	}

	/**
	 * Returns employee id
	 *
	 * @return
	 */
	public String getEmployeeId() {
		return employeeId;
	}

}