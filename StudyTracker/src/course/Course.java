package course;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import activity.Activity;
import util.SortedList;

public class Course implements Comparable<Course> {
	
	private String title;
	
	private SortedList<Activity> activity;
	
	private LocalDateTime now;
	private int dayOfYear;
	private DateTimeFormatter dtf;
	
	private final String DAY_OF_YEAR = "D";
	
	public Course(String title) {
		setTitle(title);
		activity = new SortedList<>();
		dtf = DateTimeFormatter.ofPattern(DAY_OF_YEAR);
	}
	
	public void setTitle(String title) {
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Empty title");
		}
		this.title = title;
	}
	
	public void addActivity(String date, String dayOfYear, String title, String time) {
		activity.add(new Activity(date, dayOfYear, title, time));
	}
	
	public void deleteActivity(int index) {
		if (index < 0 || index >= activity.size()) {
			throw new ArrayIndexOutOfBoundsException();
		}
		activity.remove(index);
	}
	
	public void removeActivity(String title) {
		for (int i = 0; i < activity.size(); i++) {
			if (activity.get(i).getTitle().equals(title)) {
				activity.remove(i);
				break;
			}
		}
	}
	
	public SortedList<Activity> getActivity() {
		return activity;
	}
	
	public String[][] getActivityDisplay() {
		String[][] activityDisplay = new String[activity.size()][3];
		
		for (int i = 0; i < activity.size(); i++) {
			activityDisplay[i][0] = activity.get(i).getDate();
			activityDisplay[i][1] = activity.get(i).getTitle();
			activityDisplay[i][2] = activity.get(i).getTimeString();
		}
		
		return activityDisplay;
	}
	
	public double getTotalMinutes() {
	
		double totalMinutes = 0.0;
		for (int i = 0; i < activity.size(); i++) {
			totalMinutes += activity.get(i).getTotalMinutes();
		}
		totalMinutes = Math.round(totalMinutes * 100.0) / 100.0;
		return totalMinutes;
	}
	
	public String getTotalTime() {
		
		double totalMinutes = getTotalMinutes();
		String totalTime = Activity.getTime(totalMinutes);
		
		return totalTime;
	}
	
	/**
	 * GET DAY MINUTES
	 * @param dayPeriod: period of days to check
	 * 30 - last 30 days
	 * 14 - last 14 days
	 * 7 - last 7 days
	 * 0 - today
	 * @param localDateTime an optional parameter
	 * if null, use date today, otherwise use value
	 * @return total number of minutes of activity in given period
	 */
	public double getDayMinutes(int dayPeriod, LocalDateTime localDateTime) {
		
		double totalMinutes = 0.0;
		boolean interYear = false;
	
		if (localDateTime == null) {
			now = LocalDateTime.now();
		}
		else {
			now = localDateTime;
		}
		dayOfYear = dayOfYear(now);
 		
			// lowerBound date: current day of year minus period
		LocalDateTime lowerBoundDateTime = now.minus(dayPeriod, ChronoUnit.DAYS);
		int lowerBoundDay = dayOfYear(lowerBoundDateTime);
				
			// start at day today
		int currentDay = dayOfYear;
		
		if (currentDay < lowerBoundDay) {
			lowerBoundDay = -lowerBoundDay;
			//interYear = true;
		}

			// go through each activity
		for (int i = 0; i < activity.size(); i++) {
				// if we've reached lowerBoundDay, stop loop
			if (currentDay < lowerBoundDay) {
				i = activity.size();
			}
				// if day of activity and currentDay match, add minutes
			else if (activity.get(i).getDayOfYear() == currentDay || activity.get(i).getDayOfYear() == -currentDay) {
				totalMinutes += activity.get(i).getTotalMinutes();
			}
				// if day of activity is less than currentDay, move to next currentDay and rechecks activity
			else if (activity.get(i).getDayOfYear() < currentDay || -activity.get(i).getDayOfYear() < currentDay) {
				now = now.minus(1, ChronoUnit.DAYS);
				if (interYear) {
					currentDay = -dayOfYear(now);
				}
				else {
					currentDay = dayOfYear(now);
				}
				i --; 
			}
		}
		
		return totalMinutes;
	}
	
	private int dayOfYear(LocalDateTime dateTime) {
		return Integer.parseInt(dateTime.format(dtf));
	}
	
	public String getCourseTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return Objects.equals(title, other.title);
	}

	@Override
	public int compareTo(Course o) {
		return getCourseTitle().toLowerCase().compareTo(o.getCourseTitle().toLowerCase());
	}

}
