package tracker;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrackerTest {
	
	Tracker tracker;
	
	@BeforeEach
	public void setUp() {
		tracker = Tracker.getInstance();
		tracker.reset();
	}

	@Test
	void testAddSemester() {
		tracker.addSemester("2024");
		assertEquals("2024", tracker.getCurrentSemester().getSemesterName());
		
		tracker.addSemester("2023");
		assertEquals("2023", tracker.getCurrentSemester().getSemesterName());

		assertEquals(2, tracker.getSemesterNames().length);
		
		assertThrows(IllegalArgumentException.class,
				() -> tracker.addSemester(""));
	}

	@Test
	void testAddCourseToSemester() {
		tracker.addSemester("2024");
		tracker.addSemester("2023");
		assertEquals("2023", tracker.getCurrentSemester().getSemesterName());
		
		tracker.addCourseToSemester("CSC216");
		assertEquals("CSC216", tracker.getCurrentCourse().getCourseTitle());
		assertEquals(1, tracker.getCourses().size());
		assertEquals("CSC216", tracker.getCourses().get(0).getCourseTitle());
		
		tracker.addCourseToSemester("MA242");
		assertEquals("MA242", tracker.getCurrentCourse().getCourseTitle());
		assertEquals(2, tracker.getCourses().size());
		assertEquals("MA242", tracker.getCourses().get(1).getCourseTitle());
		
	}

	@Test
	void testGetTotalTime() {
		tracker.addSemester("2024");
		tracker.addSemester("2023");
		
		tracker.addCourseToSemester("CSC216");
		tracker.addCourseToSemester("MA242");
		
		assertEquals("0:00.0", tracker.getTotalTime());
		
		tracker.saveActivity("12/11/20", "315", "study", "4:30:00");
		assertEquals("4:30.0", tracker.getTotalTime());
		assertEquals("4:30.0", tracker.getTotalSemesterTime());
		assertEquals("4:30.0", tracker.getTotalCourseTime());
		
		tracker.setCurrentCourse("CSC216");
		tracker.saveActivity("12/11/20", "315", "study", "1:15:00");
		assertEquals("5:45.0", tracker.getTotalTime());
		assertEquals("5:45.0", tracker.getTotalSemesterTime());
		assertEquals("1:15.0", tracker.getTotalCourseTime());
		
		tracker.setCurrentSemester("2024");
		assertEquals("5:45.0", tracker.getTotalTime());
		assertEquals("0:00.0", tracker.getTotalSemesterTime());
	}
	

}
