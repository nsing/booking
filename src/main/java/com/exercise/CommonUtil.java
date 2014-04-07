package com.exercise;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains various utility functions
 *
 */
public class CommonUtil {

	/**
	 * Extracts the data from an input file, populates supplied List and returns
	 * BoardRoom instance
	 *
	 * @param input
	 * @param bookingRequests
	 * @return
	 */
	public static BoardRoom extractData (File input,
			List<Booking> bookingRequests) {

		Calendar calendar = Calendar.getInstance(); //Calendar instance
		SimpleDateFormat dateFullTimeFormat =
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //Date format
		SimpleDateFormat dateTimeFormat =
				new SimpleDateFormat("yyyy-MM-dd HH:mm"); //Date format
		SimpleDateFormat timeFormat =
				new SimpleDateFormat("HHmm"); //Date format
		BoardRoom boardRoom = null; //Board room
		try {
			Scanner sc = new Scanner(input); //Scanner
			int count = 0; //Line counter
			//Flag indicating whether BoardRoom loaded or not
			boolean boardRoomLoaded = false;
			if (sc.hasNextLine()) { //Has next line
				count++; //Increment line count
				String line1 = sc.nextLine(); //Get first line
				String[] times = line1.split(" "); //Get times (start and end)
				Date startTime = null; //Start time
				Date endTime = null; //End time
				try { //Try getting start and end times
					startTime = timeFormat.parse(times[0]); //Start time
					endTime = timeFormat.parse(times[1]); //End time
					//Create BoardRoom
					boardRoom = new BoardRoom(startTime, endTime);
					boardRoomLoaded = true; //BoardRoom loaded
				}
				catch(ParseException pe) {
					//Exception while getting start/ end times
					System.out.println("Error in 1st line, ignoring others");
				}
			}
			if(boardRoomLoaded) {//BoardRoom loaded
				while(sc.hasNextLine()) { //For remaining lines
					count++; //Increment line count
					//Request time and employee line (lines 2, 4, 6, ...)
					String line1 = sc.nextLine();
					if(sc.hasNextLine()) {
						count++; //Increment line count
						//Meeting start time and duration (lines 3, 5, 7, ...)
						String line2 = sc.nextLine();
						String[] fields1 = line1.split(" ");
						String[] fields2 = line2.split(" ");
						if(3 == fields1.length && 3 == fields2.length) {
							//line1 and line2 have 3 fields each
							try {
								//Request date
								Date requestDate = dateFullTimeFormat.parse(
										String.format("%s %s", fields1[0],
												fields1[1]));
								//Employee Id
								String employeeId = fields1[2];
								//Meeting start
								Date meetingStart =
										dateTimeFormat.parse(
												String.format("%s %s",
														fields2[0], fields2[1]));
								//Duration
								byte duration = Byte.parseByte(fields2[2]);
								//Get meeting end time
								calendar.setTime(meetingStart);
								calendar.add(Calendar.HOUR, duration);
								Date meetingEnd = calendar.getTime();
								//Create new booking request
								Booking newBookingRequest =
										new Booking(requestDate, meetingStart,
												meetingEnd, employeeId);
								//Add booking request to the list
								bookingRequests.add(newBookingRequest);
							}
							catch(ParseException pe) {
								//Exception while getting data
								System.out.println(String.format(
										"Error in lines %d / %d, ignoring them"
										, count-1, count));
							}
						}
					}
				}
				System.out.println(String.format("Total lines are %d", count));
				sc.close(); //Close scanner
			}
		}
		catch(FileNotFoundException fnfe) {
			//File not found
			System.out.println("Couldn't locate input file");
		}
		return boardRoom;

	}

	/**
	 * Checks whether meeting is outside board room times or not
	 *
	 * @param startTime
	 * @param endTime
	 * @param meetingStart
	 * @param meetingEnd
	 * @return
	 */
	public static boolean meetingOutsideRange ( Date startTime,
			Date endTime, Date meetingStart, Date meetingEnd ) {

		int startHour; 				//Board room start hour
		int startMinute; 			//Board room start minute
		int endHour; 				//Board room end hour
		int endMinute; 				//Board room end minute
		int meetingStartHour; 		//Meeting start hour
		int meetingStartMinute; 	//Meeting start minute
		int meetingEndHour; 		//Meeting start hour
		int meetingEndMinute; 		//Meeting end minute

		Calendar calendar = Calendar.getInstance(); //Get calendar instance

		//Get board room start hour and minute
		calendar.setTime(startTime);
		startHour = calendar.get(Calendar.HOUR_OF_DAY);
		startMinute = calendar.get(Calendar.MINUTE);

		//Get board room end hour and minute
		calendar.setTime(endTime);
		endHour = calendar.get(Calendar.HOUR_OF_DAY);
		endMinute = calendar.get(Calendar.MINUTE);

		//Get meeting start hour and minute
		calendar.setTime(meetingStart);
		meetingStartHour = calendar.get(Calendar.HOUR_OF_DAY);
		meetingStartMinute = calendar.get(Calendar.MINUTE);

		//Get meeting end hour and minute
		calendar.setTime(meetingEnd);
		meetingEndHour = calendar.get(Calendar.HOUR_OF_DAY);
		meetingEndMinute = calendar.get(Calendar.MINUTE);

		/*
		 Time out of range if any of the following occurs:
		 1. Meeting start hour LT board room start hour
		 2. Meeting end hour GT board room end hour
		 3. Meeting start hour EQ board room start hour
		    AND
		    Meeting start minute LT board room start minute
		 4. Meeting end hour EQ board room end hour
		    AND
		    Meeting end minute GT board room end minute
		 */
		return  ( meetingStartHour < startHour ||
			meetingEndHour > endHour ||
			(meetingStartHour == startHour && meetingStartMinute < startMinute) ||
			(meetingEndHour == endHour && meetingEndMinute > endMinute) );
	}

	/**
	 * Checks whether meetings overlap or not
	 *
	 * @param meetingStart
	 * @param meetingEnd
	 * @param newMeetingStart
	 * @param newMeetingEnd
	 * @return
	 */
	public static boolean meetingsOverlap (Date meetingStart, Date meetingEnd,
			Date newMeetingStart, Date newMeetingEnd) {
		/*
		 Meetings meeting1 and meeting2 overlap if
		 meeting1 start time is less than meeting2 end time
		 AND
		 meeting1 end time is greater than meeting2 start time
		 */
	 	return ( meetingStart.getTime() < newMeetingEnd.getTime() &&
	 			 meetingEnd.getTime() > newMeetingStart.getTime() );
	}


	/**
	 * Returns bookings display string
	 *
	 * @param bookings
	 * @return
	 */
	public static String display(List<Booking> bookings) {
		//Output string
		StringBuilder outputString = new StringBuilder();
		//Date format
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//Time format
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		String strPrevMeetingStart = null; //Previous meeting start date string
		for(Booking booking : bookings) {
			//Meeting start date string
			String strMeetingStart =
					dateFormat.format(booking.getMeetingStart());
			if(null == strPrevMeetingStart ||
					!strPrevMeetingStart.equals(strMeetingStart)) {
				/*Display meeting start date string as previous meeting start
				 date string is null or it is not equal to meeting start date
				 string*/
				outputString.append(strMeetingStart);
				outputString.append("\n");
			}
			//Display meeting details
			outputString.append(String.format("%s %s %s",
					timeFormat.format(booking.getMeetingStart()),
					timeFormat.format(booking.getMeetingEnd()),
					booking.getEmployeeId()));
			outputString.append("\n");
			//Set previous meeting start date string as meeting start string
			strPrevMeetingStart = strMeetingStart;
		}
		return outputString.toString();
	}

}
