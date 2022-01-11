package tracker;

import java.io.File;
import java.time.LocalDateTime;

import activity.Activity;
import course.Course;
import io.StudyRecordsReader;
import io.StudyRecordsWriter;
import semester.Semester;
import util.SortedList;

/**
 * Tracker !!!
 * Directly interact with the GUI
 * the Controller portion of the MVC
 * background operations of the Program
 * 
 * Uses the Singleton pattern
 * 
 * @author alexzettlemoyer
 *
 */
public class Tracker {
	
	SortedList<Semester> semesters;
	
	Semester currentSemester;
	Course currentCourse;
	
	boolean isChanged;
	
	private static Tracker instance;
	
	public static Tracker getInstance() {
		if (instance == null) {
			instance = new Tracker();
		}
		return instance;
	}
	
	/**
	 * CONSTUCTOR 
	 */
	private Tracker() {
		reset();
	}
	
	public void loadRecords(File file) {
		SortedList<Semester> loadedSemesters = StudyRecordsReader.readStudyRecords(file);
		reset();
		
		for (int i = 0; i < loadedSemesters.size(); i++) {
			try {
				addSemester(loadedSemesters.get(i));
			}
			catch (Exception e) {
				// do nothing - was a duplicate semester (doesn't add)
			}
		}
		
		if (semesters.size() > 0) {
			setCurrentSemester(semesters.get(0).getSemesterName());
			
			if (semesters.get(0).getCourseList().size() > 0) {
				setCurrentCourse(semesters.get(0).getCourseList().get(0).getCourseTitle());
			}
		}
		
		if (semesters.size() == loadedSemesters.size()) {
			isChanged = false;
		}
		else {
			isChanged = true;
		}
	}
	
	public void saveRecords(File file) {
		StudyRecordsWriter.writeStudyRecordsFile(file, semesters);
		isChanged = false;
	}
	
	/**
	 * SET CURRENTSEMESTER
	 * @param String semesterName
	 */
	public void setCurrentSemester(String semesterName) {
		if (semesterName == null) {
			currentSemester = null;
			setCurrentCourse(null);
		}
		
		for (int i = 0; i < semesters.size(); i++) {
			if (semesters.get(i).getSemesterName().equals(semesterName)) {
				currentSemester = semesters.get(i);
				if (currentSemester.getCourseList().size() != 0) {
					setCurrentCourse(currentSemester.getCourseList().get(0).getCourseTitle());
				}
				else {
					setCurrentCourse(null);
				}
			}
		}
	}
		
	/**
	 * SET CURRENTCOURSE
	 * @param String courseName
	 */
	public void setCurrentCourse(String courseName) {
		if (courseName == null) {
			currentCourse = null;
		}
		else {
			Course current = currentSemester.getCourse(courseName);
			if (current == null) {
				throw new IllegalArgumentException("Course does not exist in " + currentSemester.getSemesterName());
			}
			currentCourse = current;
		}
	}
	
	/**
	 * ADD SEMESTER
	 * @param String semesterName
	 */
	public void addSemester(String semesterName) {
		Semester newSemester = new Semester(semesterName);
		addSemester(newSemester);
	}
	
	public void addSemester(Semester newSemester) {
		
		// check that it's not a duplicate
		for (int i = 0; i < semesters.size(); i++) {
			if (semesters.get(i).getSemesterName().equals(newSemester.getSemesterName())) {
				throw new IllegalArgumentException("Semester with this name already exists.");
			}
		}
		semesters.add(newSemester);
		setCurrentSemester(newSemester.getSemesterName());
		isChanged = true;
	}
	
	/**
	 * ADD COURSE to current Semester
	 * @param String courseName
	 */
	public void addCourseToSemester(String courseName) {
		currentSemester.addCourse(courseName);
		setCurrentCourse(courseName);
		isChanged = true;
	}
	
	/**
	 * REMOVE SEMESTER
	 * @param Semester object to remove
	 */
	public void removeSemester(Semester semester) {
		if (currentSemester == null) {
			throw new IllegalArgumentException("Must select a semester to remove.");
		}
		semesters.remove(semester);
		
		if (semesters.size() != 0) {
			setCurrentSemester(semesters.get(0).getSemesterName());
		}
		else {
			setCurrentSemester(null);
		}
		isChanged = true;
	}

	/**
	 * REMOVE COURSE from current Semester
	 * @param Course object to remove
	 */
	public void removeCourseFromSemester(Course course) {
		if (currentCourse == null) {
			throw new IllegalArgumentException("Must select a course to remove.");
		}
		currentSemester.removeCourse(course);
		
		if (currentSemester.getCourseList().size() != 0) {
			setCurrentCourse(currentSemester.getCourseList().get(0).getCourseTitle());
		}
		else {
			setCurrentCourse(null);
		}
		isChanged = true;
	}
	
	/**
	 * EDIT SEMESTER
	 * @param String newSemesterName
	 */
	public void editSemester(String newSemesterName) {
		if (currentSemester == null) {
			throw new IllegalArgumentException("Cannot edit null semester.");
		}
		isChanged = true;
		currentSemester.setSemesterName(newSemesterName);
	}
	
	/**
	 * EDIT COURSE
	 * @param String newCourseName
	 */
	public void editCourse(String newCourseName) {
		if (currentCourse == null) {
			throw new IllegalArgumentException("Cannot edit a null course.");
		}
		isChanged = true;
		currentCourse.setTitle(newCourseName);
	} 
	
	/**
	 * SAVE ACTIVITY
	 * @param String date
	 * @param String title
	 * @param String time
	 */
	public void saveActivity(String date, String dayOfYear, String title, String time) {
		if (currentCourse == null) {
			throw new IllegalArgumentException("Must select a course to save activity to.");
		}
		isChanged = true;
		currentCourse.addActivity(date, dayOfYear, title, time);
	}
	
	/**
	 * DELETE ACTIVITY
	 * @param index of activity to delete 
	 */
	public void deleteActivity(int index) {
		if (currentCourse == null) {
			throw new IllegalArgumentException("Must select a course to delete activity from.");
		}
		isChanged = true;
		currentCourse.deleteActivity(index);
	}
	
	/**
	 * GET CURRENTSEMESTER
	 * @return current Semester object
	 */
	public Semester getCurrentSemester() {
		return currentSemester;
	}
	
	/**
	 * GET CURRENTCOURSE
	 * @return current Course Object
	 */
	public Course getCurrentCourse() {
		return currentCourse;
	}
	
	public boolean isChanged() {
		return isChanged;
	}
	
	/**
	 * GET COURSES
	 * @return ArrayList<> of Course Objects
	 */
	public SortedList<Course> getCourses() {
		if (currentSemester != null) {
			return currentSemester.getCourseList();
		}
		else {
			return null;
		}
	}
	
	/**
	 * GET SEMESTERS
	 * @return ArrayList<> of Semester Objects
	 */
	public SortedList<Semester> getSemesters() {
		return semesters;
	}
	
	/**
	 * GET SEMESTERNAMES 
	 * @return String[] of Semester Names
	 */
	public String[] getSemesterNames() {
		String[] semesterNames = new String[semesters.size()];
		
		for (int i = 0; i < semesters.size(); i++) {
			semesterNames[i] = semesters.get(i).getSemesterName();
		}
		return semesterNames;
	}
	
	/**
	 * GET ACTIVITY
	 * @return String[][] of CurrentCourse's activity log
	 */
	public String[][] getActivity() {
		if (currentCourse == null) {
			return new String[0][0];
		}		
		return currentCourse.getActivityDisplay();
	}
	
	/**
	 * GET TOTAL TIME
	 * @return String total Time between all semesters
	 */
	public String getTotalTime() {
		String totalTime = "0:00.0";
		double totalMinutes = 0.0;
		
		for (int i = 0; i < semesters.size(); i++) {
			totalMinutes += semesters.get(i).getTotalMinutes();
		}
		totalTime = Activity.getTime(totalMinutes);	
		return totalTime;
	}
	
	
	/**
	 * GET TOTAL SEMESTER TIME
	 * @return String total Time for CurrentSemester
	 */
	public String getTotalSemesterTime() {
		if (currentSemester == null) {
			return "0:00.0";
		}
		return currentSemester.getTotalTime();
	}
	
	public String getSemesterDayMinutes(int dayPeriod, LocalDateTime dateTime) {
		if (currentSemester == null) {
			return "0:00.0";
		}
		double dayMinutes = currentSemester.getDayMinutes(dayPeriod, dateTime);
		return Activity.getTime(dayMinutes);
	}
	
	/**
	 * GET TOTAL COURSE TIME
	 * @return String total Time for CurrentCourse
	 */
	public String getTotalCourseTime() {
		if (currentCourse == null) {
			return "0:00.0";
		}
		return currentCourse.getTotalTime();
	}
	
	public String getCourseDayMinutes(int dayPeriod, LocalDateTime dateTime) {
		
		if (currentCourse == null) {
			return "0:00.0";
		}
		double dayMinutes = currentCourse.getDayPeriodMinutes(dayPeriod, dateTime);
		return Activity.getTime(dayMinutes);
	}
	
	public void reset() {
		semesters = new SortedList<>();
		currentSemester = null;
		currentCourse = null;
		isChanged = false;
	}

}
