package com.exercise;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Tests for CommonUtil
 *
 */
public class CommonUtilTest {

	@Test
	public void extractDataTest() {
		//Set URL
		URL url = BoardRoomServiceTest.class.getResource(
				"/com/exercise/input1.txt");
		//Create array list for booking requests
		List<Booking> bookingRequests = new ArrayList<Booking>();
		//Extract data from the given file
		BoardRoom boardRoom = CommonUtil.extractData(new File(url.getFile()),
				bookingRequests);
		//Test result
		assertTrue(5 == bookingRequests.size());
		//TODO more checks
	}

	@Test
	public void meetingOutsideRangeTest() {
		SimpleDateFormat dateTimeFormat =
				new SimpleDateFormat("yyyy-MM-dd HH:mm"); //Date format
		SimpleDateFormat timeFormat =
				new SimpleDateFormat("HHmm"); //Date format
		try {
			//Test
			assertTrue(true == CommonUtil.meetingOutsideRange (timeFormat.parse("0930"),
					timeFormat.parse("1730"),
					dateTimeFormat.parse("2013-12-15 08:30"),
					dateTimeFormat.parse("2013-12-15 18:30")));
		}
		catch(ParseException pe) {
			//Do nothing
		}
	}

	@Test
	public void meetingsOverlapTest() {
		SimpleDateFormat dateTimeFormat =
				new SimpleDateFormat("yyyy-MM-dd HH:mm"); //Date format
		try {
			//Test
			assertTrue(true == CommonUtil.meetingsOverlap (
					dateTimeFormat.parse("2013-12-15 09:30"),
					dateTimeFormat.parse("2013-12-15 11:30"),
					dateTimeFormat.parse("2013-12-15 10:30"),
					dateTimeFormat.parse("2013-12-15 12:30")));
		}
		catch(ParseException pe) {
			//Do nothing
		}

	}

	@Test
	public void displayTest() {
		SimpleDateFormat dateFullTimeFormat =
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //Date format
		SimpleDateFormat dateTimeFormat =
				new SimpleDateFormat("yyyy-MM-dd HH:mm"); //Date format
		List<Booking> bookings = new ArrayList<Booking>();
		try {
			//Create data
			Booking booking = new Booking(
					dateFullTimeFormat.parse("2013-12-14 09:30:15"),
					dateTimeFormat.parse("2013-12-15 09:30"),
					dateTimeFormat.parse("2013-12-15 11:30"),
					"EMP001");
			bookings.add(booking);
			//Define expected result
			String output = "2013-12-15\n"
    				+"09:30 11:30 EMP001\n";
			//Test
			assertTrue(output.equals(CommonUtil.display(bookings)));
		}
		catch(ParseException pe) {
			//Do nothing
		}

	}
}
