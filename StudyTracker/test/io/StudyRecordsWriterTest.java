package io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import course.Course;
import semester.Semester;
import util.SortedList;

class StudyRecordsWriterTest {

	@Test
	void testWriteStudyRecordsFile() {
		
		SortedList<Semester> semesters = new SortedList<Semester>();
		
		Semester s1 = new Semester("Fall 2021");
		
		Course c1 = new Course("MA242");
		c1.addActivity("12/27/21", "361", "project1", "2:00:30:00");
		c1.addActivity("12/26/21", "360", "hw3.5", "0:45:00:00");
		
		Course c2 = new Course("PY208");
		c2.addActivity("12/28/21", "362", "hw7", "1:00:30:00");
		c2.addActivity("12/14/21", "348", "hw5", "0:14:00:00");
		
		s1.addCourse(c1);
		s1.addCourse(c2);
		
		Semester s2 = new Semester("Spring 2021");
		
		Course c3 = new Course("HON395");
		c3.addActivity("5/14/21", "134", "final paper", "5:12:22:20");
		c3.addActivity("5/10/21", "130", "draft paper", "2:32:06:00");
		c3.addActivity("5/01/21", "121", "discussion post", "0:10:18:00");
		
		Course c4 = new Course("PY205");
		c4.addActivity("5/12/21", "132", "exam studying", "6:02:48:00");
		
		s2.addCourse(c3);
		s2.addCourse(c4);
		
		semesters.add(s1);
		semesters.add(s2);
		
		File exportFile = new File("test-files/actual_out.txt");
		StudyRecordsWriter.writeStudyRecordsFile(exportFile, semesters);
		
		checkFiles("test-files/tracker_records.txt", "test-files/actual_out.txt");
		
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
			 Scanner actScanner = new Scanner(new File(actFile));) {
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
