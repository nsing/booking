package com.exercise;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.junit.Test;

/**
 * Test for BookingServiceImpl
 *
 */
public class BoardRoomServiceTest {

	@Test
	public void processBookingRequestsTest() {

		//Set URL
		URL url = BoardRoomServiceTest.class.getResource(
				"/com/exercise/input1.txt");
		//Create BoardRoomServiceImpl object
        BoardRoomService boardRoomService = new BoardRoomServiceImpl();
        //Process booking requests
        BoardRoom boardRoom = boardRoomService.processBookingRequests(
        		new File(url.getFile()));
        //Define expected output
        String output = "2011-03-21\n"
        				+"09:00 11:00 EMP002\n"
        				+"2011-03-22\n"
        				+"14:00 16:00 EMP003\n"
        				+"16:00 17:00 EMP004\n";
        //Test result
        assertTrue(output.equals(CommonUtil.display(boardRoom.getBookings())));
        //Display bookings
        System.out.println(CommonUtil.display(boardRoom.getBookings()));

	}

}
