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

public class ActivityPanel extends JPanel implements ActionListener {
	
	JLabel lblCourse;
	JLabel lblSemester;
	
	JScrollPane scrollList;
	JTable tableList;
	ActivityTableModel activityTableModel;
	
	JButton btnDelete;
	JButton btnSwitch;
	
	JPanel pnlLabels;
	JPanel pnlButton;
	
	JButton btnAddActivity;
	
	private Tracker tracker;
	private Listener listener;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ActivityPanel() {
		super(new GridBagLayout());
		
		setPreferredSize(new Dimension(260, 600));
		
		tracker = Tracker.getInstance();
		listener = Listener.getInstance();
		listener.setActivity(this);
		
		pnlLabels = new JPanel();
		pnlButton = new JPanel();
		pnlButton.setPreferredSize(new Dimension(50, 30));
		pnlButton.setLayout(new BorderLayout());

		lblSemester = new JLabel();
		lblSemester.setText("Semester");
		lblCourse = new JLabel();
		lblCourse.setText("Course");
		pnlLabels.add(lblSemester, BorderLayout.WEST);
		pnlLabels.add(new JLabel(" - "), BorderLayout.CENTER);
		pnlLabels.add(lblCourse, BorderLayout.EAST);
		
		activityTableModel = new ActivityTableModel();
		scrollList = new JScrollPane();
		tableList = new JTable(activityTableModel);
		tableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableList.setPreferredScrollableViewportSize(new Dimension(50, 300));
		tableList.setFillsViewportHeight(true);
		
		Border border = new LineBorder(Color.white, 4);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBorder(border);
		btnDelete.setBackground(Color.LIGHT_GRAY);
		btnDelete.setPreferredSize(new Dimension(70, 20));
		btnDelete.addActionListener(this);
		pnlButton.add(btnDelete, BorderLayout.EAST);
		
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
	
	public void addActivity(String date, String dayOfYear, String title, String time) {
		tracker.saveActivity(date, dayOfYear, title, time);
		btnAddActivity.doClick();
	}
	
	private class ActivityTableModel extends AbstractTableModel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String[] columnNames = {"Date", "Title", "Time"};
		private Object[][] data;
		
		public ActivityTableModel() {
			updateData();
		}

		@Override
		public int getRowCount() {
			if (data == null) 
				return 0;
			return data.length;
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}
		
		public String getColumnName(int columnIndex) {
			return columnNames[columnIndex];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (data == null)
				return null;
			return data[rowIndex][columnIndex];
		}
		
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			data[rowIndex][columnIndex] = value;
			fireTableCellUpdated(rowIndex, columnIndex);
		}
		
		public void updateData() {
			data = tracker.getActivity();
		}
	}

}
