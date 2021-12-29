package activity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ActivityTest {

	@Test
	void testActivity() {
		Activity a = new Activity("12/16/21", "315", "homework", "3:15:42:00");
		
		assertTrue(a.getDate().equals("12/16/21"));
		assertTrue(a.getTitle().equals("homework"));
	}

	@Test
	void testGetTimeString() {
		Activity a = new Activity("12/16/21", "315", "homework", "3:00:00:00");
		assertEquals(180.0, a.getTotalMinutes());
		assertEquals("3:00.0", a.getTimeString());
		
		Activity b = new Activity("11/12/20", "315", "study", "4:30:00:00");
		assertEquals(270.0, b.getTotalMinutes());
		assertEquals("4:30.0", b.getTimeString());
		
		Activity c = new Activity("9/1/22", "315", "project3", "2:45:55:00");
		assertEquals(165.92, c.getTotalMinutes());
		assertEquals("2:45.9", c.getTimeString());
		
		Activity d = new Activity("12/23/21", "315", "hw2", "0:00:17:00");
		assertEquals("0:00.3", d.getTimeString());
		assertEquals(0.28, Activity.getTime("0:00:17:28"));
	}

	@Test
	void testGetTimeDouble() {
		assertEquals("2:00.0", Activity.getTime(120.0));
		assertEquals("3:36.5", Activity.getTime(216.5));
	}
	
	@Test
	void testGetTotalMinutes() {
		Activity a = new Activity("12/16/21", "315", "homework", "5:00:63:00");
		assertEquals(301.05, a.getTotalMinutes());

	}
	
	@Test
	void testManualActivity() {
				
		// testing a valid activity
		Activity a = new Activity("12/16/21", null, "hw", "3:15.0");
		assertEquals("12/16/21", a.getDate());
		assertEquals(350, a.getDayOfYear());
		assertEquals("hw", a.getTitle());
		assertEquals("3:15.0", a.getTimeString());
		assertEquals(195.0, a.getTotalMinutes());
		
		// testing a valid activity with fraction time
		Activity b = new Activity("12/27/21", null, "hw", "0:45.85");
		assertEquals("12/27/21", b.getDate());
		assertEquals(361, b.getDayOfYear());
		assertEquals("hw", b.getTitle());
		assertEquals("0:45.85", b.getTimeString());
		assertEquals(45.85, b.getTotalMinutes());
		
		// INVALID ACTIVITY TESTS
		
			// invalid month ( > 12)
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Activity("13/27/21", null, "hw", "0:45.85"));
		assertTrue(e1.getMessage().equals("Invalid month."));
		
			// invalid month ( == 0)
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Activity("0/27/21", null, "hw", "0:45.85"));
		assertTrue(e2.getMessage().equals("Invalid month."));
		
			// invalid day ( > 31)
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Activity("12/32/21", null, "hw", "0:45.85"));
		assertTrue(e3.getMessage().equals("Invalid day."));
		
			// invalid day ( == 0)
		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> new Activity("12/0/21", null, "hw", "0:45.85"));
		assertTrue(e4.getMessage().equals("Invalid day."));
		
			// invalid day (month with 30 days)
		Exception e5 = assertThrows(IllegalArgumentException.class,
				() -> new Activity("11/31/21", null, "hw", "0:45.85"));
		assertTrue(e5.getMessage().equals("Error getting date."));
		
			// invalid year (earlier than 2021)
		Exception e6 = assertThrows(IllegalArgumentException.class,
				() -> new Activity("12/27/19", null, "hw", "0:45.85"));
		assertTrue(e6.getMessage().equals("Cannot save Activity before 2021."));
		
			// invalid year (in the future)
		Exception e7 = assertThrows(IllegalArgumentException.class,
				() -> new Activity("12/27/23", null, "hw", "0:45.85"));
		assertTrue(e7.getMessage().equals("Cannot save Activity in future time."));
		
			// invalid date (null)
		Exception e8 = assertThrows(IllegalArgumentException.class,
				() -> new Activity(null, null, "hw", "0:45.85"));
		assertTrue(e8.getMessage().equals("Invalid date."));
		
			// invalid time (0 time)
		Exception e9 = assertThrows(IllegalArgumentException.class,
				() -> new Activity("12/27/21", null, "hw", "0:00.0"));
		assertTrue(e9.getMessage().equals("Cannot save 0 time."));
		
			// invalid time format 1
		Exception e10 = assertThrows(IllegalArgumentException.class,
				() -> new Activity("12/27/21", null, "hw", "10"));
		assertTrue(e10.getMessage().equals("Invalid time format. Must be Hours:Min.Sec")); 
	
			// invalid time format 2
		Exception e11 = assertThrows(IllegalArgumentException.class,
				() -> new Activity("12/27/21", null, "hw", "0:45:20:00"));
		assertTrue(e11.getMessage().equals("Invalid time format. Must be Hours:Min.Sec")); 
	
			// invalid time format 3
		Exception e12 = assertThrows(IllegalArgumentException.class,
				() -> new Activity("12/27/21", null, "hw", "0:124:00:00"));
		assertTrue(e12.getMessage().equals("Invalid time format. Must be Hours:Min.Sec")); 
	
			// invalid time (hours > 10)
		Exception e13 = assertThrows(IllegalArgumentException.class,
				() -> new Activity("12/27/21", null, "hw", "11:45.0"));
		assertTrue(e13.getMessage().equals("Invalid hours. Must be between 0 and 10.")); 
			
			// invalid time (min >= 60)
		Exception e14 = assertThrows(IllegalArgumentException.class,
				() -> new Activity("12/27/21", null, "hw", "2:60.0"));
		assertTrue(e14.getMessage().equals("Invalid minutes. Must be between 0 and 60.")); 
			
			// invalid time (sec >= 100)
		Exception e15 = assertThrows(IllegalArgumentException.class,
				() -> new Activity("12/27/21", null, "hw", "2:59.100"));
		assertTrue(e15.getMessage().equals("Invalid second fraction. Must be between 0 and 100.")); 
			
			// invalid time (sec >= 100)
		Exception e16 = assertThrows(IllegalArgumentException.class,
				() -> new Activity("12/27/21", null, "hw", "2:59.901"));
		assertTrue(e16.getMessage().equals("Invalid second fraction. Must be between 0 and 100.")); 
			
			// invalid time (empty string)
		Exception e17 = assertThrows(IllegalArgumentException.class,
				() -> new Activity("12/27/21", null, "hw", ""));
		assertTrue(e17.getMessage().equals("Invalid time.")); 
		
			
	}
	

}
