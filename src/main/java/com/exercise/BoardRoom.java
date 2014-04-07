package com.exercise;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Collections;

/**
 * This class represent Board Room
 *
 */
public class BoardRoom implements Bookable {

	/*
	 * Board room start time
	 */
	private Date startTime;

	/*
	 * Board room end time
	 */
	private Date endTime;

	/*
	 * Board room bookings
	 */
	private List<Booking> bookings;

	/*
	 * Flag indicating whether bookings are sorted or not
	 */
	private boolean bookingsSorted;

	/**
	 * Board room constructor
	 *
	 * @param startTime
	 * @param endTime
	 */
	public BoardRoom(Date startTime, Date endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.bookings = new ArrayList<Booking>();
		this.bookingsSorted = false;
	}

	/**
	 * Returns board room start time
	 *
	 * @return
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * Returns board room end time
	 *
	 * @return
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * Implementation of a method of Bookable interface to return board room bookings
	 */
	public List<Booking> getBookings() {
		if(!bookingsSorted) { //Sort bookings if they are not sorted
			Collections.sort(bookings, Booking.MeetingStartComparator);
			bookingsSorted = true; //Set sorted flag as true
		}
		//Return unmodifiable list of bookings
		return Collections.unmodifiableList(bookings);
	}

	/**
	 * Implementation of a method of Bookable interface add a board room booking
	 *
	 * Constraints
	 * a. No part of a meeting may fall outside office hours.
	 * b. Meetings may not overlap.
	 */
	public boolean addBooking(Booking newBooking) {
		boolean bookingValid = true; //Assuming that booking is valid
		if (CommonUtil.meetingOutsideRange(startTime, endTime,
				newBooking.getMeetingStart(),
				newBooking.getMeetingEnd()) ) { //Meeting outside office hours
			bookingValid = false; //Booking is invalid
		}
		else {
			for(Booking booking : bookings) {
				if (CommonUtil.meetingsOverlap(booking.getMeetingStart(),
						booking.getMeetingEnd(), newBooking.getMeetingStart(),
						newBooking.getMeetingEnd()) ) { //Meetings overlap
					bookingValid = false; //Booking is invalid
					break; //Come out of loop as booking is invalid
				}
			}
		}
		if(bookingValid) { //Booking is valid
			bookings.add(newBooking); //Add booking to the list
			bookingsSorted = false; //Bookings are not sorted
		}
		return bookingValid; //Return booking status
	}


}
