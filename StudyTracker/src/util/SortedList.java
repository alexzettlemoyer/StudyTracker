package util;

/**
 * Sorted List class
 * Implementation uses an underlying linkedList
 * @author alexzettlemoyer
 *
 * @param <E> the Type of Object being stored in the List
 */
public class SortedList<E extends Comparable<E>> {

	/**
	 * SIZE
	 * updated throughout to maintain count of how many elements are in the list
	 */
	private int size;
	
	/**
	 * FRONT 
	 * instance of ListNode inner class
	 * maintains a reference to the first Node (which represents the first element) in the list
	 */
	private ListNode front;
	
	/**
	 * Constructor sets size to 0
	 */
	public SortedList() {
		size = 0;
	}
	
	
	/**
	 * ADD element 
	 * Adds to appropriate spot in list using .compareTo
	 * @param element the element to add
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element is already in list
	 */
	public void add(E element) {
		
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		
		ListNode current = front;
		boolean added = false;
		
		// if the first linkedNode is null
		if (front == null) {
			front = new ListNode(element, null);
			size++;
		}
		// if the element is added in the front
		else if (front.data.compareTo(element) > 0) {
			front = new ListNode(element, front);
			size++; 
		}
		else if (front.next == null) {
			
			if (front.data.compareTo(element) == 0) {
				throw new IllegalArgumentException("Cannot add duplicate element.");
			}
			else if (front.data.compareTo(element) < 0) {
				front.next = new ListNode(element, null);
				size++;
			}
			else {
				front = new ListNode(element, front);
				size++;
			}
			
		}
		else {
			while (current.next != null && !added) {
				if (current.data.compareTo(element) == 0 || current.next.data.compareTo(element) == 0) {
					throw new IllegalArgumentException("Cannot add duplicate element.");
				}
				if (current.data.compareTo(element) < 0 && current.next.data.compareTo(element) > 0) {
					current.next = new ListNode(element, current.next);
					size++;
					added = true;
				}
				else {
					current = current.next;
				}
			}
			if (!added && current.data.compareTo(element) < 0) {
				current.next = new ListNode(element, current.next);
				size++;
			}
		}
	}
	
	public void remove(E element) {
		
		if (!contains(element)) {
			throw new IllegalArgumentException("Element does not exist in list.");
		}
//		if (front == null) {
//			throw new IndexOutOfBoundsException("Invalid index.");
//		}
		else {
			if (front.data.equals(element)) {
				front = front.next;
				size--;
			}
			else {
				ListNode current = front;

				while (current.next != null) {
					if (current.next.data.equals(element)) {
						current.next = current.next.next;
						size--;
						break;
					}
					current = current.next;
				}
			}
		}
	}

	/**
	 * REMOVE index
	 * Removes element from list and shifts list accordingly
	 * @param index the index of the element to remove
	 * @return the element that was removed
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 */
	public E remove(int index) {
		
		checkIndex(index);
		
		ListNode removed;
		ListNode current = front;
		
		if (index == 0) {
			removed = front;
			front = front.next; 
			size--;
		}
		else {
			
			for (int i = 0; i < index - 1; i++) {
				current = current.next; 
			}
			
			removed = current.next;
			current.next = current.next.next;
			size--;
		}	
		return removed.data;
	}
		
	/**
	 * helper method to check if the index parameter is valid
	 * @param index the index to check
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 */
	private void checkIndex(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
	}

	/**
	 * contains method.
	 * Checks whether element is in list or not.
	 * @param element the element to look for in the list
	 * @return true if element is in list, false otherwise
	 */
	public boolean contains(E element) {
		
		ListNode current = front;
		if (size == 0) {
			return false;
		}
		if (current.data.equals(element)) {
			return true;
		}
		for (int i = 0; i < size; i++) {
			if (current.data.equals(element)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	/**
	 * get method.
	 * Gets an element from the list given its index
	 * @param index the index of the element to get
	 * @return element the element with given index
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 */
	public E get(int index) {
		checkIndex(index);
		
		ListNode current = front;
		if (index == 0) {
			return front.data; 
		}

		for (int i = 0; i < index; i++) {
			current = current.next; 
		}
		
		return current.data;
	}

	/**
	 * size method.
	 * Size represents how many elements are in the list.
	 * @return size of the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Sorted List inner class
	 * ListNode has two fields
	 * data: the element in the list
	 * next: a reference to the next element in the list
	 * @author alexzettlemoyer
	 *
	 */
	public class ListNode {
		
		/**
		 * data field represents the element in the list
		 */
		public E data;
		
		/**
		 * next field maintains a reference to the next element in the list
		 */
		public ListNode next;
		
		/**
		 * ListNode constructor
		 * @param element the element in the list
		 * @param next the reference to the next element in the list
		 */
		public ListNode(E element, ListNode next) {
			data = element;
			this.next = next;
		}
		
	}
}
