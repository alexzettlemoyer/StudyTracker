package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import activity.Activity;

/**
 * TRACKER PANEL
 * 
 * Handles Timer, Title, Preview, and Manually Saving an Activity
 * 
 * Allows users to time their activity, give it a title,
 * preview the activity (with the current date and designated title,
 * and save it to the Activity Panel
 * Or to Manually enter their activity
 * 
 * @author alexzettlemoyer
 *
 */
public class TrackerPanel extends JPanel implements ActionListener, Runnable {
	
	
	/** Labels for Timer and Title */
	private JLabel lblTime;
	private JLabel lblPrompt;
	
	/** Buttons for Timer */
	private JButton btnStart;
	private JButton btnStop;
	private JButton btnSave;
	private JButton btnReset;
	
	/** Title text area */
	private final JTextArea txtArea;
		
	/** Timer fields */
	private boolean stop;
	private int hours;
	private int minutes;
	private int seconds;
	private int milliseconds;
	private long millis;
	
	/** Labels for Activity Preview */
	private JLabel lblActivity;
	private JLabel lblActivityDisplay;
	
	/** Fields for Manual Activity Add function */
	private JLabel lblDatePrompt;
	private JLabel lblTitlePrompt;
	private JLabel lblTimePrompt;
	private JTextArea txtDate;
	private JTextArea txtTitle;
	private JTextArea txtTime;
	private JButton btnManualAdd;
	
	/** Manual Add Panel */
	private JPanel pnlManualAdd;
	
	/** Fields to retrieve info about the Current Date */
	private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy");
	private final DateTimeFormatter DAYOFYEAR = DateTimeFormatter.ofPattern("D");
	private LocalDateTime now;
	
	/** Fields to handle saving an Activity */
	private String date;
	private String currentTitle;
	private String lastTime;
	
	/** instance of listener which allows panels to interact */
	private Listener listener;
		

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * TrackerPanel constructor
	 */
	public TrackerPanel() {
		super(new GridBagLayout());
		
		setPreferredSize(new Dimension(260, 600));
		
			// SETUP
		listener = Listener.getInstance();
		listener.setTracker(this);
		JPanel pnlTitle = new JPanel();
		JPanel pnlTime = new JPanel();
		JPanel pnlActivity = new JPanel();
		pnlManualAdd = new JPanel();
		Border border = new LineBorder(Color.white, 4);
		
		stop = false;
		
			// TITLE: label
		lblPrompt = new JLabel();
		lblPrompt.setText("Title: ");
			// TEXTBOX
		txtArea = new JTextArea();
		txtArea.setBorder(border);
		txtArea.setColumns(11);
		txtArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		
			// START button
		btnStart = new JButton("START");
		btnStart.setBorder(border);
		btnStart.setBackground(Color.LIGHT_GRAY);
		btnStart.setPreferredSize(new Dimension(80, 40));
		btnStart.addActionListener(this);
			// STOP button
		btnStop = new JButton("STOP");
		btnStop.setBorder(border);
		btnStop.setBackground(Color.LIGHT_GRAY);
		btnStop.setPreferredSize(new Dimension(80, 40));
		btnStop.addActionListener(this);
		btnStop.setVisible(false);
			// RESET button
		btnReset = new JButton("RESET");
		btnReset.setBorder(border);
		btnReset.setBackground(Color.LIGHT_GRAY);
		btnReset.setPreferredSize(new Dimension(80, 40));
		btnReset.addActionListener(this);
			// SAVE button
		btnSave = new JButton("SAVE");
		btnSave.setBorder(border);
		btnSave.setBackground(Color.LIGHT_GRAY);
		btnSave.setPreferredSize(new Dimension(70, 40));
		btnSave.addActionListener(this);
			// MANUAL save button
		btnManualAdd = new JButton("Save Activity Manually");
		btnManualAdd.setBorder(border);
		btnManualAdd.setBackground(Color.LIGHT_GRAY);
		btnManualAdd.setPreferredSize(new Dimension(180, 40));
		btnManualAdd.addActionListener(this);

		
			// TIME display
		lblTime = new JLabel();
		lblTime.setText("00:00:00:00");
		
		
			// ACTIVITY display
		lblActivity = new JLabel("Preview: ");
		now = LocalDateTime.now();
		date = now.format(dtf);
		
		lblActivityDisplay = new JLabel(date + " -  Title  -  0:00.0");
		lblActivityDisplay.setHorizontalAlignment(JLabel.CENTER); 
		lblActivityDisplay.setPreferredSize(new Dimension(220, 40));
		lblActivityDisplay.setBorder(new LineBorder(Color.WHITE, 1));
		
		
			// MANUAL ADD display
		txtDate = new JTextArea(date);
		txtDate.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		txtDate.setPreferredSize(new Dimension(73, 25));
		txtDate.setBorder(border);
		txtTitle = new JTextArea();
		txtTitle.setForeground(Color.GRAY);
		txtTitle.setPreferredSize(new Dimension(85, 25));
		txtTitle.setBorder(border);
		txtTime = new JTextArea("0:00.0");
		txtTime.setPreferredSize(new Dimension(50, 25));
		txtTime.setBorder(border);

		
		lblDatePrompt = new JLabel("Date:");
		lblTitlePrompt = new JLabel("Title:");
		lblTimePrompt = new JLabel("Time:");


		pnlManualAdd.setPreferredSize(new Dimension(250, 40));
		pnlManualAdd.setBorder(new LineBorder(Color.WHITE, 1));
		
		
			// Constraints for Time display
		GridBagConstraints cTime = new GridBagConstraints();
		pnlTime.setLayout(new GridBagLayout());
		
		cTime.anchor = GridBagConstraints.CENTER;
		cTime.fill = GridBagConstraints.CENTER;
		cTime.gridwidth = GridBagConstraints.REMAINDER;
		cTime.insets = new Insets(5, 5, 5, 5);
		
		cTime.gridx = 0;
		cTime.gridy = 0;
		cTime.weightx = 1;
		cTime.weighty = 0.5;
		pnlTime.add(lblTime, cTime);
		
		cTime.gridx = 0;
		cTime.gridy = 1;
		cTime.weightx = 0.5;
		cTime.weighty = 0.5;
		cTime.gridwidth = 1;
		pnlTime.add(btnStart, cTime);
		pnlTime.add(btnStop, cTime);
		
		cTime.gridx = 1;
		cTime.gridy = 1;
		cTime.weightx = 0.5;
		cTime.weighty = 0.5;
		pnlTime.add(btnReset, cTime);
		
		
			// Constraints for Title display
		GridBagConstraints cTitle = new GridBagConstraints();
		pnlTitle.setLayout(new GridBagLayout());
		
		cTitle.anchor = GridBagConstraints.LINE_START;
		cTitle.fill = GridBagConstraints.CENTER;
		cTitle.insets = new Insets(5, 5, 5, 5);
		
		cTitle.gridx = 0;
		cTitle.gridy = 0;
		cTitle.weightx = 1;
		cTitle.weighty = 0.5;
		pnlTitle.add(lblPrompt, cTitle);
		
		cTitle.anchor = GridBagConstraints.CENTER;
		cTitle.gridx = 0;
		cTitle.gridy = 1;
		cTitle.weightx = 1;
		cTitle.weighty = 0.5;
		pnlTitle.add(txtArea, cTitle);
		
		
			// Constraints for Manual add: Pop up Dialog for manually adding an Activity
		GridBagConstraints cManual = new GridBagConstraints();
		pnlManualAdd.setLayout(new GridBagLayout());
		cManual.anchor = GridBagConstraints.LINE_START;
		cManual.fill = GridBagConstraints.CENTER;
		cManual.insets = new Insets(5, 7, 0, 0);
		
		cManual.gridx = 0;
		cManual.gridy = 0;
		cManual.weightx = 0.3;
		cManual.weighty = 0.3;
		pnlManualAdd.add(lblDatePrompt, cManual);
		
		cManual.gridx = 1;
		cManual.gridy = 0;
		cManual.weightx = 0.4;
		cManual.weighty = 0.3;
		pnlManualAdd.add(lblTitlePrompt, cManual);

		cManual.gridx = 2;
		cManual.gridy = 0;
		cManual.weightx = 0.3;
		cManual.weighty = 0.3;
		pnlManualAdd.add(lblTimePrompt, cManual);
		
		cManual.anchor = GridBagConstraints.CENTER;
		cManual.insets = new Insets(0, 2, 5, 2);
		cManual.gridx = 0;
		cManual.gridy = 1;
		cManual.weightx = 0.3;
		cManual.weighty = 0.7;
		pnlManualAdd.add(txtDate, cManual);
		
		cManual.insets = new Insets(0, 0, 5, 2);
		cManual.gridx = 1;
		cManual.gridy = 1;
		cManual.weightx = 0.4;
		cManual.weighty = 0.7;
		pnlManualAdd.add(txtTitle, cManual);

		cManual.gridx = 2;
		cManual.gridy = 1;
		cManual.weightx = 0.3;
		cManual.weighty = 0.7;
		pnlManualAdd.add(txtTime, cManual);

		
			// Constraints for Activity display
		GridBagConstraints cActivity = new GridBagConstraints();
		pnlActivity.setLayout(new GridBagLayout());
		
		cActivity.anchor = GridBagConstraints.LINE_START;
		cActivity.fill = GridBagConstraints.CENTER;
		cActivity.insets = new Insets(5, 5, 5, 5);
		
		cActivity.gridx = 0;
		cActivity.gridy = 0;
		cActivity.weightx = 1;
		cActivity.weighty = 0.16;
		pnlActivity.add(lblActivity, cActivity);
		
		cActivity.anchor = GridBagConstraints.CENTER;
		cActivity.gridx = 0;
		cActivity.gridy = 1;
		cActivity.weightx = 1;
		cActivity.weighty = 0.16;
		pnlActivity.add(lblActivityDisplay, cActivity);
		
		cActivity.anchor = GridBagConstraints.LINE_END;
		cActivity.gridx = 0;
		cActivity.gridy = 2;
		cActivity.weightx = 1;
		cActivity.weighty = 0.16;
		pnlActivity.add(btnSave, cActivity);
		
		cActivity.insets = new Insets(20, 0, 20, 0);
		cActivity.anchor = GridBagConstraints.CENTER;
		cActivity.gridx = 0;
		cActivity.gridy = 5;
		cActivity.weightx = 1;
		cActivity.weighty = 0.16;
		pnlActivity.add(btnManualAdd, cActivity);
		
		
			// LAYOUT
		GridBagConstraints c = new GridBagConstraints();
		
			// TIME DISPLAY: time label, start/reset button
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.3;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.CENTER;	
		c.insets = new Insets(0, 0, 0, 0); // 50, 0, 0, 0
		add(pnlTime, c);
		
			// TITLE DISPLAY: prompt label, txtArea, save button
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0.3;
		c.insets = new Insets(0, 0, 0, 0);
		add(pnlTitle, c);
		
			// Activity DISPLAY: 
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 0.3;
		c.insets = new Insets(0, 0, 0, 0); // 0, 0, 50, 0
		add(pnlActivity, c);
		
	}

	/**
	 * run handles timer
	 * Implemented using the System.currentTimeMillis function
	 * Gets the currentTime in milliseconds when the timer is started
	 * Continues until a full second has occurred,
	 * Then increases seconds and restarts using System.getCurrentTimeMillis
	 */
	@Override
	public void run() {
		
		long startTime;
		NumberFormat nf = new DecimalFormat("00");
		
		while (hours < 60) {
		   while(minutes < 60) {
			   while (seconds < 60) {
				   
				   startTime = System.currentTimeMillis();
				   millis = 0;
				   
				   while (millis < 1000) {
					  
				      if(stop)
				      {
				    	  break;
				      }
				      	// gets the number of milliseconds since the timer has been started
				      millis = System.currentTimeMillis() - startTime;
				      milliseconds = (int) millis / 10;
				      
				      	// adding the fields to the timer display
					  lblTime.setText(nf.format(hours) + ":" + nf.format(minutes) + ":" + nf.format(seconds) + ":" + nf.format(milliseconds));				
				   }
				   seconds++;
			   }
		   minutes++;
		   seconds = 0;
		   }
		hours++;
		minutes = 0;
		}		
	}

	/**
	 * ActionPerformed
	 * Handles various events as they occur:
	 * Starting, Stopping, Resetting the timer
	 * Saving an Activity
	 * Manually Adding an Activity
	 * 
	 * @param e the ActionEvent that was performed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(btnStart)) { // button: START
			Thread t = new Thread(this);

			stop = false;
			btnStart.setVisible(false);
			btnStop.setVisible(true);
			
				// START or RESTART
			if (lblTime.getText().equals("00:00:00:00")) {
				try {
					hours = 0; 
					minutes = 0;
					seconds = 0;
					milliseconds = 0;
				}
				catch (Exception ex) {
					// add throws if title is empty
				}
			}
				// RESUME
			else {
				hours = Integer.parseInt(lastTime.substring(0, 2));
				minutes = Integer.parseInt(lastTime.substring(3, 5));
				seconds = Integer.parseInt(lastTime.substring(6, 8));
				milliseconds = Integer.parseInt(lastTime.substring(9, 11));
			}
			t.start();
		}
		else if (e.getSource().equals(btnStop)) { // button: STOP
			stop = true;
			btnStart.setVisible(true);
			btnStop.setVisible(false);
			lastTime = lblTime.getText();
			double time = Activity.getTime(lastTime);
			currentTitle = txtArea.getText();
			if (currentTitle != null && currentTitle.length() != 0) {
				lblActivityDisplay.setText(date + " - " + currentTitle + " - " + Activity.getTime(time));
			}
			else {
				lblActivityDisplay.setText(date + " - Title - " + Activity.getTime(time));
			}
		}
		else if (e.getSource().equals(btnReset)) { // button: RESET
			stop = true;
			lblTime.setText("00:00:00:00");
			txtArea.setText("");
			btnStart.setVisible(true);
			btnStop.setVisible(false);
			lblActivityDisplay.setText(date + " - Title - " + "0:00.0");
		}
		else if (e.getSource().equals(btnSave)) { // button: SAVE
			try {
				now = LocalDateTime.now();
				String dateNow = now.format(dtf);
				String dayOfYear = now.format(DAYOFYEAR);
				listener.saveActivity(dateNow, dayOfYear, txtArea.getText(), lblTime.getText());
				btnReset.doClick();
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(TrackerPanel.this, iae.getMessage()); 
			}
		}
		else if (e.getSource().equals(btnManualAdd)) {
			
			// Opens pop-up window prompting for information to manually add activity
			int selected = JOptionPane.showConfirmDialog(TrackerPanel.this, pnlManualAdd, "", JOptionPane.OK_CANCEL_OPTION);
			
			if (selected == JOptionPane.OK_OPTION) {
			
				String manualDate = txtDate.getText();
				String manualTitle = txtTitle.getText();
				String manualTime = txtTime.getText();
				
				try {
					listener.saveActivity(manualDate, null, manualTitle, manualTime);	// try to save the activity with manual info
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(TrackerPanel.this, iae.getMessage()); 
				}
			}
		}
	}

}
