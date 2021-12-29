package io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import activity.Activity;
import course.Course;
import semester.Semester;
import util.SortedList;

public class StudyRecordsWriter {
	
	public static void writeStudyRecordsFile(File file, SortedList<Semester> semesters) {
		
		SortedList<Course> courses;
		SortedList<Activity> activities;
		Activity activity;
		
		try {
			PrintStream fileWriter = new PrintStream(file);
			
			for (int s = 0; s < semesters.size(); s++) {
				
				fileWriter.println("# " + semesters.get(s).getSemesterName());
				courses = semesters.get(s).getCourseList();
				
				for (int c = 0; c < courses.size(); c++) {
					
					fileWriter.println("* " + courses.get(c).getCourseTitle());
					activities = courses.get(c).getActivity();
					
					for (int a = 0; a < activities.size(); a++) {
						
						activity = activities.get(a);
						fileWriter.println("- " + activity.getDate() + ", " + activity.getTitle() + ", " + activity.getTimeString());
					}
				}
			}
			fileWriter.close();
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
		
	}

}
