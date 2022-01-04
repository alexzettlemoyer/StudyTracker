package semester;

import java.time.LocalDateTime;
import java.util.Objects;

import activity.Activity;
import course.Course;
import util.SortedList;


public class Semester implements Comparable<Semester> {
	
	String name;
	
	SortedList<Course> courses;
	
	public Semester(String name) {
		setSemesterName(name);
		courses = new SortedList<Course>();
	}
	
	public void setSemesterName(String name) {
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException("Empty Name");
		}
		this.name = name;
	}
	
	public void addCourse(String courseName) {
		Course newCourse = new Course(courseName);
		addCourse(newCourse);
	}
	
	public void addCourse(Course course) {
		if (course == null) {
			throw new IllegalArgumentException("Cannot add null Course.");
		}
		
		// check that it's not a duplicate
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getCourseTitle().equals(course.getCourseTitle())) {
				throw new IllegalArgumentException("Course with this name already exists.");
			}
		}
		courses.add(course);
	}
	
	public void removeCourse(Course course) {
		
		if (courses.contains(course)) {
			courses.remove(course);
		}
		else {
			throw new IllegalArgumentException("Course does not exist in " + name);
		}
	}
	
	public Course getCourse(String title) {
		
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getCourseTitle().equals(title)) {
				return courses.get(i);
			}
		}
		return null;
	}
	
	public String getSemesterName() {
		return name;
	}
	
	public SortedList<Course> getCourseList() {
		return courses;
	}
	
	public double getTotalMinutes() {
		double totalMinutes = 0.0;
		
		for (int i = 0; i < courses.size(); i++) {
			totalMinutes += courses.get(i).getTotalMinutes();
		}
		return totalMinutes;
	}
	
	public double getDayMinutes(int period, LocalDateTime dateTime) {
		double totalMinutes = 0.0;
		
		for (int i = 0; i < courses.size(); i++) {
			totalMinutes += courses.get(i).getDayPeriodMinutes(period, dateTime);
		}
		return totalMinutes;
		
	}
	
	public String getTotalTime() {
		double totalMinutes = getTotalMinutes();
		return Activity.getTime(totalMinutes);
	}
	
	/**
	 * return String array of all Courses in currentSemester
	 * @return
	 */
	public String[] getCourseNames() {
		String[] courseNames = new String[courses.size()];
		
		for (int i = 0; i < courses.size(); i++) {
			courseNames[i] = courses.get(i).getCourseTitle();
		}
 		return courseNames;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Semester other = (Semester) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public int compareTo(Semester o) {
		return getSemesterName().toLowerCase().compareTo(o.getSemesterName().toLowerCase());
	}
}
