package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import course.Course;
import semester.Semester;
import tracker.Tracker;

public class DirectoryPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel lblCurrentSemester;
	JComboBox<String> comboSemesters;
	JButton btnAddSemester;
	JButton btnEditSemester;
	JButton btnRemoveSemester;
	
	JLabel lblCurrentCourse;
	JComboBox<String> comboCourses;
	JButton btnAddCourse;
	JButton btnEditCourse;
	JButton btnRemoveCourse;
	
	private Tracker tracker;
	private Listener listener;
	
	public DirectoryPanel() {
		super(new GridBagLayout());
		
		tracker = Tracker.getInstance();
		listener = Listener.getInstance();
		
		JPanel pnlSemester = new JPanel();
		JPanel pnlSemesterButtons = new JPanel();
		
		JPanel pnlCourse = new JPanel();
		JPanel pnlCourseButtons = new JPanel();
		
		Border border = new LineBorder(Color.white, 4);
		
			// CURRENT SEMESTER: label
		lblCurrentSemester = new JLabel("Current Semester:");
			// SEMESTER: combo box
		comboSemesters = new JComboBox<String>();
		comboSemesters.setBorder(border);
		comboSemesters.setBackground(Color.LIGHT_GRAY);
		comboSemesters.setPreferredSize(new Dimension(120, 40)); 
		comboSemesters.addActionListener(this);
			// ADD SEMESTER: button
		btnAddSemester = new JButton("Add");
		btnAddSemester.setBorder(border);
		btnAddSemester.setBackground(Color.LIGHT_GRAY);
		btnAddSemester.setPreferredSize(new Dimension(80, 25));
		btnAddSemester.addActionListener(this);
			// EDIT SEMESTER: button
		btnEditSemester = new JButton("Edit");
		btnEditSemester.setBorder(border);
		btnEditSemester.setBackground(Color.LIGHT_GRAY);
		btnEditSemester.setPreferredSize(new Dimension(80, 25));
		btnEditSemester.addActionListener(this);
			// REMOVE SEMESTER: button
		btnRemoveSemester = new JButton("Remove");
		btnRemoveSemester.setBorder(border);
		btnRemoveSemester.setBackground(Color.LIGHT_GRAY);
		btnRemoveSemester.setPreferredSize(new Dimension(80, 25));
		btnRemoveSemester.addActionListener(this);
		
			// CURRENT COURSE: label
		lblCurrentCourse = new JLabel("Current Course:");
			// COURSE: combo box
		comboCourses = new JComboBox<String>();
		comboCourses.setBorder(border);
		comboCourses.setBackground(Color.LIGHT_GRAY);
		comboCourses.setPreferredSize(new Dimension(120, 40));
		comboCourses.addActionListener(this);
			// ADD COURSE: combo box
		btnAddCourse = new JButton("Add");
		btnAddCourse.setBorder(border);
		btnAddCourse.setBackground(Color.LIGHT_GRAY);
		btnAddCourse.setPreferredSize(new Dimension(80, 25));
		btnAddCourse.addActionListener(this);
			// EDIT COURSE: button
		btnEditCourse = new JButton("Edit");
		btnEditCourse.setBorder(border);
		btnEditCourse.setBackground(Color.LIGHT_GRAY);
		btnEditCourse.setPreferredSize(new Dimension(80, 25));
		btnEditCourse.addActionListener(this);
			// REMOVE COURSE: button
		btnRemoveCourse = new JButton("Remove");
		btnRemoveCourse.setBorder(border);
		btnRemoveCourse.setBackground(Color.LIGHT_GRAY);
		btnRemoveCourse.setPreferredSize(new Dimension(80, 25));
		btnRemoveCourse.addActionListener(this);
		
			// SEMSETER BUTTONS: panel
		GridBagConstraints cSemesterButtons = new GridBagConstraints();
		cSemesterButtons.insets = new Insets(3, 3, 3, 3);
		
		cSemesterButtons.gridheight = GridBagConstraints.REMAINDER;
		cSemesterButtons.fill = GridBagConstraints.VERTICAL;
		cSemesterButtons.anchor = GridBagConstraints.LINE_START;
		
		pnlSemesterButtons.setLayout(new GridBagLayout());
		
		cSemesterButtons.gridx = 0;
		cSemesterButtons.gridy = 0;
		cSemesterButtons.weightx = 0.3;
		cSemesterButtons.weighty = 1;
		pnlSemesterButtons.add(btnAddSemester, cSemesterButtons);
		
		cSemesterButtons.gridx = 1;
		cSemesterButtons.weightx = 0.3;
		cSemesterButtons.weighty = 1;
		pnlSemesterButtons.add(btnEditSemester, cSemesterButtons);
		
		cSemesterButtons.gridx = 2;
		cSemesterButtons.weightx = 0.3;
		cSemesterButtons.weighty = 1;
		pnlSemesterButtons.add(btnRemoveSemester, cSemesterButtons);
		
			// SEMESTER: panel
		GridBagConstraints cSemester = new GridBagConstraints();
		cSemester.insets = new Insets(3, 3, 3, 3);
		
		cSemester.gridwidth = GridBagConstraints.REMAINDER;
		cSemester.fill = GridBagConstraints.HORIZONTAL;
		cSemester.anchor = GridBagConstraints.LINE_START;
		
		pnlSemester.setLayout(new GridBagLayout());
		
		cSemester.gridx = 0;
		cSemester.gridy = 0;
		cSemester.weightx = 1;
		cSemester.weighty = 0.3;
		pnlSemester.add(lblCurrentSemester, cSemester);
		
		cSemester.gridx = 0;
		cSemester.gridy = 1;
		cSemester.weightx = 1;
		cSemester.weighty = 0.3;
		pnlSemester.add(comboSemesters, cSemester);
		
		cSemester.gridx = 0;
		cSemester.gridy = 2;
		cSemester.weightx = 1;
		cSemester.weighty = 0.3;
		pnlSemester.add(pnlSemesterButtons, cSemester);
		
		
			// COURSE BUTTONS: panel
		GridBagConstraints cCourseButtons = new GridBagConstraints();
		cCourseButtons.insets = new Insets(3, 3, 3, 3);
		
		cCourseButtons.gridheight = GridBagConstraints.REMAINDER;
		cCourseButtons.fill = GridBagConstraints.VERTICAL;
		cCourseButtons.anchor = GridBagConstraints.LINE_START;
		
		pnlCourseButtons.setLayout(new GridBagLayout());
		
		cCourseButtons.gridx = 0;
		cCourseButtons.gridy = 0;
		cCourseButtons.weightx = 0.3;
		cCourseButtons.weighty = 1;
		pnlCourseButtons.add(btnAddCourse, cCourseButtons);
		
		cCourseButtons.gridx = 1;
		cCourseButtons.weightx = 0.3;
		cCourseButtons.weighty = 1;
		pnlCourseButtons.add(btnEditCourse, cCourseButtons);
		
		cCourseButtons.gridx = 2;
		cCourseButtons.weightx = 0.3;
		cCourseButtons.weighty = 1;
		pnlCourseButtons.add(btnRemoveCourse, cCourseButtons);
		
			// COURSE: panel
		GridBagConstraints cCourse = new GridBagConstraints();
		cCourse.insets = new Insets(3, 3, 3, 3);
		
		cCourse.gridwidth = GridBagConstraints.REMAINDER;
		cCourse.fill = GridBagConstraints.HORIZONTAL;
		cCourse.anchor = GridBagConstraints.LINE_START;
		
		pnlCourse.setLayout(new GridBagLayout());
		
		cCourse.gridx = 0;
		cCourse.gridy = 0;
		cCourse.weightx = 1;
		cCourse.weighty = 0.3;
		pnlCourse.add(lblCurrentCourse, cCourse);
		
		cCourse.gridx = 0;
		cCourse.gridy = 1;
		cCourse.weightx = 1;
		cCourse.weighty = 0.3;
		pnlCourse.add(comboCourses, cCourse);
		
		cCourse.gridx = 0;
		cCourse.gridy = 2;
		cCourse.weightx = 1;
		cCourse.weighty = 0.3;
		pnlCourse.add(pnlCourseButtons, cCourse);
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 10, 10, 5);
		add(pnlSemester, c);
		add(pnlCourse, c);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == comboSemesters) { // comboList: SEMESTERS
			int idx = comboSemesters.getSelectedIndex();
			
			if (idx == -1) {
				 updateLists();
			} else {
				String semesterName = comboSemesters.getItemAt(idx);
				tracker.setCurrentSemester(semesterName);
				updateLists();
				listener.changeSemester();
			}
		}
		else if (e.getSource().equals(btnAddSemester)) { // button: ADD SEMESTER
			try {
				String semesterName = (String) JOptionPane.showInputDialog(this, "Semester?", "Create New Semester", JOptionPane.QUESTION_MESSAGE);
				tracker.addSemester(semesterName);
				updateLists();
			} catch (IllegalArgumentException iae) {
				if (!iae.getMessage().equals("Empty Name")) {
					JOptionPane.showMessageDialog(DirectoryPanel.this, iae.getMessage());
				}
			}
		}
		else if (e.getSource().equals(btnRemoveSemester)) { // button: REMOVE SEMESTER
			try {
				tracker.removeSemester(tracker.getCurrentSemester());
				listener.changeSemester();
				updateLists();
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(DirectoryPanel.this, iae.getMessage());
			}
		}
		else if (e.getSource().equals(btnEditSemester)) { // button: EDIT SEMESTER
			try {
				if (tracker.getCurrentSemester() == null) {
					throw new IllegalArgumentException("Must select a Semester to edit.");
				}
				String semesterName = (String) JOptionPane.showInputDialog(this, "New Semester Name?", "Edit Semester", JOptionPane.QUESTION_MESSAGE);
				tracker.editSemester(semesterName);
				updateSemesterList();
			} catch (IllegalArgumentException iae) {
				if (!iae.getMessage().equals("Empty Name")) {
					JOptionPane.showMessageDialog(DirectoryPanel.this, iae.getMessage());
				}
			}
		}
		else if (e.getSource() == comboCourses) { // comboList: COURSES
			int idx = comboCourses.getSelectedIndex();
			
			if (idx == -1) {
				 updateLists();
			} else {
				String courseName = comboCourses.getItemAt(idx);
				tracker.setCurrentCourse(courseName);
				updateCourseList();
				listener.changeCourse();
			}
		}
		else if (e.getSource().equals(btnAddCourse)) { // button: ADD COURSE
			try {
				if (tracker.getCurrentSemester() == null) {
					throw new IllegalArgumentException("Must select a Semester.");
				}
				String courseName = (String) JOptionPane.showInputDialog(this, "Course Name?", "Create New Course", JOptionPane.QUESTION_MESSAGE);
				tracker.addCourseToSemester(courseName);
				updateCourseList();
			} catch (IllegalArgumentException iae) {
				if (!iae.getMessage().equals("Empty title")) {
					JOptionPane.showMessageDialog(DirectoryPanel.this, iae.getMessage());
				}
			}
		}
		else if (e.getSource().equals(btnRemoveCourse)) { // button: REMOVE COURSE
			try {
				tracker.removeCourseFromSemester(tracker.getCurrentCourse());
				listener.changeCourse();
				updateLists();
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(DirectoryPanel.this, iae.getMessage());
			}
		}
		else if (e.getSource().equals(btnEditCourse)) { // button: EDIT COURSE
			try {
				if (tracker.getCurrentCourse() == null) {
					throw new IllegalArgumentException("Must select a Course to edit.");
				}
				String courseName = (String) JOptionPane.showInputDialog(this, "New Course Name?", "Edit Course", JOptionPane.QUESTION_MESSAGE);
				tracker.editCourse(courseName);
				updateCourseList();
			} catch (IllegalArgumentException iae) {
				if (!iae.getMessage().equals("Empty title")) {
					JOptionPane.showMessageDialog(DirectoryPanel.this, iae.getMessage());
				}
			}
		}		
	}
	
	/**
	 * updates semester and course list
	 */
	public void updateLists() {
		updateSemesterList();
		updateCourseList();
	}
	
	/**
	 * updates semester list
	 */
	private void updateSemesterList() {
		
		Semester currentSemester = tracker.getCurrentSemester();
		String semesterName = null;
		if (currentSemester != null) {
			semesterName = currentSemester.getSemesterName();
		}

		comboSemesters.removeAllItems();
		String[] semesterNames = tracker.getSemesterNames();
		for (int i = 0; i < semesterNames.length; i++) {
			comboSemesters.addItem(semesterNames[i]);
		}
		
		comboSemesters.setSelectedItem(semesterName);
	}
	
	/**
	 * updates course list
	 */
	private void updateCourseList() {
		if (tracker.getCurrentSemester() != null) {
			if (tracker.getCurrentSemester().getCourseList().size() == 0) {
				comboCourses.removeAllItems();
			}
			else {
				Course currentCourse = tracker.getCurrentCourse();
				String courseName = currentCourse.getCourseTitle();
				
				comboCourses.removeAllItems();
				String[] courseNames = tracker.getCurrentSemester().getCourseNames();
				for (int i = 0; i < courseNames.length; i++) {
					comboCourses.addItem(courseNames[i]);
				}
			
				comboCourses.setSelectedItem(courseName);
			}
		}
		else {
			comboCourses.removeAllItems();
		}
	}
	

}
