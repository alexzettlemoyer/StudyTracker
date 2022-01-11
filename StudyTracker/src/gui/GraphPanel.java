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

public class GraphPanel extends JFrame {
	
	
	Tracker tracker;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
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
		
		StandardChartTheme theme = (StandardChartTheme) ChartFactory.getChartTheme();
		theme.setChartBackgroundPaint(Color.WHITE);
		theme.setPlotBackgroundPaint(Color.WHITE);
		theme.setRangeGridlinePaint(Color.LIGHT_GRAY);
		
		ChartFactory.setChartTheme(theme);
		
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
	
	private JFreeChart setupCourseGraph(int period) {
		
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		Course course = tracker.getCurrentCourse();		
		
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
	
	private JFreeChart setupSemesterGraph(int period) {
		
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String timePeriod = new String();
		
		SortedList<Course> courses = tracker.getCurrentSemester().getCourseList();
		if (period == -1) {
			for (int i = 0; i < courses.size(); i++) {
				dataSet.addValue(courses.get(i).getTotalMinutes() / 60, "", courses.get(i).getCourseTitle());
			}
			timePeriod = "total";
		}
		else {
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
