package gui;

import tracker.Tracker;

/**
 * LISTENER
 * 
 * Class that interacts with all of the panels
 * allows the panels to interact with one another
 * 
 * When an action is performed in one panel,
 * the listener triggers an action from another panel accordingly
 * 
 * used for adding / removing activities,
 * Changing the current Semester or Course
 * 
 * Uses the singleton pattern so that each panel 
 * can access the same instance of Listener
 * 
 * @author alexzettlemoyer
 *
 */
public class Listener {
	
	/** Instances of the other panels in the GUI */
	TrackerPanel myTracker;
	ActivityPanel myActivity;
	DataPanel myData;
	Tracker tracker;
	
	/** singleton instance of Listener */
	private static Listener instance;
	
	/**
	 * Singleton pattern constructor
	 * @return this instance of listener
	 */
	public static Listener getInstance() {
		if (instance == null) {
			instance = new Listener();
		}
		return instance;
	}
	
	/**
	 * Constructor using singleton Pattern
	 */
	private Listener() {
		tracker = Tracker.getInstance();
	}
	
	/**
	 * sets the TrackerPanel
	 * @param tracker the TrackerPanel
	 */
	public void setTracker(TrackerPanel tracker) {
		myTracker = tracker;
	}
	
	/**
	 * sets the ActivityPanel
	 * @param activity the ActivityPanel
	 */
	public void setActivity(ActivityPanel activity) {
		myActivity = activity;
	}
	
	/**
	 * sets the DataPanel
	 * @param data the DataPanel
	 */
	public void setData(DataPanel data) {
		myData = data;
	}
	
	/**
	 * When the current Semester is changed
	 * Update the ActivityPanel and DataPanel
	 */
	public void changeSemester() {
		myActivity.update();
		myData.update();
	}
	
	/**
	 * When the current Course is changed
	 * Update the ActivityPanel and DataPanel
	 */
	public void changeCourse() {
		myActivity.update();
		myData.update();
	}
	
	/**
	 * When an activity is added,
	 * add it to the ActivityPanel and DataPanel
	 * 
	 * @param date
	 * @param dayOfYear
	 * @param title
	 * @param timeElapsed
	 */
	public void saveActivity(String date, String dayOfYear, String title, String timeElapsed) {
		myActivity.addActivity(date, dayOfYear, title, timeElapsed);
		myData.addActivity();
	}
	
	/**
	 * When an activity is removed,
	 * remove it from the ActivityPanel and DataPanel
	 */
	public void removeActivity() {
		myData.removeActivity();
	}

}
