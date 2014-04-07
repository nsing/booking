package com.exercise;

import java.io.File;

/**
 * Board room service interface
 *
 */
public interface BoardRoomService {

	/**
	 * Processes booking requests given in a file and returns BoardRoom object
	 *
	 * @param file
	 * @return
	 */
	BoardRoom processBookingRequests(File file);

}
