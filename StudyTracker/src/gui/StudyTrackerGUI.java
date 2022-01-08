package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import tracker.Tracker;

/**
 * STUDYTRACKER GUI
 * Class that directly interacts with the console
 * 
 * Contains 4 panels:
 * pnlDirectory across the top of the screen
 * pnlTracker on the bottom left of the screen
 * pnlActivity in the bottom middle of the screen
 * pnlData at on the bottom left of the screen
 * 
 * Contains a menu bar and handles functions:
 * Open, Save As, Clear, and Quit
 * Which directly interact with the Programs .properties file
 * 
 * After opening the program for the first time, 
 * the .properties file is stored in the program's directory
 * the .properties file contains the absolute path of the most recent 
 * .txt file that the program saved, 
 * 
 * @author alexzettlemoyer
 * 
 */
public class StudyTrackerGUI extends JFrame implements ActionListener {
	
	/** Directory Panel (top) */
	DirectoryPanel pnlDirectory;
	/** Tracker Panel (bottom left) */
	TrackerPanel pnlTracker;
	/** Activity Panel (bottom middle) */ 
	ActivityPanel pnlActivity;
	/** Data Panel (bottom right) */
	DataPanel pnlData;
	
	JMenuBar menuBar;
	JMenu menu;
	
	/** MenuBar items: */
	JMenuItem itemLoad;
	JMenuItem itemSaveAs;
	JMenuItem itemSave;
	JMenuItem itemClear;
	JMenuItem itemQuit;
	
	/** Instance of the GUI */
	static StudyTrackerGUI ui;
	
	/** fields for handling .properties file */
	String properties;
	String path;
	Properties prop;
	File propFile;
	
	/** fields that interact with the rest of the program
	 * ie. panels, and the program's controller */
	static Tracker tracker;
	Listener listener;
	
	
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
				
		// setup
		Container c = getContentPane();
		setTitle("StudyTracker");
		setLocation(100, 100);
		setSize(800, 600);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setUpMenuBar();
		
			// gets the absolute path of where the program was launched
		path = System.getProperty("user.dir");
			// the absolute path of the properties file is in the 
			// "lib" file titled "config.properties"
		properties = path + "/lib/config.properties";
		
			// makes a new file called StudyTrackerData 
			// which stores the .txt Files (mainly for testing purposes)
		new File(path + "/lib/StudyTrackerData/").mkdir();
			
			// creating instance of Properties and the .properties file
		prop = new Properties();
		propFile = new File(properties);
		
			// indicating that the properties will store filePath
		prop.setProperty("filePath", "");

		
		
		tracker = Tracker.getInstance();
		listener = Listener.getInstance();
		
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
		
		
			// adding them together
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
		
			// if there are contents in the properties file,
		if (loadPropFile().length() != 0) {
			tracker.loadRecords(new File(loadPropFile()));
			pnlDirectory.updateLists();
			pnlActivity.update();
			pnlData.update();
		}
		
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				itemQuit.doClick();
			}
		});
		
	}
	
	private void setUpMenuBar() {
		
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		
		itemLoad = new JMenuItem("Open");
		itemSaveAs = new JMenuItem("Save As");
		itemSave = new JMenuItem();
		itemClear = new JMenuItem("Clear");
		itemQuit = new JMenuItem("Quit");
		
		itemLoad.addActionListener(this);
		itemSaveAs.addActionListener(this);
		itemSave.addActionListener(this);
		itemClear.addActionListener(this);
		itemQuit.addActionListener(this);
				
		menu.add(itemLoad);
		menu.add(itemSaveAs);
		menu.add(itemClear);
		menu.add(itemQuit);
		menuBar.add(menu);

		this.setJMenuBar(menuBar);
		
	}
	
	public static void main(String[] args) {
				
		ui = new StudyTrackerGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == itemClear) {
			
			if (tracker.isChanged()) {
				int selected = JOptionPane.showConfirmDialog(null, "Study tracker data is unsaved. Would you like to save before clearing?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (selected == JOptionPane.YES_OPTION) {
					itemSave.doClick();
					tracker.reset();
				}
				else if (selected == JOptionPane.NO_OPTION) {
					tracker.reset();
				}
				else if (selected == JOptionPane.CANCEL_OPTION) {
					// do nothing
				}
			}
			else {
				tracker.reset();
			}
			listener.changeSemester();
			listener.changeCourse();
		}
		else if (e.getSource() == itemLoad) {
						
			try {
				
				if (loadPropFile().length() == 0) {
					String fileName = getFileName(true);
					tracker.loadRecords(new File(fileName));
					savePropFile(fileName);
				}
				else {
					String fileName = getFileName(true);
					tracker.loadRecords(new File(fileName));
					savePropFile(fileName);
				}
				
			}
			catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(this, "Unable to load file.");
			}
			catch (IllegalStateException ise) {
				// ignore (user canceled)
			}
		}
		else if (e.getSource() == itemSaveAs) {
			
			if (tracker.isChanged()) {
				try {					
					String fileName = getFileName(false);
					tracker.saveRecords(new File(fileName));
					savePropFile(fileName);
						
				}
				catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(this, "Unable to save file.");
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "No changes to save.");
			}
		}
		else if (e.getSource() == itemSave) {
			
			if (loadPropFile().length() == 0) {
				String fileName = "MyStudyRecords.txt";
				File newFile = new File(path + "/lib/StudyTrackerData/", fileName);
				tracker.saveRecords(newFile);
				savePropFile(newFile.getAbsolutePath());
			}
			else {
				File currentFile = new File(loadPropFile());
				tracker.saveRecords(currentFile);
			}
			
		}
		else if (e.getSource() == itemQuit) {
			
			if (tracker != null && tracker.isChanged()) {
				try {
					// Save!!
					itemSave.doClick();	
					System.exit(0);
				}
				catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(this, "Unable to save file.");
				}
				catch (IllegalStateException ise) {
					// ignore (user canceled
				}
			}
			else {
				System.exit(0);
			}
		}
		
		pnlDirectory.updateLists();
		pnlActivity.update();
		pnlData.update();
		
	}
	
	private String getFileName(boolean load) {
		
		JFileChooser fc = new JFileChooser("./");
		fc.setCurrentDirectory(new File(path + "/lib/StudyTrackerData/"));
		int returnVal = Integer.MIN_VALUE;
		if (load) {
			returnVal = fc.showOpenDialog(this);
		}
		else {
			returnVal = fc.showSaveDialog(this);
		}
		
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			throw new IllegalStateException();
		}
		File loadFile = fc.getSelectedFile();
		return loadFile.getAbsolutePath();
		
	}
	
	/**
	 * loads the properties in the config.properties file
	 * and returns a string of the contents of "filePath"
	 * 
	 * if the config.properties file throws an exception
	 * (it didn't exist in the system) make a new one!
	 * Then calls the method loadPropFile() again to load the properties
	 * from the new properties file just created
	 * 
	 * 
	 * @return String contents - the filePath stored in the properties file
	 */
	boolean multiple = false;
	private String loadPropFile() {
		
		String contents = new String();
					
		try {
			prop.load(new FileInputStream(properties));
				
		} catch (IOException e) {
				
			try {
				propFile.createNewFile();
			} catch (IOException e1) {
				if (multiple) {
					return "";
				}
				JOptionPane.showMessageDialog(this, "IOException creating file: " + e.getMessage());
				multiple = true;
			}
			propFile = new File(properties);
			loadPropFile();
				
		}

		contents = prop.getProperty("filePath");
		return contents;
	}
	
	private void savePropFile(String filePath) {
		try {
			prop.setProperty("filePath", filePath);
			prop.store(new FileOutputStream(propFile), "new Filepath");
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(this, "IOException: " + e.getMessage());
		}
	}
	
}
