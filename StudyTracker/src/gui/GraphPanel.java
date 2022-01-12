package gui;

import java.awt.Color;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import course.Course;
import tracker.Tracker;
import util.SortedList;

/**
 * GRAPH PANEL
 * is opened when graph course/semester is clicked in the DataPanel
 * 
 * Creates graphs using org.jfree.chart
 * according to the data and whether it is the course or semester graph
 * 
 * @author alexzettlemoyer
 *
 */
public class GraphPanel extends JFrame {
	
	/** Instance of tracker to retrieve necessary data to graph */
	Tracker tracker;
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * GraphPanel Constructor
	 * Creates the graph
	 * 
	 * @param boolean: true if CourseGraph, false if SemesterGraph
	 */
	public GraphPanel(boolean course, String period) {
		
		setLocation(500, 100);
		setTitle("Graph");
		
		tracker = Tracker.getInstance();
		JFreeChart chart;
		
		int timePeriod;
		
		switch (period) {
		case "today":
			timePeriod = 0;
			break;
		case "last 7":
			timePeriod = 7;
			break;
		case "last 14":
			timePeriod = 14;
			break;
		case "last 30":
			timePeriod = 30;
			break;
		default:
			timePeriod = -1;
			break;	
		}
		
			// Customizing the chart appearance
		StandardChartTheme theme = (StandardChartTheme) ChartFactory.getChartTheme();
		theme.setChartBackgroundPaint(Color.WHITE);
		theme.setPlotBackgroundPaint(Color.WHITE);
		theme.setRangeGridlinePaint(Color.LIGHT_GRAY);
		
		ChartFactory.setChartTheme(theme);
		
			// creates the chart accordingly for Course/Semester
		if (course) {
			chart = setupCourseGraph(timePeriod);
			setSize(750, 500);
		}
		else {
			chart = setupSemesterGraph(timePeriod);
			setSize(900, 500);
		}

		ChartPanel chartPanel = new ChartPanel(chart);
		setContentPane(chartPanel);
	}
	
	/**
	 * setUpCourseGraph
	 * creates a CourseGraph
	 * uses the data from tracker to find hrs studied over the last 30 days
	 * graphs each day each individually in the bar graph
	 * 
	 * @param period
	 * @return JFreeChart the CourseGraph
	 */
	private JFreeChart setupCourseGraph(int period) {
		
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		Course course = tracker.getCurrentCourse();		
		
			// for each of the last 30 days add the data to the chart
		for (int i = 0; i <= 30; i++) {
			String date = course.getDateFromDayPeriod(i, null);
			dataSet.addValue(course.getIndividualDayMinutes(i, null) /  60, "", date);
		}
			
		JFreeChart barChart = ChartFactory.createBarChart(
				course.getCourseTitle() + " last 30 days", "Date", "Time [Minutes]", dataSet, PlotOrientation.VERTICAL, false, true, false);
		barChart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		
		((BarRenderer)barChart.getCategoryPlot().getRenderer()).setBarPainter(new StandardBarPainter());
	    barChart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.GRAY);
		
		return barChart;
	}
	
	/**
	 * setUpSemesterGraph
	 * creates a SemesterGraph
	 * uses the data from tracker to find hrs studied in each course
	 * over the period given (selected in the dataPanel)
	 * graphs each course individually in the bar graph
	 * 
	 * @param period
	 * @return JFreeChart the SemesterGraph
	 */
	private JFreeChart setupSemesterGraph(int period) {
		
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String timePeriod = new String();
		
		
		SortedList<Course> courses = tracker.getCurrentSemester().getCourseList();
			// if graphing total data
		if (period == -1) {
				// add the data for each of the courses total minutes
			for (int i = 0; i < courses.size(); i++) {
				dataSet.addValue(courses.get(i).getTotalMinutes() / 60, "", courses.get(i).getCourseTitle());
			}
			timePeriod = "total";
		}
		else {
				// add the data for each of the courses within the given period
			for (int i = 0; i < courses.size(); i++) {
				dataSet.addValue(courses.get(i).getDayPeriodMinutes(period, null) / 60, "", courses.get(i).getCourseTitle());
			}
			timePeriod = "last " + period + " days";
		}
		
		if (period == 0) {
			timePeriod = "today";
		}
		
		JFreeChart barChart = ChartFactory.createBarChart(
				tracker.getCurrentSemester().getSemesterName() + " " + timePeriod, "Course", "Time [Hours]", dataSet, PlotOrientation.VERTICAL, false, true, false);
		
		((BarRenderer)barChart.getCategoryPlot().getRenderer()).setBarPainter(new StandardBarPainter());
	    barChart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.GRAY);
		
		return barChart;
	}

}
