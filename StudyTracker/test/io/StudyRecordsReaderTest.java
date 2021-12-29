package io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import activity.Activity;
import course.Course;
import semester.Semester;
import util.SortedList;

class StudyRecordsReaderTest {
	
	private final File validTestFile = new File("test-files/tracker_records.txt");

	@Test
	void testReadStudyRecords() {
		
		Semester expectedS;
		Semester actualS;
		
		Course expectedC;
		Course actualC;
		
		Activity expectedA;
		Activity actualA;
		
		SortedList<Semester> expectedSemesters = new SortedList<Semester>();
		
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
		c3.addActivity("5/14/21", "134", "final paper", "5:12:24:20");
		c3.addActivity("5/10/21", "130", "draft paper", "2:32:06:00");
		c3.addActivity("5/01/21", "121", "discussion post", "0:10:18:00");
		
		Course c4 = new Course("PY205");
		c4.addActivity("5/12/21", "132", "exam studying", "6:02:48:00");
		
		s2.addCourse(c3);
		s2.addCourse(c4);
		
		expectedSemesters.add(s1);
		expectedSemesters.add(s2);
		
		SortedList<Semester> actualSemesters = StudyRecordsReader.readStudyRecords(validTestFile);
		
		assertEquals(expectedSemesters.size(), actualSemesters.size());
		
		for (int i = 0; i < actualSemesters.size(); i++) {
			
			expectedS = expectedSemesters.get(i);
			actualS = actualSemesters.get(i);
			
			assertEquals(expectedS.getSemesterName(), actualS.getSemesterName());
			assertEquals(expectedS.getTotalMinutes(), actualS.getTotalMinutes());
			assertEquals(expectedS.getTotalTime(), actualS.getTotalTime());
			assertEquals(expectedS.getCourseList().size(), actualS.getCourseList().size());

			for (int j = 0; j < actualS.getCourseList().size(); j++) {
				
				expectedC = expectedS.getCourseList().get(j);
				actualC = actualS.getCourseList().get(j);
				
				assertEquals(expectedC.getCourseTitle(), actualC.getCourseTitle());
				assertEquals(expectedC.getTotalMinutes(), actualC.getTotalMinutes());
				assertEquals(expectedC.getTotalTime(), actualC.getTotalTime());
				assertEquals(expectedC.getActivity().size(), actualC.getActivity().size());
				
				for (int k = 0; k < actualC.getActivity().size(); k++) {
					
					expectedA = expectedC.getActivity().get(k);
					actualA = actualC.getActivity().get(k);

					assertEquals(expectedA.getDate(), actualA.getDate());
					assertEquals(expectedA.getDayOfYear(), actualA.getDayOfYear());
					assertEquals(expectedA.getTitle(), actualA.getTitle());
					assertEquals(expectedA.getTotalMinutes(), actualA.getTotalMinutes());
					assertEquals(expectedA.getTimeString(), actualA.getTimeString());
				}
			}
			
			
		}
		
	}

}
