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
import java.io.FileNotFoundException;
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

public class StudyTrackerGUI extends JFrame implements ActionListener {
	
	DirectoryPanel pnlDirectory;
	TrackerPanel pnlTracker;
	ActivityPanel pnlActivity;
	DataPanel pnlData;
	
	JMenuBar menuBar;
	JMenu menu;
	
	JMenuItem itemLoad;
	JMenuItem itemSave;
	JMenuItem itemClear;
	JMenuItem itemQuit;
		
	static StudyTrackerGUI ui;
	
	String properties = "file.properties";
	
	static Tracker tracker;
	Listener listener;
		
	Properties prop;
	File propFile;
	
	
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
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setUpMenuBar();
				
		prop = new Properties();
		propFile = new File(properties);
		
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
		itemSave = new JMenuItem("Save As");
		itemClear = new JMenuItem("Clear");
		itemQuit = new JMenuItem("Quit");
		
		itemLoad.addActionListener(this);
		itemSave.addActionListener(this);
		itemClear.addActionListener(this);
		itemQuit.addActionListener(this);
				
		menu.add(itemLoad);
		menu.add(itemSave);
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
		else if (e.getSource() == itemSave) {
			
			if (tracker.isChanged()) {
				try {
					if (loadPropFile().length() == 0) {
						String fileName = getFileName(false);
						tracker.saveRecords(new File(fileName));
						savePropFile(fileName);
					}
					else {
						File currentFile = new File(loadPropFile());
						int selected = JOptionPane.showConfirmDialog(null, "Save to " + currentFile.getName() + "?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						
						if (selected == JOptionPane.YES_OPTION) {
							tracker.saveRecords(currentFile);
						}
						else if (selected == JOptionPane.NO_OPTION) {
							String fileName = getFileName(false);
							tracker.saveRecords(new File(fileName));
							savePropFile(fileName);
						}
					}
					
				}
				catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(this, "Unable to save file.");
				}
				catch (IllegalStateException ise) {
					// ignore (user canceled)
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "No changes to save.");
			}
		}
		else if (e.getSource() == itemQuit) {
			
			if (tracker != null && tracker.isChanged()) {
				try {
					if (loadPropFile().length() != 0) {
						
						tracker.saveRecords(new File(loadPropFile()));
					}
					else {
						int selected = JOptionPane.showConfirmDialog(null, "Study tracker data is unsaved. Would you like to save before clearing?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
						
						if (selected == JOptionPane.YES_OPTION) {
							String fileName = getFileName(false);
							tracker.saveRecords(new File(fileName));
							savePropFile(fileName);
						}
					}
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
	
	private String loadPropFile() {
		
		String contents = new String();
		
		try {
			prop.load(new FileInputStream(propFile));
		} catch (FileNotFoundException e) {
			System.out.println("Unable to load filePath FileNotFound");
		} catch (IOException e) {
			System.out.println("Unable to load filePath IOException");
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
			System.out.println("Unable to save filePath");
		}
	}
	
}
