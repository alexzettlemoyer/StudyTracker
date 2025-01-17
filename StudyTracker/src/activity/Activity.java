package activity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Activity implements Comparable<Activity>{
	
	double totalMinutes;
	String timeElapsed;
	String title;
	String date;
	int dayOfYear;
	LocalDateTime dateTimeOfActivity;
	
	static NumberFormat nf;
	
	//LocalDateTime now;
	DateTimeFormatter DAY_OF_YEAR = DateTimeFormatter.ofPattern("D");
	
	public Activity(String date, String dayOfYear, String title, String time) {
		
		LocalDateTime now = LocalDateTime.now();
		
		nf = new DecimalFormat("00.0");
		nf.setMaximumIntegerDigits(2);
		nf.setMaximumIntegerDigits(2);
		nf.setMaximumFractionDigits(1);
		nf.setMinimumFractionDigits(1);
		
		if (dayOfYear == null) {	// manually created Activity
			setDateManually(date, now);
			setTitle(title);
			setTimeManually(time);
		}
		else {
			setDate(date);
			setTitle(title);
			setTime(time);
			setDayOfYear(dayOfYear);
		}
	}
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	public static double getTime(String time) {
		
		Scanner scnr = new Scanner(time);
		scnr.useDelimiter(":");
		
		double minutesElapsed;
		
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		
		minutesElapsed = 0;
		
		hours = Integer.parseInt(scnr.next());
		minutes = Integer.parseInt(scnr.next());
		seconds = Integer.parseInt(scnr.next());
				
		minutesElapsed += seconds / (double)60;
		minutesElapsed += minutes;
		minutesElapsed += hours* (double)60;
		scnr.close();
		minutesElapsed = Math.round(minutesElapsed * 100.0) / 100.0;
		return minutesElapsed;
	}
	
	public static String getTime(double time) {
		
		nf = new DecimalFormat("00.0");
		nf.setMaximumIntegerDigits(2);
		nf.setMaximumIntegerDigits(2);
		nf.setMaximumFractionDigits(1);
		nf.setMinimumFractionDigits(1);

		int hours = (int)time / 60;
		double minutes = time % 60;
		
		String timeString = "" + hours + ":" + nf.format(minutes);
		
		return timeString;
	}
	
	private void setDate(String date) {
		if (date == null || date.length() == 0) {
			throw new IllegalArgumentException("Error getting date.");
		}
		
		dateTimeOfActivity = getDateTime(date);
		this.date = date;
	}
	
	private void setDateManually(String date, LocalDateTime now) {
				
		LocalDateTime manualDateTime = getDateTime(date);
		
		if (manualDateTime.isAfter(now)) {
			throw new IllegalArgumentException("Cannot save Activity in future time.");
		}
		
		if (manualDateTime.isBefore(LocalDateTime.of(2021, 1, 1, 9, 30))) {
			throw new IllegalArgumentException("Cannot save Activity before 2021.");
		}
		
		dateTimeOfActivity = manualDateTime;
		this.date = date;
	}
	
	private LocalDateTime getDateTime(String date) {
		
		if (date == null || date.length() == 0) {
			throw new IllegalArgumentException("Invalid date.");
		}		
		
		Scanner scan = new Scanner(date);
		scan.useDelimiter("/");
		
		int month = scan.nextInt();
		int day = scan.nextInt();
		int year = scan.nextInt() + 2000;
		
		scan.close();
		
		if (month > 12 || month <= 0) {
			throw new IllegalArgumentException("Invalid month.");
		}
		if (day > 31 || day <= 0) {
			throw new IllegalArgumentException("Invalid day.");
		}
		
		LocalDateTime dateTime;
		try {
			dateTime = LocalDateTime.of(year, month, day, 9, 30);
			setDayOfYearManually(dateTime);
		} catch (Exception e) {
			throw new IllegalArgumentException("Error getting date.");
		}
		
		return dateTime;
		
	}
	
	private void setDayOfYear(String dayOfYear) {
		if (dayOfYear == null || dayOfYear.length() == 0) {
			throw new IllegalArgumentException("Error getting Day of Year.");
		}
		this.dayOfYear = Integer.parseInt(dayOfYear);
	}
	
	private void setDayOfYearManually(LocalDateTime dateTime) {
		String day = dateTime.format(DAY_OF_YEAR);
		dayOfYear = Integer.parseInt(day);
	}
	
	private void setTitle(String title) {
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Title must be defined.");
		}
		this.title = title;
	}
	
	private void setTime(String timeString) {
		if (timeString == null || timeString.length() == 0) {
			throw new IllegalArgumentException("Error getting time.");
		}
		if (timeString.equals("00:00:00:00")) {
			throw new IllegalArgumentException("Cannot save 0 time.");
		}
		
		totalMinutes = getTime(timeString);
		timeElapsed = getTime(totalMinutes);

	}
	
	private void setTimeManually(String timeString) {
		if (timeString == null || timeString.length() == 0) {
			throw new IllegalArgumentException("Invalid time.");
		}
		if (timeString.equals("0:00.0")) {
			throw new IllegalArgumentException("Cannot save 0 time.");
		}
		
		int hours;
		int min;
		int sec;
		
		Scanner hrScan = new Scanner(timeString);
		Scanner minScan;
		hrScan.useDelimiter(":");
		
		
		try {
			hours = Integer.parseInt(hrScan.next());			
			minScan = new Scanner(hrScan.next());
			hrScan.close();
			
			minScan.useDelimiter("\\.");
			min = Integer.parseInt(minScan.next());
			
			String seconds = minScan.next();
			
			if (seconds.length() == 1) { // 0.5 = 0.50
				seconds = seconds + "0";
			}
			sec = Integer.parseInt(seconds);
			
		} catch (Exception e) {
			hrScan.close();
			throw new IllegalArgumentException("Invalid time format. Must be Hours:Min.Sec");
		}
		
		if (hours < 0 || hours > 10) {
			throw new IllegalArgumentException("Invalid hours. Must be between 0 and 10.");
		}
		if (min >= 60 || min < 0) {
			throw new IllegalArgumentException("Invalid minutes. Must be between 0 and 60.");
		}
		if (sec >= 100 || sec < 0) {
			throw new IllegalArgumentException("Invalid second fraction. Must be between 0 and 100.");
		}
		
		double minutesElapsed = (hours * 60) + min + ((double)sec / 100);
		totalMinutes = Math.round(minutesElapsed * 100.0) / 100.0;
		timeElapsed = getTime(totalMinutes);

	}
	
	
	public String getTitle() {
		return title;
	}
	
	public String getDate() {
		return date;
	}
	
	public int getDayOfYear() {
		return dayOfYear;
	}
	
	public LocalDateTime getDateTimeOfActivity() {
		return dateTimeOfActivity;
	}
	
	public double getTotalMinutes() {
		return totalMinutes;
	}
	
	public String getTimeString() {
		return timeElapsed;
	}
	
	public String toString() {
		return date + "\\t" + title + "\\t" + timeElapsed;
	}

	@Override
	public int compareTo(Activity o) {
		
		if (dateTimeOfActivity.equals(o.getDateTimeOfActivity())) {
			return 0;
		}
		else if (dateTimeOfActivity.isBefore(o.getDateTimeOfActivity())) {
			return 1;
		}
		else {
			return -1;
		}
	}

}
