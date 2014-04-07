package com.exercise;

import java.util.List;

/**
 * This is an interface declaring booking related methods
 *
 */
public interface Bookable {

	/**
	 * Returns existing bookings
	 *
	 * @return
	 */
	List<Booking> getBookings();

	/**
	 * Adds a new booking
	 *
	 * @param booking
	 * @return
	 */
	boolean addBooking(Booking booking);

}
