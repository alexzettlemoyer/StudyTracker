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
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import course.Course;
import semester.Semester;
import tracker.Tracker;

public class DataPanel extends JPanel implements ActionListener {
	
	JLabel lblTitle;
	
	JLabel lblCourse;
	JLabel lblCourseTime;
	JButton btnGraphCourseWeekly;
	JButton btnGraphCourse;
	JComboBox<String> comboCoursePeriod;
	
	JLabel lblSemester;
	JLabel lblSemesterTime;
	JButton btnGraphSemester;
	JComboBox<String> comboSemesterPeriod;
	
	JLabel lblTotal;
	JLabel lblTotalTime;
	
	JLabel lblCourseDivider;
	JLabel lblSemesterDivider;
	JLabel lblTotalDivider;
	
	JButton btnAddActivity;
	JButton btnSwitch;
	
	Listener listener;
	Tracker tracker;
	
	String coursePeriod;
	String semesterPeriod;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataPanel() {
		super(new GridBagLayout());	
		
		listener = Listener.getInstance();
		listener.setData(this);
		
		tracker = Tracker.getInstance();
		
		setPreferredSize(new Dimension(260, 600));
		
		JPanel pnlCourse = new JPanel();
		JPanel pnlSemester = new JPanel();
		JPanel pnlTotal = new JPanel();
		
		Border border = new LineBorder(Color.white, 4);
		
			// DIVIDER: ":" label
		lblCourseDivider = new JLabel(":");
		lblSemesterDivider = new JLabel(":");
		lblTotalDivider = new JLabel(":");
		
			// TITLE: label
		lblTitle = new JLabel("Study Time");
		
			// COURSE: label
		lblCourse = new JLabel("Course");
			// COURSE TIME: label
		lblCourseTime = new JLabel("0:00.0");
		lblCourseTime.setHorizontalAlignment(JLabel.CENTER);
		lblCourseTime.setPreferredSize(new Dimension(80, 40));
		lblCourseTime.setBorder(new LineBorder(Color.WHITE, 1));
			// COURSE PERIOD: combo box
		comboCoursePeriod = new JComboBox<String>();
		comboCoursePeriod.setBorder(border);
		comboCoursePeriod.setPreferredSize(new Dimension(110, 40));
		comboCoursePeriod.addActionListener(this);
		comboCoursePeriod.addItem("today");
		comboCoursePeriod.addItem("last 7");
		comboCoursePeriod.addItem("last 14");
		comboCoursePeriod.addItem("last 30");
		comboCoursePeriod.addItem("total");
		coursePeriod = "total";
		comboCoursePeriod.setSelectedItem(coursePeriod);
			// GRAPH COURSE [ weekly total ] : button
		btnGraphCourseWeekly = new JButton("Weekly");
		btnGraphCourseWeekly.setBorder(border);
		btnGraphCourseWeekly.setBackground(Color.LIGHT_GRAY);
		btnGraphCourseWeekly.setPreferredSize(new Dimension(80, 40));
		btnGraphCourseWeekly.addActionListener(this);
			// GRAPH COURSE [ last 30 days ] : button
		btnGraphCourse = new JButton("Graph");
		btnGraphCourse.setBorder(border);
		btnGraphCourse.setBackground(Color.LIGHT_GRAY);
		btnGraphCourse.setPreferredSize(new Dimension(80, 40));
		btnGraphCourse.addActionListener(this);
		
		
			// SEMESTER: label
		lblSemester = new JLabel("Semester");
			// SEMESTER TIME: label
		lblSemesterTime = new JLabel("0:00.0");
		lblSemesterTime.setHorizontalAlignment(JLabel.CENTER);
		lblSemesterTime.setPreferredSize(new Dimension(80, 40));
		lblSemesterTime.setBorder(new LineBorder(Color.WHITE, 1));
			// SEMESTER PERIOD: combo box
		comboSemesterPeriod = new JComboBox<String>();
		comboSemesterPeriod.setBorder(border);
		comboSemesterPeriod.setPreferredSize(new Dimension(110, 40));
		comboSemesterPeriod.addActionListener(this);
		comboSemesterPeriod.addItem("today");
		comboSemesterPeriod.addItem("last 7");
		comboSemesterPeriod.addItem("last 14");
		comboSemesterPeriod.addItem("last 30");
		comboSemesterPeriod.addItem("total");
		semesterPeriod = "total";
		comboSemesterPeriod.setSelectedItem(semesterPeriod);
			// GRAPH BUTTON: button
		btnGraphSemester = new JButton("Graph");
		btnGraphSemester.setBorder(border);
		btnGraphSemester.setBackground(Color.LIGHT_GRAY);
		btnGraphSemester.setPreferredSize(new Dimension(80, 40));
		btnGraphSemester.addActionListener(this);
		
		
			// STUDY TIME: label
		lblTotal = new JLabel("Total Study Time");
			// TOTAL TIME: label
		lblTotalTime = new JLabel("0:00.0");
		lblTotalTime.setHorizontalAlignment(JLabel.CENTER);
		lblTotalTime.setPreferredSize(new Dimension(80, 40));
		lblTotalTime.setBorder(new LineBorder(Color.WHITE, 1));
		
		btnAddActivity = new JButton();
		btnSwitch = new JButton();
		
		
		GridBagConstraints cGraph = new GridBagConstraints();
		cGraph.insets = new Insets(2, 2, 2, 2);
		
		pnlCourse.setLayout(new GridBagLayout());		
		pnlSemester.setLayout(new GridBagLayout());
		
		cGraph.gridx = 0;
		cGraph.gridy = 0;
		cGraph.weightx = 1;
		cGraph.weighty = 0.3;
		cGraph.anchor = GridBagConstraints.LINE_START;
		pnlCourse.add(lblCourse, cGraph);
		pnlSemester.add(lblSemester, cGraph);
		
		cGraph.gridx = 0;
		cGraph.gridy = 1;
		cGraph.weightx = 0.49;
		cGraph.weighty = 0.4;
		pnlCourse.add(comboCoursePeriod, cGraph);
		pnlSemester.add(comboSemesterPeriod, cGraph);
		
		cGraph.gridx = 1;
		cGraph.gridy = 1;
		cGraph.weightx = 0.02;
		cGraph.weighty = 0.4;
		pnlCourse.add(lblCourseDivider, cGraph);
		pnlSemester.add(lblSemesterDivider, cGraph);
		
		cGraph.gridx = 2;
		cGraph.gridy = 1;
		cGraph.weightx = 0.49;
		cGraph.weighty = 0.4;
		cGraph.anchor = GridBagConstraints.LINE_END;
		pnlCourse.add(lblCourseTime, cGraph);
		pnlSemester.add(lblSemesterTime, cGraph);
		
		cGraph.gridx = 2;
		cGraph.gridy = 2;
		cGraph.weightx = 0.49;
		cGraph.weighty = 0.1;
		pnlCourse.add(btnGraphCourse, cGraph);
		pnlSemester.add(btnGraphSemester, cGraph);
		
	
		GridBagConstraints cTotal = new GridBagConstraints();
		pnlTotal.setLayout(new GridBagLayout());
		
		cTotal.gridx = 0;
		cTotal.gridy = 1;
		cTotal.weightx = 0.49;
		cTotal.weighty = 0.5;
		pnlTotal.add(lblTotal, cTotal);
		
		cTotal.gridx = 1;
		cTotal.gridy = 1;
		cTotal.weightx = 0.02;
		cTotal.weighty = 0.5;
		pnlTotal.add(lblTotalDivider, cTotal);
		
		cTotal.gridx = 2;
		cTotal.gridy = 1;
		cTotal.weightx = 0.49;
		cTotal.weighty = 0.5;
		cTotal.anchor = GridBagConstraints.LINE_END;
		pnlTotal.add(lblTotalTime, cTotal);	
		
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 20, 1, 20);
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.CENTER;
		add(lblTitle, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0.4;
		c.insets = new Insets(30, 20, 1, 20);
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(pnlCourse, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 0.4;
		c.insets = new Insets(1, 20, 1, 20);
		add(pnlSemester, c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 1;
		c.weighty = 0.1;
		c.insets = new Insets(10, 20, 60, 20);
		add(pnlTotal, c);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(btnSwitch)) {
			//
		}
		else if (e.getSource().equals(btnAddActivity)) {
			//
		}
		else if (e.getSource().equals(comboCoursePeriod)) {
			coursePeriod = (String) comboCoursePeriod.getSelectedItem();
			updateCourseTime(coursePeriod);
		}
		else if (e.getSource().equals(comboSemesterPeriod)) {
			semesterPeriod = (String) comboSemesterPeriod.getSelectedItem();
			updateSemesterTime(semesterPeriod);
		}
		else if (e.getSource().equals(btnGraphCourse)) {
			
			GraphPanel graphCourse = new GraphPanel(true, (String) comboCoursePeriod.getSelectedItem());
			graphCourse.setVisible(true);
		}
		else if (e.getSource().equals(btnGraphSemester)) {
			
			GraphPanel graphSemester = new GraphPanel(false, (String) comboSemesterPeriod.getSelectedItem());
			graphSemester.setVisible(true);
		}
	}
	
	public void update() {
		updateCourseTime("total");
		updateSemesterTime("total");
		updateTotal();
		updateSemester();
		updateCourse();
		this.validate();
		this.repaint();
	}
	
	public void addActivity() {
		comboCoursePeriod.actionPerformed(null);
		comboSemesterPeriod.actionPerformed(null);
		comboCoursePeriod.setSelectedItem(coursePeriod);
		comboSemesterPeriod.setSelectedItem(semesterPeriod);
		updateTotal();
	}
	
	public void removeActivity() {
		comboCoursePeriod.actionPerformed(null);
		comboSemesterPeriod.actionPerformed(null);
		comboCoursePeriod.setSelectedItem(coursePeriod);
		comboSemesterPeriod.setSelectedItem(semesterPeriod);
		updateTotal();
	}
	
	public void updateCourseTime(String period) {
		
		switch (period) {
			case "today":
				lblCourseTime.setText(tracker.getCourseDayMinutes(0, null));
				break;
			case "last 7":
				lblCourseTime.setText(tracker.getCourseDayMinutes(7, null));
				break;
			case "last 14":
				lblCourseTime.setText(tracker.getCourseDayMinutes(14, null));
				break;
			case "last 30":
				lblCourseTime.setText(tracker.getCourseDayMinutes(30, null));
				break;
			default:
				lblCourseTime.setText(tracker.getTotalCourseTime());
				break;
		}
	}
	
	public void updateSemesterTime(String period) {
		
			switch (period) {
			case "today":
				lblSemesterTime.setText(tracker.getSemesterDayMinutes(0, null));
				break;
			case "last 7":
				lblSemesterTime.setText(tracker.getSemesterDayMinutes(7, null));
				break;
			case "last 14":
				lblSemesterTime.setText(tracker.getSemesterDayMinutes(14, null));
				break;
			case "last 30":
				lblSemesterTime.setText(tracker.getSemesterDayMinutes(30, null));
				break;
			default:
				lblSemesterTime.setText(tracker.getTotalSemesterTime());
				break;
		}
	}
	
	public void updateTotal() {
		lblTotalTime.setText(tracker.getTotalTime());
	}
	
	public void updateSemester() {
		Semester currentSemester = tracker.getCurrentSemester();
		if (currentSemester != null) {
			lblSemester.setText(currentSemester.getSemesterName());
		}
		else {
			lblSemester.setText("Semester");
		}
		updateCourse();
		comboCoursePeriod.actionPerformed(null);
		comboCoursePeriod.setSelectedItem(coursePeriod);
		comboSemesterPeriod.setSelectedItem(semesterPeriod);
	}
	
	public void updateCourse() {
		Course currentCourse = tracker.getCurrentCourse();
		if (currentCourse != null) {
			lblCourse.setText(currentCourse.getCourseTitle());
		}
		else {
			lblCourse.setText("Course");
		}
		comboCoursePeriod.actionPerformed(null);
		comboSemesterPeriod.actionPerformed(null);
		comboCoursePeriod.setSelectedItem(coursePeriod);
	}

}
