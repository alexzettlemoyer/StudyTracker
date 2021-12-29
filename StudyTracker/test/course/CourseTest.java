package course;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class CourseTest {

	@Test
	void testGetTotalMinutes() {
		Course c = new Course("CSC216");
		
		c.addActivity("12/16/21", "315", "homework1", "3:15:42");
		assertEquals(195.7, c.getTotalMinutes());
		
		c.addActivity("12/16/21", "315", "homework2", "1:45:21");
		assertEquals(301.05, c.getTotalMinutes());
		
		c.addActivity("12/16/21", "315", "homework3", "0:37:57");
		assertEquals(339.0, c.getTotalMinutes());
		
		c.removeActivity("homework2");
		assertEquals(233.65, c.getTotalMinutes());
		
	}

	@Test
	void testGetTotalTime() {
		Course c = new Course("CSC216");
		
		c.addActivity("12/16/21", "315", "homework1", "3:15:42");
		assertEquals("3:15.7", c.getTotalTime());
		
		c.addActivity("12/16/21", "315", "homework2", "1:45:21");
		assertEquals("5:01.1", c.getTotalTime());
		
		c.addActivity("12/16/21", "315", "homework3", "0:37:57");
		assertEquals("5:39.0", c.getTotalTime());
		
		c.removeActivity("homework2");
		assertEquals("3:53.7", c.getTotalTime());
		
	}
	
	@Test
	void testGetDayMinutes() {
		
		Course c = new Course("MA242");
		
		// Day of Year: 359
		LocalDateTime dayOfYear = LocalDateTime.parse("2021-12-25T15:54:51.959314");
			
		assertEquals(0.0, c.getTotalMinutes());
		assertEquals(0.0, c.getDayMinutes(1, dayOfYear));
		assertEquals(0.0, c.getDayMinutes(7, dayOfYear));
		assertEquals(0.0, c.getDayMinutes(14, dayOfYear));
		assertEquals(0.0, c.getDayMinutes(30, dayOfYear));
		
		c.addActivity("11/21/21", "325", "hw1", "0:01:00:00");
		assertEquals(1.0, c.getTotalMinutes());
		assertEquals(0.0, c.getDayMinutes(7, dayOfYear));
		assertEquals(0.0, c.getDayMinutes(14, dayOfYear));
		assertEquals(0.0, c.getDayMinutes(30, dayOfYear));
		
		c.addActivity("12/1/21", "334", "hw1", "0:45:45:00"); // 30 days
		assertEquals(46.75, c.getTotalMinutes());
		assertEquals(0.0, c.getDayMinutes(7, dayOfYear));
		assertEquals(0.0, c.getDayMinutes(14, dayOfYear));
		assertEquals(45.75, c.getDayMinutes(30, dayOfYear));
		
		c.addActivity("12/5/21", "339", "hw2", "0:20:45:00");
		assertEquals(67.5, c.getTotalMinutes());
		assertEquals(0.0, c.getDayMinutes(7, dayOfYear));
		assertEquals(0.0, c.getDayMinutes(14, dayOfYear));
		assertEquals(66.5, c.getDayMinutes(30, dayOfYear));
		
		c.addActivity("12/11/21", "345", "hw3", "1:00:00:00"); // 14 days
		assertEquals(127.5, c.getTotalMinutes());
		assertEquals(0.0, c.getDayMinutes(7, dayOfYear));
		assertEquals(60.0, c.getDayMinutes(14, dayOfYear));
		assertEquals(126.5, c.getDayMinutes(30, dayOfYear));
		
		c.addActivity("12/11/21", "345", "hw4", "0:20:30:00");
		assertEquals(148.0, c.getTotalMinutes());
		assertEquals(0.0, c.getDayMinutes(7, dayOfYear));
		assertEquals(80.5, c.getDayMinutes(14, dayOfYear));
		assertEquals(147.0, c.getDayMinutes(30, dayOfYear));
		
		c.addActivity("12/16/21", "350", "hw5", "2:21:30:00");
		assertEquals(289.5, c.getTotalMinutes());
		assertEquals(0.0, c.getDayMinutes(7, dayOfYear));
		assertEquals(222.0, c.getDayMinutes(14, dayOfYear));
		assertEquals(288.5, c.getDayMinutes(30, dayOfYear));
		
		c.addActivity("12/18/21", "352", "hw6", "0:45:45:00"); // 7 days
		assertEquals(335.25, c.getTotalMinutes());
		assertEquals(45.75, c.getDayMinutes(7, dayOfYear));
		assertEquals(267.75, c.getDayMinutes(14, dayOfYear));
		assertEquals(334.25, c.getDayMinutes(30, dayOfYear));
		
		c.addActivity("12/18/21", "352", "hw7", "1:51:30:00");
		assertEquals(446.75, c.getTotalMinutes());
		assertEquals(157.25, c.getDayMinutes(7, dayOfYear));
		assertEquals(379.25, c.getDayMinutes(14, dayOfYear));
		assertEquals(445.75, c.getDayMinutes(30, dayOfYear));
		
		c.addActivity("12/20/21", "354", "hw8", "0:45:45:00");
		assertEquals(492.5, c.getTotalMinutes());
		assertEquals(203.0, c.getDayMinutes(7, dayOfYear));
		assertEquals(425.0, c.getDayMinutes(14, dayOfYear));
		assertEquals(491.5, c.getDayMinutes(30, dayOfYear));
		
		c.addActivity("12/20/21", "354", "hw9", "1:21:30:00");
		assertEquals(574.0, c.getTotalMinutes());
		assertEquals(284.5, c.getDayMinutes(7, dayOfYear));
		assertEquals(506.5, c.getDayMinutes(14, dayOfYear));
		assertEquals(573.0, c.getDayMinutes(30, dayOfYear));

		c.addActivity("12/22/21", "356", "hw10", "3:00:00:00");
		assertEquals(754.0, c.getTotalMinutes());
		assertEquals(464.5, c.getDayMinutes(7, dayOfYear));
		assertEquals(686.5, c.getDayMinutes(14, dayOfYear));
		assertEquals(753.0, c.getDayMinutes(30, dayOfYear));	
		
		c.addActivity("12/25/21", "358", "hw11", "0:30:00:00");
		assertEquals(784.0, c.getTotalMinutes());
		assertEquals(0.0, c.getDayMinutes(0, dayOfYear));
		assertEquals(494.5, c.getDayMinutes(7, dayOfYear));
		assertEquals(716.5, c.getDayMinutes(14, dayOfYear));
		assertEquals(783.0, c.getDayMinutes(30, dayOfYear));
		
		c.addActivity("12/25/21", "359", "hw12", "0:10:00:00");
		assertEquals(794.0, c.getTotalMinutes());
		assertEquals(10.0, c.getDayMinutes(0, dayOfYear));
		assertEquals(504.5, c.getDayMinutes(7, dayOfYear));
		assertEquals(726.5, c.getDayMinutes(14, dayOfYear));
		assertEquals(793.0, c.getDayMinutes(30, dayOfYear));
		
		
		
		// Specific failing test
		Course b = new Course("PY208");
		// day of year tested: 362
		LocalDateTime dayOfYear2 = LocalDateTime.parse("2021-12-28T15:54:51.959314");

				
		b.addActivity("12/28/21", "362", "hw7", "0:00:20:18");
		assertEquals(0.33, b.getTotalMinutes());
		assertEquals(0.33, b.getDayMinutes(0, dayOfYear2));
		assertEquals(0.33, b.getDayMinutes(7, dayOfYear2));
		assertEquals(0.33, b.getDayMinutes(14, dayOfYear2));
		assertEquals(0.33, b.getDayMinutes(30, dayOfYear2));
		
		b.addActivity("11/01/21", null, "hw1", "2:00.0");
		assertEquals(120.33, b.getTotalMinutes());
		assertEquals(0.33, b.getDayMinutes(0, dayOfYear2));
		assertEquals(0.33, b.getDayMinutes(7, dayOfYear2));
		assertEquals(0.33, b.getDayMinutes(14, dayOfYear2));
		assertEquals(0.33, b.getDayMinutes(30, dayOfYear2));
		
	}
	
	@Test
	void testGetDayMinutesInterYear() {
		Course c = new Course("CSC216");
				
		// Day of year: 4
		// Date: 1/4/2022 9:30
		LocalDateTime dayOfYear = LocalDateTime.of(2022, 1, 4, 9, 30);
		
		c.addActivity("12/28/21", "362", "hw1", "0:10:00:00"); // last 7
		assertEquals(10.0, c.getTotalMinutes());
		assertEquals(0.0, c.getDayMinutes(0, dayOfYear));
		assertEquals(10.0, c.getDayMinutes(7, dayOfYear));
		assertEquals(10.0, c.getDayMinutes(14, dayOfYear));
		assertEquals(10.0, c.getDayMinutes(30, dayOfYear));
		
		c.addActivity("12/31/21", "365", "hw2", "1:30:30:00"); // last 7
		assertEquals(100.5, c.getTotalMinutes());
		assertEquals(0.0, c.getDayMinutes(0, dayOfYear));
		assertEquals(100.5, c.getDayMinutes(7, dayOfYear));
		assertEquals(100.5, c.getDayMinutes(14, dayOfYear));
		assertEquals(100.5, c.getDayMinutes(30, dayOfYear));
		
		c.addActivity("1/2/22", "2", "hw3", "0:45:45:00"); // last 7
		assertEquals(146.25, c.getTotalMinutes());
		assertEquals(0.0, c.getDayMinutes(0, dayOfYear));
		assertEquals(146.25, c.getDayMinutes(7, dayOfYear));
		assertEquals(146.25, c.getDayMinutes(14, dayOfYear));
		assertEquals(146.25, c.getDayMinutes(30, dayOfYear));
		
		c.addActivity("1/4/22", "4", "hw4", "3:00:00:00"); // today
		assertEquals(326.25, c.getTotalMinutes());
		assertEquals(180.0, c.getDayMinutes(0, dayOfYear));
		assertEquals(326.25, c.getDayMinutes(7, dayOfYear));
		assertEquals(326.25, c.getDayMinutes(14, dayOfYear));
		assertEquals(326.25, c.getDayMinutes(30, dayOfYear));

	}

}
