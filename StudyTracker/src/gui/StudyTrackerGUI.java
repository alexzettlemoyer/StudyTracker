package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class StudyTrackerGUI extends JFrame {
	
	DirectoryPanel pnlDirectory;
	TrackerPanel pnlTracker;
	ActivityPanel pnlActivity;
	DataPanel pnlData;


	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 4686436822619654077L;

	/** setup */
	public StudyTrackerGUI() {
		initializeGUI();
	}
	
	/**
	 * initializing the GUI
	 */
	public void initializeGUI() {
		Container c = getContentPane();
		setTitle("StudyTracker");
		setLocation(100, 100);
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		c.setLayout(new GridBagLayout());
		Border lowerEtched = BorderFactory.createEtchedBorder(Color.white, Color.white);		
		
			// DIRECTORY PANEL
		pnlDirectory = new DirectoryPanel();
		pnlDirectory.setMinimumSize(new Dimension(800, 200));
		pnlDirectory.setBorder(lowerEtched);
		
			// TRACKER PANEL
		pnlTracker = new TrackerPanel();
		TitledBorder borderTracker = BorderFactory.createTitledBorder(lowerEtched, "My Tracker");
		pnlTracker.setMinimumSize(new Dimension(240, 600));
		pnlTracker.setBorder(borderTracker);
		
			// ACTIVITY PANEL
		pnlActivity = new ActivityPanel();
		TitledBorder borderActivity = BorderFactory.createTitledBorder(lowerEtched, "Activity");
		pnlActivity.setMinimumSize(new Dimension(240, 600));
		pnlActivity.setBorder(borderActivity);
		
			// VISUAL PANEL
		pnlData = new DataPanel();
		TitledBorder borderData = BorderFactory.createTitledBorder(lowerEtched, "Data");
		pnlData.setMinimumSize(new Dimension(240, 600));
		pnlData.setBorder(borderData);
		
		GridBagConstraints constraints = new GridBagConstraints();
		
			// set directory constraints
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 0.25;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
			// add the directory with its constraints
		c.add(pnlDirectory, constraints);
		
			// set tracker constraints
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0.35;
		constraints.weighty = 0.75;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 1;
			// add the tracker with its constraints
		c.add(pnlTracker, constraints);
		
			// set activity constraints
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 0.325;
		constraints.weighty = 0.75;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.fill = GridBagConstraints.BOTH;
			// add activity with its constraints
		c.add(pnlActivity, constraints);
		
			// set data constraints
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.weightx = 0.325;
		constraints.weighty = 0.75;
		constraints.anchor = GridBagConstraints.LINE_END;
		constraints.fill = GridBagConstraints.BOTH;
			// add data with its constraints
		c.add(pnlData, constraints);
			
		setVisible(true);
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		StudyTrackerGUI ui = new StudyTrackerGUI();
	}
}
