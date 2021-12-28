package tracker;

import java.time.LocalDateTime;

import activity.Activity;
import course.Course;
import semester.Semester;
import util.SortedList;

public class Tracker {
	
	SortedList<Semester> semesters;
	
	Semester currentSemester;
	Course currentCourse;
	
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
	
	/**
	 * SET CURRENTSEMESTER
	 * @param String semesterName
	 */
	public void setCurrentSemester(String semesterName) {
		if (semesterName == null) {
			currentSemester = null;
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
		
		// check that it's not a duplicate
		for (int i = 0; i < semesters.size(); i++) {
			if (semesters.get(i).getSemesterName().equals(semesterName)) {
				throw new IllegalArgumentException("Semester with this name already exists.");
			}
		}
		semesters.add(newSemester);
		setCurrentSemester(newSemester.getSemesterName());
	}
	
	/**
	 * ADD COURSE to current Semester
	 * @param String courseName
	 */
	public void addCourseToSemester(String courseName) {
		currentSemester.addCourse(courseName);
		setCurrentCourse(courseName);
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
	}
	
	/**
	 * EDIT SEMESTER
	 * @param String newSemesterName
	 */
	public void editSemester(String newSemesterName) {
		if (currentSemester == null) {
			throw new IllegalArgumentException("Cannot edit null semester.");
		}
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
			return null;
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
		double dayMinutes = currentCourse.getDayMinutes(dayPeriod, dateTime);
		return Activity.getTime(dayMinutes);
	}
	
	public void reset() {
		semesters = new SortedList<>();
		currentSemester = null;
		currentCourse = null;
	}

}
