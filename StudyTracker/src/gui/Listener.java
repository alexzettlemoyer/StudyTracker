package gui;

import tracker.Tracker;

public class Listener {
	
	TrackerPanel myTracker;
	ActivityPanel myActivity;
	DataPanel myData;
	Tracker tracker;
	
	private static Listener instance;
	
	public static Listener getInstance() {
		if (instance == null) {
			instance = new Listener();
		}
		return instance;
	}
	
	private Listener() {
		tracker = Tracker.getInstance();
	}
	
	public void setTracker(TrackerPanel tracker) {
		myTracker = tracker;
	}
	
	public void setActivity(ActivityPanel activity) {
		myActivity = activity;
	}
	
	public void setData(DataPanel data) {
		myData = data;
	}
	
	public void changeSemester() {
		myActivity.update();
		myData.update();
	}
	
	public void changeCourse() {
		myActivity.update();
		myData.update();
	}
	
	public void saveActivity(String date, String dayOfYear, String title, String timeElapsed) {
		myActivity.addActivity(date, dayOfYear, title, timeElapsed);
		myData.addActivity();
	}
	
	public void removeActivity() {
		myData.removeActivity();
	}

}
