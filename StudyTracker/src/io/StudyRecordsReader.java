package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import activity.Activity;
import course.Course;
import semester.Semester;
import util.SortedList;

public class StudyRecordsReader {
	
	public static SortedList<Semester> readStudyRecords(File file) {
		
		SortedList<Semester> semesters = new SortedList<Semester>();
		String semesterData = new String();
		
		try {
			Scanner fileReader = new Scanner(new FileInputStream(file));
			
			while (fileReader.hasNext()) {
				fileReader.reset();
				
					// check that first character matches that of a valid semester
				if (fileReader.next().charAt(0) == '#') {
					
						// semester token
					fileReader.useDelimiter("\\r?\\n?[#]");
					semesterData = fileReader.next();
					semesters.add(processSemester(semesterData));
				}
				else {
					throw new IllegalArgumentException("Invalid file format.");
				}
			}
			fileReader.close();
		}
		catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		return semesters;
	}
	
	private static Semester processSemester(String semesterData) {
		
		
		Scanner scan = new Scanner(semesterData);
		Semester semester = null;
		String semesterName = new String();
		String courseData = new String();
		
		try {
			semesterName = scan.nextLine().trim();
			semester = new Semester(semesterName);
			
			scan.useDelimiter("\\r?\\n?[*]");
			
			while(scan.hasNext()) {
				
				scan.reset();
				
				if (scan.next().charAt(0) == '*') {
					// course token
					scan.useDelimiter("\\r?\\n?[*]");
					
					courseData = scan.next();
					Course course = processCourse(courseData);
					if (course != null) {
						semester.addCourse(course);
					}
				}
			}
			scan.close();
		}
		catch (IllegalArgumentException e) {
			// do nothing, invalid semesters are not loaded
		}
		
		return semester;
	}
	
	private static Course processCourse(String courseData) {
				
		Scanner scan = new Scanner(courseData);
		Scanner activityScanner = null;
		Course course = null;
		Activity activity = null;
		
		String courseTitle = new String();
		String activityInput = new String();
		
		String date = new String();
		String title = new String();
		String time = new String();
		
		try {
			courseTitle = scan.nextLine().trim();
			course = new Course(courseTitle);
			
			scan.useDelimiter("\\r?\\n?[-]");
			
			while (scan.hasNextLine()) {
				scan.reset();
				
				if (scan.next().charAt(0) == '-') {
					
					scan.useDelimiter("\\r?\\n?[-]");
					
					activityInput = scan.next();
					
					if (!activityInput.contains(",")) {
						activity = null;
						break;
					}
					
					activityScanner = new Scanner(activityInput);
					activityScanner.useDelimiter(",");
					
					date = activityScanner.next().trim();
					title = activityScanner.next().trim();
					time = activityScanner.next().trim();
					
					activity = new Activity(date, null, title, time);
					
					if (activity != null) {
						course.addActivity(activity);
					}
				}
			}
			scan.close();
		}
		catch (Exception e) {
			// do nothing, invalid courses are not loaded
		}
		
		return course;
	}

}
