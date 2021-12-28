package util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the SortedList class
 * @author alexzettlemoyer
 *
 */
class SortedListTest {

	/**
	 * Tests the add() method
	 */
	@Test
	void testAdd() {
		
		// adding many elements
		SortedList<Integer> list = new SortedList<Integer>();
		list.add(1);
		assertEquals(1, list.get(0));
		list.add(2);
		assertEquals(2, list.size());
		assertEquals(1, list.get(0));
		assertEquals(2, list.get(1));
		list.add(5);
		assertEquals(3, list.size());
		assertEquals(5, list.get(2));
		list.add(3);
		assertEquals(3, list.get(2));
		assertEquals(5, list.get(3));
		
		// adding to the front of the list
		SortedList<Integer> list2 = new SortedList<Integer>();
		list2.add(3);
		assertEquals(3, list2.get(0));
		list2.add(2);
		assertEquals(2, list2.get(0));
		assertEquals(3, list2.get(1)); // 2 -> 3
		list2.add(1);
		assertEquals(1, list2.get(0)); 
		assertEquals(2, list2.get(1));
		assertEquals(3, list2.get(2)); // 1 -> 2 -> 3

		// adding to the back of the list
		SortedList<Integer> list3 = new SortedList<Integer>();
		list3.add(1);
		assertEquals(1, list3.get(0));
		list3.add(2);
		assertEquals(2, list3.get(1));
		list3.add(6);
		assertEquals(6, list3.get(2));
		list3.add(100);
		assertEquals(100, list3.get(3));
		
		// adding null element
		Exception e5 = assertThrows(NullPointerException.class,
				() -> list3.add(null));
		assertEquals("Cannot add null element.", e5.getMessage());
	}
	
	@Test
	void testRemoveElement() {
		SortedList<String> s = new SortedList<String>();
		
		assertThrows(IllegalArgumentException.class,
				() -> s.remove("banana"));
		
		s.add("banana");
		s.add("fruit");
		s.add("apple");
		s.add("peach");
		s.add("grape");
		
		assertEquals("apple", s.get(0));
		assertEquals("banana", s.get(1));
		assertEquals("fruit", s.get(2));
		assertEquals("grape", s.get(3));
		assertEquals("peach", s.get(4)); // apple, banana, fruit, grape, peach
		
		// removing from end of list
		s.remove("peach"); 
		assertEquals("apple", s.get(0));
		assertEquals("banana", s.get(1));
		assertEquals("fruit", s.get(2));
		assertEquals("grape", s.get(3)); // apple, banana, fruit, grape
		
		// removing from beginning of list
		s.remove("apple");
		assertEquals("banana", s.get(0));
		assertEquals("fruit", s.get(1));
		assertEquals("grape", s.get(2)); // banana, fruit, grape

		// removing from middle of list
		s.remove("fruit");
		assertEquals("banana", s.get(0));
		assertEquals("grape", s.get(1)); // banana, grape
		
		Exception e = assertThrows(IllegalArgumentException.class, 
				() -> s.remove("strawberry"));
		assertEquals("Element does not exist in list.", e.getMessage());
	}

	/**
	 * Tests the remove() method
	 */
	@Test
	void testRemoveIndex() {
		SortedList<Integer> s = new SortedList<Integer>();
		
		s.add(5);
		s.add(10);
		s.add(3);
		s.add(1);
		s.add(7);
		
		assertEquals(1, s.get(0));
		assertEquals(3, s.get(1));
		assertEquals(5, s.get(2));
		assertEquals(7, s.get(3));
		assertEquals(10, s.get(4)); // 1 3 5 7 10
		
		// removing from end of list
		assertEquals(10, s.remove(4)); // 1 3 5 7
		assertEquals(1, s.get(0));
		assertEquals(3, s.get(1));
		assertEquals(5, s.get(2));
		assertEquals(7, s.get(3));
		
		// removing from beginning of list
		assertEquals(1, s.remove(0)); // 3 5 7
		assertEquals(3, s.get(0));
		assertEquals(5, s.get(1));
		assertEquals(7, s.get(2));

		// removing from middle of list
		assertEquals(5, s.remove(1)); // 3 7
		assertEquals(3, s.get(0));
		assertEquals(7, s.get(1));
		
		Exception e = assertThrows(IndexOutOfBoundsException.class, 
				() -> s.remove(4));
		assertEquals("Invalid index.", e.getMessage());

	}

	/**
	 * Tests the contains() method
	 */
	@Test
	void testContains() {
		SortedList<Integer> s = new SortedList<Integer>();
		s.add(5);
		s.add(3);
		s.add(10);
		
		assertTrue(s.contains(5));
		assertTrue(s.contains(10));
		assertTrue(s.contains(3));
		
		assertFalse(s.contains(7));
		assertFalse(s.contains(2));
		assertFalse(s.contains(100));


	}

	/**
	 * Tests the get() method
	 */
	@Test
	void testGet() {
		
		SortedList<Integer> s = new SortedList<Integer>();
		s.add(5);
		s.add(3);
		s.add(10);
		
		assertEquals(3, s.get(0));
		assertEquals(5, s.get(1));
		assertEquals(10, s.get(2));
		
		assertEquals(3, s.size());
		
		Exception e = assertThrows(IndexOutOfBoundsException.class, 
				() -> s.get(3));
		assertEquals("Invalid index.", e.getMessage());
	}

	/**
	 * Tests the size() method
	 */
	@Test
	void testSize() {
		SortedList<Integer> s = new SortedList<Integer>();
		assertEquals(0, s.size());
		s.add(5);
		assertEquals(1, s.size());
		s.add(3);
		assertEquals(2, s.size());
		s.add(10);
		assertEquals(3, s.size());

		s.remove(0);
		assertEquals(2, s.size());
		s.remove(0);
		assertEquals(1, s.size());
	}

}

