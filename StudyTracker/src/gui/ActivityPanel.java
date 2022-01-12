package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;

import course.Course;
import semester.Semester;
import tracker.Tracker;

/**
 * ACTIVITY PANEL
 * 
 * Handles displaying the Saved Activities
 * Saves the most recent activities to the top of the list
 * Allows users to delete Activities from the list
 * 
 * @author alexzettlemoyer
 *
 */
public class ActivityPanel extends JPanel implements ActionListener {
	
	/** Course and Semester Labels */
	private JLabel lblCourse;
	private JLabel lblSemester;
	
	/** Fields to handle the list which holds the Activities */
	private JScrollPane scrollList;
	private JTable tableList;
	private ActivityTableModel activityTableModel;
	
	/** Delete button */
	private JButton btnDelete;
	
	/** Non-visible buttons which allow the Panel to react when actions occur in other Panels */
	private JButton btnSwitch;
	private JButton btnAddActivity;
	
	/** fields that interact with the rest of the program
	 * ie. panels, and the program's controller */
	private Tracker tracker;
	private Listener listener;

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ActivityPanel Constructor
	 */
	public ActivityPanel() {
		super(new GridBagLayout());
		
		setPreferredSize(new Dimension(260, 600));
		
			// Setup
		tracker = Tracker.getInstance();
		listener = Listener.getInstance();
		listener.setActivity(this);
		Border border = new LineBorder(Color.white, 4);
		JPanel pnlLabels = new JPanel();
		JPanel pnlButton = new JPanel();
		pnlButton.setPreferredSize(new Dimension(50, 30));
		pnlButton.setLayout(new BorderLayout());

			// CURRENT SEMESTER: label
		lblSemester = new JLabel();
		lblSemester.setText("Semester");
			// CURRENT COURSE: label
		lblCourse = new JLabel();
		lblCourse.setText("Course");
			// Adding Current Semester/Course to panel
		pnlLabels.add(lblSemester, BorderLayout.WEST);
		pnlLabels.add(new JLabel(" - "), BorderLayout.CENTER);
		pnlLabels.add(lblCourse, BorderLayout.EAST);
		
			// Activity Table List
		activityTableModel = new ActivityTableModel();
		scrollList = new JScrollPane();
		tableList = new JTable(activityTableModel);
		tableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableList.setPreferredScrollableViewportSize(new Dimension(50, 300));
		tableList.setFillsViewportHeight(true);
				
			// DELETE: button
		btnDelete = new JButton("Delete");
		btnDelete.setBorder(border);
		btnDelete.setBackground(Color.LIGHT_GRAY);
		btnDelete.setPreferredSize(new Dimension(70, 20));
		btnDelete.addActionListener(this);
		pnlButton.add(btnDelete, BorderLayout.EAST);
		
			// invisible buttons that react to actions from other panels
		btnAddActivity = new JButton();
		btnAddActivity.addActionListener(this);
		btnSwitch = new JButton();
		btnSwitch.addActionListener(this);
		
		scrollList = new JScrollPane(tableList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.1;
		add(pnlLabels, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 2;
		c.weighty = 0.8;
		add(scrollList, c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 1;
		c.weighty = 0.1;
		c.insets = new Insets(0, 90, 0, 5);
		add(pnlButton, c);
		
	}

	/**
	 * ActionPerformed
	 * Handles various events as they occur:
	 * Deleting an Activity
	 * 
	 * btnSwitch.click occurs when the DirectoryPanel 
	 * switches between Semesters or Courses to update this Panel
	 * 
	 * btnAddActivity.click occurs when the TrackerPanel
	 * adds an Activity (both manually or generically)
	 * 
	 * @param e the ActionEvent that was performed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(btnAddActivity)) { // ADD activity
			activityTableModel.updateData();
		}
		else if (e.getSource().equals(btnSwitch)) { // SWITCH between Courses or Semesters
			activityTableModel.updateData();
		}
		else if (e.getSource().equals(btnDelete)) { // DELETE activity
			int row = tableList.getSelectedRow();
			if (row != -1 && row != tableList.getRowCount()) {
				try {
					tracker.deleteActivity(row);
				} catch (ArrayIndexOutOfBoundsException aioobe) {
					JOptionPane.showMessageDialog(this, "No activity selected.");
				}
			}
			activityTableModel.updateData();
			listener.removeActivity();
		}
		
		this.validate();
		this.repaint();
	}
	
	/**
	 * updates the currentSemester and currentCourse label
	 */
	public void update() {
		updateSemester();
		updateCourse();
		this.validate();
		this.repaint();
	}
	
	/**
	 * Updates the currentSemester Label
	 */
	public void updateSemester() {
		Semester currentSemester = tracker.getCurrentSemester();
		if (currentSemester != null) {
			lblSemester.setText(currentSemester.getSemesterName());
		}
		else {
			lblSemester.setText("Semester");
		}
		btnSwitch.doClick();
	}
	
	/**
	 * Updates the currentCourse label
	 */
	public void updateCourse() {
		Course currentCourse = tracker.getCurrentCourse();
		if (currentCourse != null) {
			lblCourse.setText(currentCourse.getCourseTitle());
		}
		else {
			lblCourse.setText("Course");
		}
		btnSwitch.doClick();
	}
	
	/**
	 * Adds an activity to the List
	 * 
	 * @param date
	 * @param dayOfYear
	 * @param title
	 * @param time
	 */
	public void addActivity(String date, String dayOfYear, String title, String time) {
		tracker.saveActivity(date, dayOfYear, title, time);
		btnAddActivity.doClick();
	}
	
	/**
	 * ACTIVITY TABLE MODEL inner class
	 * the scrollable list of activities
	 * 
	 * @author alexzettlemoyer
	 *
	 */
	private class ActivityTableModel extends AbstractTableModel {
		
		/**
		 * Serial Version UID
		 */
		private static final long serialVersionUID = 1L;
		
		/** column names */
		private String[] columnNames = {"Date", "Title", "Time"};
		/** 2d array of objects to hold the data */
		private Object[][] data;
		
		/**
		 * ActivityTabelModel constructor
		 */
		public ActivityTableModel() {
			updateData();
		}

		/**
		 * getRowCount
		 * @return the number of rows currently filled
		 */
		@Override
		public int getRowCount() {
			if (data == null) 
				return 0;
			return data.length;
		}

		/**
		 * getColumnCount
		 * @return the number of columns currently filled
		 */
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}
		
		/**
		 * getColumnName
		 * @param columnIndex 
		 * @return the String of the Column's name
		 */
		public String getColumnName(int columnIndex) {
			return columnNames[columnIndex];
		}

		/**
		 * getValueAt
		 * @param rowIndex
		 * @param columnIndex
		 * @return Object the object at the given row and column index
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (data == null)
				return null;
			return data[rowIndex][columnIndex];
		}
		
		/**
		 * setValueAt
		 * @param value the Object to set
		 * @param rowIndex
		 * @param columnIndex
		 */
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			data[rowIndex][columnIndex] = value;
			fireTableCellUpdated(rowIndex, columnIndex);
		}
		
		/**
		 * updateData
		 * updates the data according to the tracker's current Activity
		 */
		public void updateData() {
			data = tracker.getActivity();
		}
	}

}
