package com.exercise;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Board room service implementation
 *
 */
public class BoardRoomServiceImpl implements BoardRoomService {


	/**
	 * Processes booking requests given in a file and returns BoardRoom object
	 *
	 * @param file
	 * @return
	 */
	public BoardRoom processBookingRequests(File input) {
		//Create an an array list
		List<Booking> bookingRequests = new ArrayList<Booking>();
		//Extract data from the given file
		BoardRoom boardRoom = CommonUtil.extractData(input, bookingRequests);
		//Sort booking requests
		Collections.sort(bookingRequests, Booking.RequestDateComparator);
		//Add booking
		for(Booking request : bookingRequests) {
			boardRoom.addBooking(request);
		}
		//Returns board room object
		return boardRoom;
	}

	/**
	 * Main method
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		if(0 == args.length) { //No file name supplied
			System.out.println("Input file name not supplied");
	    }
	    else {
	    	//Create File object
            File file = new File(args[0]);
            //Instantiate BoardRoomServiceImpl
			BoardRoomService service = new BoardRoomServiceImpl();
			//Process booking requests
			BoardRoom boardRoom = service.processBookingRequests(file);
			if(null != boardRoom) { //Display bookings
				System.out.print(CommonUtil.display(boardRoom.getBookings()));
			}
	    }
	}

}
